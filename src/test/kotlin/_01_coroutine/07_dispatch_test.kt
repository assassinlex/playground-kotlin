package _01_coroutine

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

class DispatchTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDispatch01() {
        dispatch01()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDispatch02() {
        dispatch02()
    }
}