package eu.app.editedvideosplayer.ui.inputvideoslist

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import eu.app.editedvideosplayer.entities.video.VideoItem
import timber.log.Timber

@Composable
fun InputVideoListItem(videoItem: VideoItem) {

    val context = LocalContext.current

    val exoPlayer = remember(context) {
        setupExoPlayer(context, videoItem)
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.background(Color.Gray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DisposableEffect(
            AndroidView(
                factory = {
                    StyledPlayerView(it).apply {
                        hideController()
                        player = exoPlayer
                        useController = false
                    }
                },
                modifier = Modifier
                    .background(Color.Gray)
                    .height(300.dp)
            )
        ) {
            onDispose { exoPlayer.release() }
        }
        Timber.d(videoItem.path)
    }
}

private fun setupExoPlayer(context: Context, videoItem: VideoItem): ExoPlayer =
    ExoPlayer.Builder(context).build().apply {

        val mediaItem = MediaItem.Builder()
            .setUri(videoItem.uri.toUri())
            .build()

        setMediaItem(mediaItem)

        playWhenReady = true
        prepare()
        repeatMode = Player.REPEAT_MODE_ONE
    }