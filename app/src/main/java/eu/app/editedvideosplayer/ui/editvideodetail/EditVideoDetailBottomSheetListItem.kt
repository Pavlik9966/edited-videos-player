package eu.app.editedvideosplayer.ui.editvideodetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun EditVideoDetailBottomSheetListItem(
    imageVector: ImageVector,
    title: String,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(Color.DarkGray)
            .clickable { onItemClick() }
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            contentDescription = null,
            imageVector = imageVector,
            modifier = Modifier.padding(end = 4.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(color = Color.Black, fontWeight = FontWeight.Bold, text = title)
    }
}