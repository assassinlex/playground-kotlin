package _01_coroutine

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun coroutineScope01() {
    GlobalScope.launch {
        val result1 = withContext(Dispatchers.Default) {
            delay(2000)
            1
        }
        // coroutineScope 采用父协程的 CoroutineContext, 无法使用其他的 CoroutineDispatcher
        val result2 = coroutineScope {
            delay(1000)
            2
        }
        val result = result1 + result2
        println(result)
    }
    Thread.sleep(5000)
}