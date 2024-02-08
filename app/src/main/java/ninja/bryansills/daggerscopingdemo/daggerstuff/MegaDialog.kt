package ninja.bryansills.daggerscopingdemo.daggerstuff

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MegaDialog(megaViewModel: MegaViewModel = hiltViewModel()) {
    val uiState by megaViewModel.uiState.collectAsState(initial = "butts" to 69)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(modifier = Modifier
            .height(400.dp)
            .width(400.dp)
        ) {
            Text("This is the mega dialog")

            Text("Name: ${uiState.first}")
            Text("Age: ${uiState.second}")

            Button(onClick = megaViewModel::newName) {
                Text("New Name")
            }

            Button(onClick = megaViewModel::increment) {
                Text("Increment Age")
            }
        }
    }
}