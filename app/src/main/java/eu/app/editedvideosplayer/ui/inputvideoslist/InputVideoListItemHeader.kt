package eu.app.editedvideosplayer.ui.inputvideoslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eu.app.editedvideosplayer.entities.video.VideoItem

@Composable
fun InputVideoListItemHeader(videoItem: VideoItem) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            maxLines = 1,
            modifier = Modifier.padding(start = 16.dp),
            overflow = TextOverflow.Ellipsis,
            text = videoItem.fileName
        )
    }
}