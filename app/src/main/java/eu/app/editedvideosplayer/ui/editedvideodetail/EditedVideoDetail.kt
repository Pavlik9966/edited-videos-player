package eu.app.editedvideosplayer.ui.editedvideodetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import eu.app.editedvideosplayer.entities.video.VideoItem
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditedVideoDetail(videoItem: VideoItem? = null) {

    val editedVideoDetailViewModel: EditedVideoDetailViewModel =
        getViewModel { parametersOf(videoItem) }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Gray,
                title = {
                    Text(color = Color.Black, text = "Edited video detail")
                })
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                val context = LocalContext.current

                val exoPlayer = remember(context) {
                    ExoPlayer.Builder(context).build().apply {

                        val mediaItem = MediaItem.Builder()
                            .setUri(editedVideoDetailViewModel.state.value.video.uri.toUri())
                            .build()

                        setMediaItem(mediaItem)

                        playWhenReady = true
                        prepare()
                    }
                }

                DisposableEffect(
                    AndroidView(
                        factory = {
                            StyledPlayerView(it).apply {
                                player = exoPlayer
                            }
                        },
                        modifier = Modifier.background(Color.Gray)
                    )
                ) {
                    onDispose { exoPlayer.release() }
                }
            }
        })
}