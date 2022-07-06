package eu.app.editedvideosplayer.ui.inputvideoslist

import android.net.Uri
import android.view.LayoutInflater
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import eu.app.editedvideosplayer.entities.video.VideoItem
import org.koin.androidx.compose.getViewModel

@Composable
fun InputVideosList() {

    val inputVideosListViewModel: InputVideosListViewModel = getViewModel()

    Scaffold(
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = Color.Gray,
                title = {
                    Text(text = "Input videos list")
                })
        },
        floatingActionButton = {

            var videoUrlList by remember {
                mutableStateOf<MutableList<Uri>>(mutableListOf())
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetMultipleContents(),
                onResult = { selectedVideos ->
                    videoUrlList = selectedVideos.toMutableList()

                    val videoItemsList = videoUrlList.map {
                        VideoItem(it)
                    }

                    inputVideosListViewModel.addVideos(videoItemsList)
                })

            FloatingActionButton(
                backgroundColor = Color.Gray,
                onClick = {
                    launcher.launch("video/*")
                }) {
                Icon(
                    contentDescription = "",
                    imageVector = Icons.Default.Add
                )
            }
        },
        content = {
            LazyColumn(
                Modifier
                    .background(Color.LightGray)
            ) {
                items(inputVideosListViewModel.state.value.inputVideos) { video ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        InputVideoListItemHeader(video)
                        InputVideoListItem(video)
                    }
                }
            }
        }
    )
}