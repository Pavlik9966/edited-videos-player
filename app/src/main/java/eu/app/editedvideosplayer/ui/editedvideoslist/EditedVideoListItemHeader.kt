package eu.app.editedvideosplayer.ui.editedvideoslist

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import eu.app.editedvideosplayer.entities.video.VideoItem

@Composable
fun EditedVideoListItemHeader(navController: NavHostController, videoItem: VideoItem) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.background(Color.DarkGray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            color = Color.Black,
            maxLines = 1,
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f, fill = true),
            overflow = TextOverflow.Ellipsis,
            text = videoItem.fileName
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Gray,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .padding(top = 4.dp, end = 8.dp, bottom = 4.dp)
                .width(80.dp),
            onClick = {
                val json = Uri.encode(Gson().toJson(videoItem))
                navController.navigate("editedVideoDetail/$json")
            },
        ) {
            Text(text = "Detail")
        }
    }
}