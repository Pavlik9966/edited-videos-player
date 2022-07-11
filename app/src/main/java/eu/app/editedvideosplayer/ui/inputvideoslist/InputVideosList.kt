package eu.app.editedvideosplayer.ui.inputvideoslist

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavHostController
import com.google.gson.Gson
import eu.app.editedvideosplayer.entities.video.VideoItem
import eu.app.editedvideosplayer.ui.common.getFileName
import org.koin.androidx.compose.getViewModel

@Composable
fun InputVideosList(navController: NavHostController) {

    val context = LocalContext.current

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
                        VideoItem(it.toString(), it.getFileName(context))
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
        floatingActionButtonPosition = FabPosition.Center,
        content = {
            LazyColumn(
                Modifier
                    .background(Color.LightGray)
                    .fillMaxSize()
            ) {
                items(inputVideosListViewModel.state.value.inputVideos) { video ->
                    Card(
                        Modifier
                            .background(Color.LightGray)
                            .clickable {
                                val json = Uri.encode(Gson().toJson(video))
                                navController.navigate("editVideoDetail/$json")
                            }
                            .fillMaxSize()
                            .padding(end = 4.dp, start = 4.dp, top = 4.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            InputVideoListItemHeader(video)
                            InputVideoListItem(video)
                        }
                    }
                }
            }
        }
    )
}