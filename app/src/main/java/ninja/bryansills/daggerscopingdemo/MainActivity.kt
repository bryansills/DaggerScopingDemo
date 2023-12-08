package ninja.bryansills.daggerscopingdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ninja.bryansills.daggerscopingdemo.ui.theme.DaggerScopingDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DaggerScopingDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val startRoute = "example"
    NavHost(navController, startDestination = startRoute) {
        composable("example") {
            val viewModel = hiltViewModel<FirstViewModel>()
            FirstScreen(viewModel) {
                navController.navigate("second")
            }
        }
        composable("second") {
            val viewModel = hiltViewModel<FirstViewModel>()
            SecondScreen(viewModel)
        }
    }
}

@Composable
fun FirstScreen(viewModel: FirstViewModel, onNavigate: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState(initial = 0 to true)

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Count: ${uiState.first}")
        Text("Is Even: ${uiState.second}")

        Button(onClick = viewModel::increment) {
            Text("Increment")
        }

        Button(onClick = onNavigate) {
            Text("Go to next screen")
        }
    }
}

@Composable
fun SecondScreen(viewModel: FirstViewModel) {
    val uiState by viewModel.uiState.collectAsState(initial = 0 to true)

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Count: ${uiState.first}")
        Text("Is Even: ${uiState.second}")

        Button(onClick = viewModel::increment) {
            Text("Increment")
        }
    }
}
