package ninja.bryansills.daggerscopingdemo

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

@Composable
fun FirstDialog(otherViewModel: OtherViewModel, onNavigateToSecond: () -> Unit) {
    val uiState by otherViewModel.uiState.collectAsState(initial = 0 to true)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(modifier = Modifier
            .height(400.dp)
            .width(400.dp)
        ) {
            Text("Count: ${uiState.first}")
            Text("Is Even: ${uiState.second}")

            Button(onClick = otherViewModel::increment) {
                Text("Increment")
            }

            Text("This is the first dialog")
            Button(onClick = onNavigateToSecond) {
                Text("Go to next")
            }
        }
    }
}