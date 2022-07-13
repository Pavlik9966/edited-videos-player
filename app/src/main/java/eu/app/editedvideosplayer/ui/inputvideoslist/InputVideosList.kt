package eu.app.editedvideosplayer.ui.inputvideoslist

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NavigateNext
import androidx.compose.runtime.Composable
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

    Scaffold(
        backgroundColor = Color.LightGray,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = Color.Gray,
                title = {
                    Text(color = Color.Black, text = "Input videos list")
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
                            InputVideoListItemHeader(navController, video)
                            InputVideoListItem(video)
                        }
                    }
                }
            }
            Column(
                Modifier
                    .background(Color.Transparent)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Gray,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.width(128.dp),
                        onClick = {
                            val json = Uri.encode(Gson().toJson(Videos(videos)))
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
            }
        }
    )
}