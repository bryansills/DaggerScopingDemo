package ninja.bryansills.daggerscopingdemo

import androidx.compose.foundation.layout.Box
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test

class CoolBoxTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.NEXUS_10 // TODO: other device config
    )

    @Test
    fun basic() {
        paparazzi.snapshot { Box { CoolBox() } }
    }
}
