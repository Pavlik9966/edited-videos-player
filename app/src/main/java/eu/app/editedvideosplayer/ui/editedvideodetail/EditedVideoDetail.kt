package eu.app.editedvideosplayer.ui.editedvideodetail

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.koin.androidx.compose.getViewModel

@Composable
fun EditedVideoDetail() {

    val editedVideoDetailViewModel: EditedVideoDetailViewModel = getViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Gray,
                title = {
                    Text(text = "Edited video detail")
                })
        },
        content = {

        })
}