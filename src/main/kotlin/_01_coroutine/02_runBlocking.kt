package _01_coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 1. runBlocking 创建的协程直接运行在当前线程上, 并且会阻塞当前线程直至协程结束
// 2. runBlocking 可以创建其他协程, 例如 launch, 但是在 launch 中不能创建 runBlocking
// 3. runBlocking 最后一行的值为它的返回值

fun runBlocking01() = runBlocking {
    launch {
        delay(1000)
        println("Hello Coroutines!")
    }
    delay(2000)
}