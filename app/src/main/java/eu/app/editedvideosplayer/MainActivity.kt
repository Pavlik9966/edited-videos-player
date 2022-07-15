package eu.app.editedvideosplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import eu.app.editedvideosplayer.entities.video.VideoItem
import eu.app.editedvideosplayer.entities.video.VideoItemNavType
import eu.app.editedvideosplayer.entities.video.Videos
import eu.app.editedvideosplayer.entities.video.VideosNavType
import eu.app.editedvideosplayer.ui.common.useNonBreakingSpace
import eu.app.editedvideosplayer.ui.editedvideodetail.EditedVideoDetail
import eu.app.editedvideosplayer.ui.editedvideoslist.EditedVideosList
import eu.app.editedvideosplayer.ui.editvideodetail.EditVideoDetail
import eu.app.editedvideosplayer.ui.inputvideoslist.InputVideosList
import eu.app.editedvideosplayer.ui.inputvideossource.InputVideosSource
import eu.app.editedvideosplayer.ui.theme.EditedVideosPlayerTheme
import eu.app.editedvideosplayer.ui.utils.PermissionRequest
import timber.log.Timber
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())

        setContent {

            val navController = rememberNavController()

            EditedVideosPlayerTheme {
                Navigation(navController = navController)
                PermissionRequest()
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "inputVideosSource") {
        composable("inputVideosSource") {
            InputVideosSource(navController)
        }
        composable(
            "inputVideosList/{videos}",
            arguments = listOf(
                navArgument("videos") {
                    type = VideosNavType()
                }
            )
        ) {
            val videos = it.arguments?.getParcelable<Videos>("videos")
            InputVideosList(
                navController = navController,
                videos = videos?.videos ?: emptyList()
            )
        }
        composable(
            "editVideoDetail/{video}",
            arguments = listOf(
                navArgument("video") {
                    type = VideoItemNavType()
                }
            )
        ) {
            val video = it.arguments?.getParcelable<VideoItem>("video")
            EditVideoDetail(
                navController = navController,
                videoItem = video
            )
        }
        composable(
            "editedVideosList/{videos}",
            arguments = listOf(
                navArgument("videos") {
                    type = VideosNavType()
                }
            )
        ) {
            val videos = it.arguments?.getParcelable<Videos>("videos")
            EditedVideosList(
                navController = navController,
                videos = videos?.videos ?: emptyList()
            )
        }
        composable("editedVideoDetail/{video}",
            arguments = listOf(
                navArgument("video") {
                    type = VideoItemNavType()
                }
            )
        ) {
            val video = it.arguments?.getParcelable<VideoItem>("video")
            EditedVideoDetail(
                videoItem = video
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EditedVideosPlayerTheme {
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
                        text = "Set video playback speed"
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    var sliderValue by remember {
                        mutableStateOf(1f)
                    }

                    Text(
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp),
                        text = String.format(locale = Locale.ENGLISH, "%.2fx", sliderValue)
                    )
                    Slider(
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Black,
                            activeTrackColor = Color.Black
                        ),
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        onValueChange = { sliderValue = it },
                        value = sliderValue,
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
                        onClick = { },
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
                        onClick = { },
                    ) {
                        Text(fontWeight = FontWeight.Bold, text = "OK")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview_() {
    EditedVideosPlayerTheme {
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
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .width(200.dp),
                        text = "Are you sure you?".useNonBreakingSpace(),
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
                        onClick = { },
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
                        onClick = { },
                    ) {
                        Text(fontWeight = FontWeight.Bold, text = "OK")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview__() {
    EditedVideosPlayerTheme {
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
                        text = "Set clip values"
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    var startSeconds by remember {
                        mutableStateOf("")
                    }

                    var duration by remember {
                        mutableStateOf("")
                    }

                    OutlinedTextField(
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray,
                            cursorColor = Color.Black,
                            disabledLabelColor = Color.LightGray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { startSeconds = it },
                        value = startSeconds,
                        label = {
                            Text(text = "Start time (seconds)")
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .padding(start = 16.dp, end = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray,
                            cursorColor = Color.Black,
                            disabledLabelColor = Color.LightGray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { duration = it },
                        value = duration,
                        label = {
                            Text(text = "Duration (seconds)")
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .padding(start = 16.dp, end = 16.dp)
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
                        onClick = { },
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
                        onClick = { },
                    ) {
                        Text(fontWeight = FontWeight.Bold, text = "OK")
                    }
                }
            }
        }
    }
}