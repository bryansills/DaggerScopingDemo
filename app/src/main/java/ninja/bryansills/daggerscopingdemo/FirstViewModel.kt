package ninja.bryansills.daggerscopingdemo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(
    private val counterUseCase: CounterUseCase,
    private val isEvenUseCase: IsEvenUseCase,
) : ViewModel() {

    val uiState: Flow<Pair<Int, Boolean>> = combine(
        counterUseCase.invoke(),
        isEvenUseCase.invoke()
    ) { count, isEven -> count to isEven }

    fun increment() {
        counterUseCase.increment()
    }
}