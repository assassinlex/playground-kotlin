package _01_coroutine

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun withContext01() {
    GlobalScope.launch {
        // withContext 不会创建新的协程
        val result1 = withContext(Dispatchers.Default) {
            delay(2000)
            1
        }
        val result2 = withContext(Dispatchers.IO) {
            delay(1000)
            2
        }
        val result = result1 + result2
        println(result)
    }
    Thread.sleep(5000)
}

@OptIn(DelicateCoroutinesApi::class)
fun withContext02() {
    val job = GlobalScope.launch {
        withContext(NonCancellable) {
            delay(2000)
            println("任务不会被取消") // todo:: 这里并没有输出, 是程序没有执行吗?
        }
    }
    job.cancel()
    Thread.sleep(5000)
}