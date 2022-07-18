package eu.app.editedvideosplayer.ui.inputvideoslist

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import eu.app.editedvideosplayer.entities.video.VideoItem
import eu.app.editedvideosplayer.entities.video.Videos
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun InputVideosList(navController: NavHostController, videos: List<VideoItem>) {

    val inputVideosListViewModel: InputVideosListViewModel = getViewModel { parametersOf(videos) }

    val screenResultState = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<VideoItem>("video_item")
        ?.observeAsState()

    screenResultState?.value?.let {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.remove<VideoItem>("video_item")
        LaunchedEffect(key1 = it, block = {
            inputVideosListViewModel.updateVideos(it)
        })
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = Color.Gray,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(color = Color.Black, text = "Input videos list")
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.DarkGray,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier
                                .width(128.dp)
                                .padding(end = 8.dp),
                            onClick = {
                                val json = Uri.encode(
                                    Gson().toJson(Videos(inputVideosListViewModel.state.value.inputVideos))
                                )
                                navController.navigate("editedVideosList/$json")
                            },
                        ) {
                            Icon(
                                contentDescription = null,
                                imageVector = Icons.Outlined.NavigateNext,
                                modifier = Modifier
                                    .padding(end = 4.dp)
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(fontWeight = FontWeight.Bold, text = "Next")
                            }
                        }
                    }
                })
        },
        content = {
            LazyColumn(
                Modifier
                    .background(Color.LightGray)
                    .fillMaxSize()
            ) {
                items(inputVideosListViewModel.state.value.inputVideos) { video ->
                    Card(
                        elevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 4.dp, start = 4.dp, top = 4.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.background(Color.Gray),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            InputVideoListItemHeader(
                                navController,
                                video,
                                editClicked = {
                                    inputVideosListViewModel.isEdited(video)
                                }
                            )
                            InputVideoListItem(video)
                        }
                    }
                }
            }
            BackHandler(enabled = true) { }
        }
    )
}