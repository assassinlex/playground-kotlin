package _01_coroutine

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun yield01() = runBlocking {
    // 解释: yield() 用于挂起当前协程, 将当前的协程分发到 CoroutineDispatcher 队列,
    //      等待其他协程完成或挂起后, 再从先前被挂起协程的挂起点继续执行
    // 本质: yield 交出 cpu 时间片, 让程序交替执行
    // 0 -> 1 -> 4 -> 2 -> 5 -> 3 -> 6
    launch {
        println(1)
        yield()
        println(2)
        yield()
        println(3)
    }
    launch {
        println(4)
        yield()
        println(5)
        yield()
        println(6)
    }
    println(0)
}