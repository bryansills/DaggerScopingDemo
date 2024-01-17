package ninja.bryansills.daggerscopingdemo

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsEvenUseCase @AssistedInject constructor(
    @Assisted private val counterUseCase: CounterUseCase
) {
    fun invoke(): Flow<Boolean> {
        return counterUseCase.invoke().map { it % 2 == 0 }
    }

    @AssistedFactory
    interface Factory {
        fun create(counterUseCase: CounterUseCase): IsEvenUseCase
    }
}
