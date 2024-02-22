package ninja.bryansills.daggerscopingdemo

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FakeIsEvenUseCase {
    lateinit var counterUseCase: CounterUseCase
    lateinit var isEvenUseCase: IsEvenUseCase

    fun create(start: Int) {
        counterUseCase = FakeCounterUseCase(start)
        isEvenUseCase = IsEvenUseCase(counterUseCase)
    }

    @Test
    fun simpleTest() = runTest {
        create(10)

        isEvenUseCase().test {
            assertThat(awaitItem()).isTrue()
            counterUseCase.increment()
            assertThat(awaitItem()).isFalse()
            ensureAllEventsConsumed()
            cancel()
        }
    }

    @Test
    fun failTest() = runTest {
        create(14)

        isEvenUseCase().test {
            assertThat(awaitItem()).isTrue()
            counterUseCase.increment()
            assertThat(awaitItem()).isFalse()
            ensureAllEventsConsumed()
            cancel()
        }
    }
}

class FakeCounterUseCase(start: Int) : CounterUseCase {
    private val internalFlow = MutableStateFlow(start)

    override fun invoke(): StateFlow<Int> = internalFlow.asStateFlow()

    override fun increment() {
        internalFlow.update { it + 1 }
    }
}
