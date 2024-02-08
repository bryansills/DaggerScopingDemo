package ninja.bryansills.daggerscopingdemo.daggerstuff

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

class MegaProvider @Inject constructor() : NameProvider, AgeProvider {
    private val _name = MutableStateFlow("Bob")
    override val name: Flow<String> = _name.asStateFlow()

    override fun changeName(newName: String) {
        _name.value = newName
    }

    private val _age = MutableStateFlow(0)
    override val age: Flow<Int> = _age.asStateFlow()

    override fun increment() {
        _age.update { it + 1 }
    }

}

interface NameProvider {
    val name: Flow<String>

    fun changeName(newName: String)
}

interface AgeProvider {
    val age: Flow<Int>

    fun increment()
}

@InstallIn(SingletonComponent::class)
@Module
interface MegaModule {
    @Singleton
    @Binds
    fun nameProvider(megaProvider: MegaProvider): NameProvider

    @Singleton
    @Binds
    fun ageProvider(megaProvider: MegaProvider): AgeProvider
}