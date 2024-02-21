package ninja.bryansills.daggerscopingdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ninja.bryansills.daggerscopingdemo.daggerstuff.MegaDialog
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
            val viewModel = hiltViewModel<FirstViewModel, FirstViewModel.Factory> {
                it.create(10, 5)
            }
            FirstScreen(viewModel) {
                navController.navigate("dialogs")
            }
        }
        composable("second") { navBackStackEntry ->
            val count = 100 // imagine getting this from navBackStackEntry
            val viewModel = hiltViewModel<FirstViewModel, FirstViewModel.Factory> {
                it.create(count, 20)
            }
            SecondScreen(viewModel)
        }
        navigation(startDestination = "firstDialog", route = "dialogs") {
            dialog("firstDialog") { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("dialogs")
                }
                val dialogsViewModel = hiltViewModel<OtherViewModel>(parentEntry)
                FirstDialog(dialogsViewModel) {
                    navController.navigate("secondDialog")
                }
            }
            dialog("secondDialog") { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("dialogs")
                }
                val dialogsViewModel = hiltViewModel<OtherViewModel>(parentEntry)
                SecondDialog(dialogsViewModel)
            }
        }
        composable("megadialog") {
            MegaDialog()
        }
    }
}

@Composable
fun FirstScreen(viewModel: FirstViewModel, onNavigate: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState(initial = 0 to true)
    val lifecycleOwner = LocalLifecycleOwner.current

    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycleState) {
        Log.d("BLARG", "Lifecycle state now is: ${lifecycleState.name}")
    }

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
