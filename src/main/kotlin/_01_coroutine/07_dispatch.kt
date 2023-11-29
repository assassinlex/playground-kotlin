package _01_coroutine

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
@ExperimentalCoroutinesApi
fun dispatch01() = runBlocking(Dispatchers.Default) {
    val jobs = ArrayList<Job>()
    jobs += launch(Dispatchers.Unconfined) { // 无限制
        println("'Dispatchers.Unconfined': 当前线程 ${Thread.currentThread().name}")
    }
    jobs += launch(coroutineContext) { // 使用父级上下文, 即 runBlocking 的上下文
        println("'coroutineContext': 当前线程 ${Thread.currentThread().name}")
    }
    jobs += launch(Dispatchers.Default) {
        println("'Dispatchers.Default': 当前线程 ${Thread.currentThread().name}")
    }
    jobs += launch {
        println("'default': 当前线程 ${Thread.currentThread().name}")
    }
    jobs += launch(newSingleThreadContext("MyThread")) { // 自己创建的线程
        println("'MyThread': 当前线程 ${Thread.currentThread().name}")
    }
    jobs.forEach { it.join() }
}

@OptIn(DelicateCoroutinesApi::class)
@ExperimentalCoroutinesApi
fun dispatch02() = runBlocking() {
    val jobs = ArrayList<Job>()
    // Unconfined 在当前默认协程中运行, 但是遇到第一个挂起点之后, 恢复的线程是不确定的
    // Unconfined 无法保证其任务都在一个线程中被执行
    jobs += launch(Dispatchers.Unconfined) { // 无限制
        println("'Dispatchers.Unconfined': 当前线程 ${Thread.currentThread().name}")
        delay(500)
        println("'Dispatchers.Unconfined': 当前线程 ${Thread.currentThread().name}")
    }
    jobs += launch(coroutineContext) { // 使用父级上下文, 即 runBlocking 的上下文
        println("'coroutineContext': 当前线程 ${Thread.currentThread().name}")
    }
    jobs += launch(Dispatchers.Default) {
        println("'Dispatchers.Default': 当前线程 ${Thread.currentThread().name}")
    }
    jobs += launch {
        println("'default': 当前线程 ${Thread.currentThread().name}")
    }
    jobs += launch(newSingleThreadContext("MyThread")) { // 自己创建的线程
        println("'MyThread': 当前线程 ${Thread.currentThread().name}")
    }
    jobs.forEach { it.join() }
}