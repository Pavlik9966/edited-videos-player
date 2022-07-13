package eu.app.editedvideosplayer.ui.inputvideossource

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import eu.app.editedvideosplayer.entities.video.VideoItem
import eu.app.editedvideosplayer.entities.video.Videos
import eu.app.editedvideosplayer.ui.common.getFileName
import org.koin.androidx.compose.getViewModel

@Composable
fun InputVideosSource(navController: NavHostController) {

    val inputVideosSourceViewModel: InputVideosSourceViewModel = getViewModel()

    Scaffold(
        backgroundColor = Color.LightGray,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = Color.Gray,
                title = {
                    Text(color = Color.Black, text = "Input videos source")
                })
        }, content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    inputVideosSourceViewModel
                        .toastMessage
                        .collect { msg ->
                            Toast.makeText(
                                context,
                                msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }

                var videoUrlList by remember {
                    mutableStateOf<MutableList<Uri>>(mutableListOf())
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetMultipleContents(),
                    onResult = { selectedVideos ->
                        videoUrlList = selectedVideos.toMutableList()

                        val videoItemsList = videoUrlList.map {
                            VideoItem(it.toString(), it.getFileName(context))
                        }

                        val videos = Videos(videoItemsList)

                        val json = Uri.encode(Gson().toJson(videos))
                        navController.navigate("inputVideosList/$json")
                    })

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Gray,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.width(128.dp),
                    onClick = {
                        launcher.launch("video/*")
                    },
                ) {
                    Icon(
                        contentDescription = null,
                        imageVector = Icons.Outlined.Home,
                        modifier = Modifier
                            .padding(end = 4.dp)
                    )
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                        Text(fontWeight = FontWeight.Bold, text = "Local")
                    }
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Gray,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.width(128.dp),
                    onClick = {
                        inputVideosSourceViewModel.loadFromRemoteSource("Input remote source...")
                    },
                ) {
                    Icon(
                        contentDescription = null,
                        imageVector = Icons.Outlined.Cloud,
                        modifier = Modifier
                            .padding(end = 4.dp)
                    )
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                        Text(fontWeight = FontWeight.Bold, text = "Remote")
                    }
                }
            }
        }
    )
}