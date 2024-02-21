package ninja.bryansills.daggerscopingdemo

import androidx.compose.ui.unit.IntOffset
import dagger.Binds
import dagger.Module
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
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
class RealCounterUseCase @AssistedInject constructor(
    @Assisted("counter-initial") initialCount: Int,
    @Assisted("counter-offset") offset: Int,
) : CounterUseCase {
    private val count = MutableStateFlow(initialCount + offset)

    override fun invoke(): StateFlow<Int> = count.asStateFlow()

    override fun increment() {
        count.update { it + 1 }
    }

    @AssistedFactory
    interface RealFactory : CounterUseCase.Factory {
        override fun create(
            @Assisted("counter-initial") initialCount: Int,
            @Assisted("counter-offset") offset: Int
        ): RealCounterUseCase
    }
}

interface CounterUseCase {
    operator fun invoke(): StateFlow<Int>

    fun increment()

    // lotta work here, but now we can use interfaces with @AssistedInject
    interface Factory {
        fun create(initialCount: Int, offset: Int): CounterUseCase
    }
}

@InstallIn(SingletonComponent::class)
@Module
interface CounterModule {
    @Binds
    fun bindFactory(real: RealCounterUseCase.RealFactory): CounterUseCase.Factory
}
