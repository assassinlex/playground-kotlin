package _01_coroutine

import kotlinx.coroutines.*

// case01: 父协程退出, 子协程会随之退出
@OptIn(DelicateCoroutinesApi::class)
fun coroutineContext01() {
    val job = GlobalScope.launch {
        // job1 使用的是全局作用域创建的协程, 换言之 job1 不是 job 的子协程, 其不受 job 协程影响
        val job1 = GlobalScope.launch {
            println("job1: I have mny own context and execute independently!")
            delay(1000)
            println("job1: I am not affected by cancellation of the job")
        }
        // job2 使用的是 job 协程的上下文, 故 job2 是 job 的子协程
        // 父协程取消后退出, 子协程随之退出
        val job2 = launch(coroutineContext) {
            println("job2: I am a child of the job coroutine")
            delay(1000)
            println("job2: I will not execute this line if my parent job is cancelled")
        }
        job1.join()
        job2.join()
    }
    Thread.sleep(500) // 先让 job 执行一小会儿
    job.cancel() // 然后取消 job
    Thread.sleep(2000) // 阻塞主线程 2s
}

// case02: 父协程会等待所有子协程完成
fun coroutineContext02() = runBlocking {
    // job 父协程
    val job = launch {
        // 子协程 1
        val job1 = launch(coroutineContext) {
            println("job1 is running")
            delay(1000)
            println("job1 is done")
        }
        // 子协程 2
        val job2 = launch(coroutineContext) {
            println("job2 is running")
            delay(1500)
            println("job2 is done")
        }
        // 子协程 3
        val job3 = launch(coroutineContext) {
            println("job3 is running")
            delay(2000)
            println("job3 is done")
        }
        job1.join()
        job2.join()
        job3.join()
    }
    job.join()
    println("all jobs is complete")
}

// case03: 多个 coroutineContext 进行 + 运算
// case0301: 无 + 运算版
@OptIn(DelicateCoroutinesApi::class)
fun coroutineContext0301() {
    val job = GlobalScope.launch {
        // childJob 虽然命名为子协程, 其实他并不是 job 的子协程
        val childJob = GlobalScope.launch(Dispatchers.Default) {
            println(1)
            delay(1000)
            println(2)
        }
        childJob.join()
    }
    Thread.sleep(500)
    job.cancel() // job 取消, 并不会影响 childJob 的执行
    Thread.sleep(2000)
}

// case0302: 有 + 运算版
@OptIn(DelicateCoroutinesApi::class)
fun coroutineContext0302() {
    val job = GlobalScope.launch {
        // 此时的 childJob 的上下文由于重载运算符, 使得 childJob 有多个协程上下文的特性
        // 那么 child 就拥有 job 的子协程的特性, 可以把 childJob 看做 job 的子协程
        val childJob = GlobalScope.launch(Dispatchers.Default + coroutineContext) {
            println(1)
            delay(1000)
            println(2)
        }
        childJob.join()
    }
    Thread.sleep(500)
    job.cancel() // job 取消退出, 那么 childJob 会随之退出
    Thread.sleep(2000)
}

// case04: 协程的 coroutineContext + job, 那么 job 可以直接管理该协程
fun coroutineContext04() = runBlocking {
    val job = Job() // 创建 job 来管理协程的声明周期
    launch(coroutineContext + job) {
        delay(500)
        println("job1 is done")
    }
    launch(coroutineContext + job) {
        delay(1000)
        println("job2 is done")
    }
    launch(Dispatchers.Default + job) {
        delay(1500)
        println("job3 is done")
    }
    launch(Dispatchers.Default + job) {
        delay(2000)
        println("job4 is done")
    }
    launch(Dispatchers.Default + job) {
        delay(2500)
        println("job5 is done")
    }
    delay(1800)
    println("Cancelling the job!")
    job.cancel() // job 一旦取消, job 所管理的未完成的协程全部都会取消
}