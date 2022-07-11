package eu.app.editedvideosplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import eu.app.editedvideosplayer.entities.video.VideoItem
import eu.app.editedvideosplayer.entities.video.VideoItemNavType
import eu.app.editedvideosplayer.ui.editedvideodetail.EditedVideoDetail
import eu.app.editedvideosplayer.ui.editedvideoslist.EditedVideosList
import eu.app.editedvideosplayer.ui.editvideodetail.EditVideoDetail
import eu.app.editedvideosplayer.ui.inputvideoslist.InputVideosList
import eu.app.editedvideosplayer.ui.inputvideossource.InputVideosSource
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
    NavHost(navController = navController, startDestination = "inputVideosSource") {
        composable("inputVideosSource") {
            InputVideosSource(navController)
        }
        composable("inputVideosList") {
            InputVideosList(navController)
        }
        composable(
            "editVideoDetail/{video}",
            arguments = listOf(
                navArgument("video") {
                    type = VideoItemNavType()
                }
            )
        ) {
            val video = it.arguments?.getParcelable<VideoItem>("video")
            EditVideoDetail(video)
        }
        composable("editedVideosList") {
            EditedVideosList()
        }
        composable("editedVideoDetail") {
            EditedVideoDetail()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EditedVideosPlayerTheme {
    }
}