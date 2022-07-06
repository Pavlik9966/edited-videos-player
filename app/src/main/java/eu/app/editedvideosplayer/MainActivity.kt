package eu.app.editedvideosplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.app.editedvideosplayer.ui.inputvideoslist.InputVideosList
import eu.app.editedvideosplayer.ui.theme.EditedVideosPlayerTheme
import eu.app.editedvideosplayer.ui.utils.PermissionRequest
import org.koin.android.BuildConfig
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setContent {

            val navController = rememberNavController()

            EditedVideosPlayerTheme {
                Navigation(navController = navController)
                PermissionRequest()
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "inputVideosList") {
        composable("inputVideosList") {
            InputVideosList()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EditedVideosPlayerTheme {

    }
}