package eu.app.editedvideosplayer.ui.confirmationdialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import eu.app.editedvideosplayer.ui.common.useNonBreakingSpace

@Composable
fun ConfirmationDialog(
    onCancelClick: () -> Unit,
    onOKClick: () -> Unit,
    onDismiss: () -> Unit,
    text: String
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            elevation = 8.dp,
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
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
                        text = "Please confirm"
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.width(200.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .width(200.dp),
                        text = text.useNonBreakingSpace(),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Gray,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .width(96.dp),
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
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .width(96.dp),
                        onClick = { onOKClick() },
                    ) {
                        Text(fontWeight = FontWeight.Bold, text = "OK")
                    }
                }
            }
        }
    }
}