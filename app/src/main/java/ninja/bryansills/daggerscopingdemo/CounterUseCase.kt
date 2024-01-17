package ninja.bryansills.daggerscopingdemo

import androidx.compose.ui.unit.IntOffset
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

// HERE! Try out each one of these annotations!
// Also try out commenting out both.
//@Singleton
//@ViewModelScoped
// With @AssistedInject you cannot have a scope
class CounterUseCase @AssistedInject constructor(
    @Assisted("counter-initial") initialCount: Int,
    @Assisted("counter-offset") offset: Int,
) {
    private val count = MutableStateFlow(initialCount + offset)

    fun invoke(): StateFlow<Int> = count.asStateFlow()

    fun increment() {
        count.update { it + 1 }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("counter-initial") initialCount: Int,
            @Assisted("counter-offset") offset: Int
        ): CounterUseCase
    }
}
