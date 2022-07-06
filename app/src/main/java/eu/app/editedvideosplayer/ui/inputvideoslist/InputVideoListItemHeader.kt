package eu.app.editedvideosplayer.ui.inputvideoslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import eu.app.editedvideosplayer.entities.video.VideoItem

@Composable
fun InputVideoListItemHeader(videoItem: VideoItem) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = videoItem.uri.toString())
    }
}