package _01_coroutine

import kotlinx.coroutines.*

// 1. GlobalScope.launch {} 会返回一个 Job 对象
// 2. launch、async 的 invokeOnCompletion 函数会对他们的执行结果进行回调
//    结果一般为 null[正常执行] 或 Exception[发生错误]

@OptIn(DelicateCoroutinesApi::class)
fun launch01() {
    GlobalScope.launch {
        delay(1000)
        println("Hello Coroutines!")
    }
    Thread.sleep(2000)
}

@OptIn(DelicateCoroutinesApi::class)
fun launch02() {
    GlobalScope.launch {
        val result1 = async {
            delay(2000)
            1
        }
        val result2 = async {
            delay(1000)
            2
        }
        val result = result1.await() + result2.await()
        println(result)
    }
    Thread.sleep(5000)
}

@OptIn(DelicateCoroutinesApi::class)
fun launch03() {
    GlobalScope.launch {
        val result = async {
            delay(2000)
            1
        }
        result.invokeOnCompletion {
            if (it != null) {
                println("exception: ${it.message}")
            } else {
                println("result is completed!")
            }
        }
        result.cancelAndJoin() // 取消任务
        println(result.await())
    }
    Thread.sleep(5000)
}