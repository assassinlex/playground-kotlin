package _01_coroutine

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.runBlocking

@OptIn(ObsoleteCoroutinesApi::class)
fun actor01() = runBlocking {
    val sumActor = actor<Int>(coroutineContext) {
        var sum = 0
        for (i in channel) { // 此处的 channel 为 actor 内部的 channel
            println("$sum + $i = ${sum.plus(i)}")
            sum += i
        }
    }

    repeat(10) { sumActor.send(it+1) }

    sumActor.close()
}