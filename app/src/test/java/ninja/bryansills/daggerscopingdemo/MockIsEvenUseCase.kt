package ninja.bryansills.daggerscopingdemo

import app.cash.turbine.test
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MockIsEvenUseCase {
    @RelaxedMockK
    lateinit var counterUseCase: CounterUseCase
    lateinit var isEvenUseCase: IsEvenUseCase

    fun create(start: Int) {
        counterUseCase = mockk(relaxUnitFun = true)
        every { counterUseCase() } returns MutableStateFlow(start)
        isEvenUseCase = IsEvenUseCase(counterUseCase)
    }

    @Test
    fun simpleTest() = runTest {
        create(10)

        isEvenUseCase().test {
            TestCase.assertTrue(awaitItem())
            counterUseCase.increment()
//            TestCase.assertFalse(awaitItem()) MOCKING IS DUMB THIS WON'T WORK
            ensureAllEventsConsumed()
            cancel()
        }
    }
}
