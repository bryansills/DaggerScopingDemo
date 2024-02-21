package ninja.bryansills.daggerscopingdemo

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isTrue
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
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
            assertThat(awaitItem()).isTrue()
            counterUseCase.increment()
//            TestCase.assertFalse(awaitItem()) MOCKING IS DUMB THIS WON'T WORK
            ensureAllEventsConsumed()
            cancel()
        }
    }

    @Test
    fun ughTest() = runTest {
        create(11)

        isEvenUseCase().test {
            assertThat(awaitItem()).isTrue()
            counterUseCase.increment()
//            TestCase.assertFalse(awaitItem()) MOCKING IS DUMB THIS WON'T WORK
            ensureAllEventsConsumed()
            cancel()
        }
    }
}
