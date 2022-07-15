package eu.app.editedvideosplayer.ui.editvideodetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun EditVideoClipDialog(
    onCancelClick: () -> Unit,
    onOKClick: (Float, Float) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            elevation = 8.dp,
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {

            val startSeconds = remember {
                mutableStateOf("")
            }

            val duration = remember {
                mutableStateOf("")
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
                        text = "Set clip values"
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray,
                            cursorColor = Color.Black,
                            disabledLabelColor = Color.LightGray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        onValueChange = { startSeconds.value = it },
                        value = startSeconds.value,
                        isError = startSeconds.value.isEmpty(),
                        label = {
                            Text(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                text = "Start time (seconds)"
                            )
                        },
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray,
                            cursorColor = Color.Black,
                            disabledLabelColor = Color.LightGray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { duration.value = it },
                        value = duration.value,
                        isError = duration.value.isEmpty(),
                        label = {
                            Text(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                text = "Duration (seconds)"
                            )
                        },
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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
                        onClick = { onCancelClick() },
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
                            if (startSeconds.value.isNotEmpty() && duration.value.isNotEmpty()) {
                                onOKClick(
                                    startSeconds.value.toFloat(),
                                    duration.value.toFloat()
                                )
                                onDismiss()
                            }
                        },
                    ) {
                        Text(fontWeight = FontWeight.Bold, text = "OK")
                    }
                }
            }
        }
    }
}