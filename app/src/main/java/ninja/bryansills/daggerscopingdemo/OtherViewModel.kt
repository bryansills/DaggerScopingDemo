package ninja.bryansills.daggerscopingdemo

import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class OtherViewModel @Inject constructor(
    private val counterUseCaseFactory: CounterUseCase.Factory,
    private val isEvenUseCaseFactory: IsEvenUseCase.Factory,
) : ViewModel() {
    private val counterUseCase = counterUseCaseFactory.create(50, 5)
    private val isEvenUseCase = isEvenUseCaseFactory.create(counterUseCase)

    val uiState: Flow<Pair<Int, Boolean>> = combine(
        counterUseCase.invoke(),
        isEvenUseCase.invoke()
    ) { count, isEven -> count to isEven }

    fun increment() {
        counterUseCase.increment()
    }
}