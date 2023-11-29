package _01_coroutine

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun delay01() = runBlocking {
    // 输出顺序: 1(立即) -> 2(立即) -> 3(立即) -> 4(等待1s) -> 5(等待2s)
    // 1, 2, 5 在同一个线程, 3, 4 在同一个线程
    println("1: current thread is ${Thread.currentThread().name}")
    GlobalScope.launch {
        println("3: current thread is ${Thread.currentThread().name}")
        // delay 会挂起当前函数, 但不会阻塞当前线程, 不影响当前线程执行其他任务(如有新的任务绑到当前线程)
        delay(1000L)
        println("4: current thread is ${Thread.currentThread().name}")
    }
    println("2: current thread is ${Thread.currentThread().name}")
    Thread.sleep(2000L)
    println("5: current thread is ${Thread.currentThread().name}")
}