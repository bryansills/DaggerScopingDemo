package ninja.bryansills.daggerscopingdemo

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
@ViewModelScoped
class CounterUseCase @Inject constructor() {
    private val count = MutableStateFlow(0)

    fun invoke(): StateFlow<Int> = count.asStateFlow()

    fun increment() {
        count.update { it + 1 }
    }
}