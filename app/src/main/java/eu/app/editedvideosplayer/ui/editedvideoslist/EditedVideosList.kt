package eu.app.editedvideosplayer.ui.editedvideoslist

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.app.editedvideosplayer.entities.video.VideoItem
import eu.app.editedvideosplayer.ui.outputvideosource.OutputVideoSourceDialog
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditedVideosList(navController: NavHostController, videos: List<VideoItem>) {

    val editedVideosListViewModel: EditedVideosListViewModel = getViewModel { parametersOf(videos) }

    Scaffold(
        backgroundColor = Color.LightGray,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = Color.Gray,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(color = Color.Black, text = "Edited videos list")
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.DarkGray,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier
                                .width(128.dp)
                                .padding(end = 8.dp),
                            onClick = { editedVideosListViewModel.openOutputDialog() },
                        ) {
                            Icon(
                                contentDescription = null,
                                imageVector = Icons.Outlined.Save,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(fontWeight = FontWeight.Bold, text = "Save")
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
                items(editedVideosListViewModel.state.value.inputVideos) { video ->
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
                            EditedVideoListItemHeader(navController, video)
                            EditedVideoListItem(video)
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .background(Color.Transparent)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Red,
                                        contentColor = Color.Black
                                    ),
                                    modifier = Modifier
                                        .padding(start = 8.dp, top = 60.dp)
                                        .width(50.dp),
                                    onClick = { editedVideosListViewModel.removeVideo(video.fileName) }
                                ) {
                                    Icon(
                                        contentDescription = null,
                                        imageVector = Icons.Outlined.Delete,
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Green,
                                        contentColor = Color.Black
                                    ),
                                    modifier = Modifier
                                        .padding(top = 60.dp, end = 8.dp)
                                        .width(50.dp),
                                    onClick = { }
                                ) {
                                    Icon(
                                        contentDescription = null,
                                        imageVector = Icons.Outlined.Check,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            BackHandler(enabled = true) { }

            val context = LocalContext.current

            LaunchedEffect(Unit) {
                editedVideosListViewModel
                    .toastMessage
                    .collect { msg ->
                        Toast.makeText(
                            context,
                            msg,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }

            if (editedVideosListViewModel.state.value.isOutputDialogOpen) {
                OutputVideoSourceDialog(
                    onDismiss = editedVideosListViewModel::dismissOutputDialog,
                    onLocalClick = editedVideosListViewModel::saveIntoLocalSource,
                    onRemoteClick = editedVideosListViewModel::saveIntoRemoteSource,
                    navController = navController
                )
            }
        })
}