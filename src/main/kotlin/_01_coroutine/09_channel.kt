package _01_coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import java.lang.Exception

// case01: channel 通信
fun channel01() = runBlocking {
    val chan = Channel<Int>()
    val times = 5
    launch(Dispatchers.Default) {
        repeat(times) {
            delay(500)
            chan.send((it+1) * (it+1))
        }
    }
    launch(Dispatchers.Default) {
        repeat(times) {
            println(chan.receive())
        }
    }
    delay(5000)
    println("Job is done")
}

// case02: channel 可以关闭
fun channel02() = runBlocking {
    val chan = Channel<Int>()
    val times = 5
    launch(Dispatchers.Default) {
        repeat(times) {
            delay(500)
            chan.send((it+1) * (it+1))
            if (it == 2) { // 发送 3 次后关闭
                chan.close()
                return@launch
            }
        }
    }
    launch(Dispatchers.Default) {
        repeat(times) {
            try {
                println(chan.receive())
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }
    delay(3000)
    println("Job is done")
}

// case03: 模拟管道
@OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
fun channel03() = runBlocking {
    fun produce1() = GlobalScope.produce(Dispatchers.Default) {
        repeat(5) { send(it) }
    }
    fun produce2(nums: ReceiveChannel<Int>) = GlobalScope.produce(Dispatchers.Default) {
        for (x in nums) {
            send((x * x))
        }
    }
    fun produce3(nums: ReceiveChannel<Int>) = GlobalScope.produce(Dispatchers.Default) {
        for (x in nums) {
            send(x+1)
        }
    }

    val nums = produce1()
    val squares = produce2(nums)
    val adds = produce3(squares)
    adds.consumeEach(::println) // 函数引用
    println("接收完成")
    adds.cancel()
    squares.cancel()
    nums.cancel()
}