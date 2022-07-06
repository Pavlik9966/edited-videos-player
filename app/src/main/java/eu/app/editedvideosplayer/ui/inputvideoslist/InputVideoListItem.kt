package eu.app.editedvideosplayer.ui.inputvideoslist

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import eu.app.editedvideosplayer.entities.video.VideoItem

@Composable
fun InputVideoListItem(videoItem: VideoItem) {

    val context = LocalContext.current

    val playerView = StyledPlayerView(context)

    val exoPlayer = setupExoPlayer(context, videoItem)

    playerView.player = exoPlayer

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AndroidView(
            factory = {
                playerView
            },
            modifier = Modifier
                .background(Color.Yellow)
        )
    }
}

private fun setupExoPlayer(context: Context, videoItem: VideoItem): ExoPlayer =
    ExoPlayer.Builder(context).build().apply {

        val mediaItem = MediaItem.Builder()
            .setUri(videoItem.uri)
            .build()

        setMediaItem(mediaItem)

        playWhenReady = false
        prepare()
    }