package ninja.bryansills.daggerscopingdemo

import androidx.compose.foundation.isSystemInDarkTheme
import app.cash.turbine.test
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
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
            assertTrue(awaitItem())
            counterUseCase.increment()
            assertFalse(awaitItem())
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
