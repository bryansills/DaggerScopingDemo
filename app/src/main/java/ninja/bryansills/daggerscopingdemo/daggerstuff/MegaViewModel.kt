package ninja.bryansills.daggerscopingdemo.daggerstuff

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class MegaViewModel @Inject constructor(
    private val nameProvider: NameProvider,
    private val ageProvider: AgeProvider,
) : ViewModel() {
    val uiState = combine(
        nameProvider.name,
        ageProvider.age,
    ) { name, age -> name to age }

    fun newName() { nameProvider.changeName(names.random())}

    fun increment() { ageProvider.increment() }
}

private val names = listOf("asdfasd", "dfghdfgh", "dfghjdfgj", "fgertycdfs")