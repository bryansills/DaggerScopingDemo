package ninja.bryansills.daggerscopingdemo

import android.util.Log
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.Duration
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

interface TimeProvider {
    val now: Instant
}

interface PastTimeProvider : TimeProvider {
    val someDaysAgo: Instant
}

data class DayAmountHolder(val numOfDays: Long)

class RealPastTimeProvider @Inject constructor(
    private val dayAmountHolder: DayAmountHolder
) : PastTimeProvider {
    init {
        Log.d("BRYANS", "creating instance")
    }

    override val now: Instant
        get() = Instant.now()
    override val someDaysAgo: Instant
        get() = Instant.now() - Duration.ofDays(dayAmountHolder.numOfDays)
}

@Module
@InstallIn(SingletonComponent::class)
interface TimeProviderModule {
    @Binds
    fun bindTimeProvider(impl: PastTimeProvider): TimeProvider

    @Singleton
    @Binds
    fun bindPastTimeProvider(impl: RealPastTimeProvider): PastTimeProvider

    companion object {
        @Provides
        fun provideDayAmountHolder() = DayAmountHolder(2)
    }
}