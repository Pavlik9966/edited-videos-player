package eu.app.editedvideosplayer.ui.editvideodetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.*

@Composable
fun EditVideoPlaybackSpeedDialog(
    onCancelClick: () -> Unit,
    onOKClick: (Float) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            elevation = 8.dp,
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {

            val sliderValue = remember {
                mutableStateOf(1f)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.DarkGray)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp),
                        text = "Set video playback speed"
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp),
                        text = String.format(locale = Locale.ENGLISH, "%.2f", sliderValue.value)
                    )
                    Slider(
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Black,
                            activeTrackColor = Color.Black
                        ),
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        onValueChange = { sliderValue.value = it },
                        value = sliderValue.value,
                        valueRange = 0.25f..4f
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Gray,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.width(96.dp),
                        onClick = {
                            onCancelClick()
                        },
                    ) {
                        Text(fontWeight = FontWeight.Bold, text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Gray,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.width(96.dp),
                        onClick = {
                            onOKClick(sliderValue.value)
                            onDismiss()
                        },
                    ) {
                        Text(fontWeight = FontWeight.Bold, text = "OK")
                    }
                }
            }
        }
    }
}