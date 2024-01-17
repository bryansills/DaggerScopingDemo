package ninja.bryansills.daggerscopingdemo

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel(assistedFactory = FirstViewModel.Factory::class)
class FirstViewModel @AssistedInject constructor(
    private val counterUseCaseFactory: CounterUseCase.Factory,
    private val isEvenUseCaseFactory: IsEvenUseCase.Factory,
    @Assisted("vm-initial") val initialCount: Int,
    @Assisted("vm-offset") val offset: Int
) : ViewModel() {

    private val counterUseCase = counterUseCaseFactory.create(initialCount, offset)
    private val isEvenUseCase = isEvenUseCaseFactory.create(counterUseCase)

    val uiState: Flow<Pair<Int, Boolean>> = combine(
        counterUseCase.invoke(),
        isEvenUseCase.invoke()
    ) { count, isEven -> count to isEven }

    fun increment() {
        counterUseCase.increment()
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("vm-initial") initialCount: Int,
            @Assisted("vm-offset") offset: Int
        ): FirstViewModel
    }
}
