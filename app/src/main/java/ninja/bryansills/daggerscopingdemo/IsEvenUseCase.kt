package ninja.bryansills.daggerscopingdemo

import android.util.Log
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsEvenUseCase @AssistedInject constructor(
    private val pastTimeProvider: PastTimeProvider,
    @Assisted private val counterUseCase: CounterUseCase
) {
    operator fun invoke(): Flow<Boolean> {
        Log.d("BRYANS", "past time is ${pastTimeProvider.someDaysAgo}")
        Log.d("BRYANS", "current time is ${pastTimeProvider.now}")
        return counterUseCase.invoke().map { it % 2 == 0 }
    }

    @AssistedFactory
    interface Factory {
        fun create(counterUseCase: CounterUseCase): IsEvenUseCase
    }
}
