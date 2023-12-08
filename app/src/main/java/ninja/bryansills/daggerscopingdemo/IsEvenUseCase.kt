package ninja.bryansills.daggerscopingdemo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsEvenUseCase @Inject constructor(private val counterUseCase: CounterUseCase) {
    fun invoke(): Flow<Boolean> {
        return counterUseCase.invoke().map { it % 2 == 0 }
    }
}

