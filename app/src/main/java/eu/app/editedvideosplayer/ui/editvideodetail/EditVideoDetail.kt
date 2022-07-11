package eu.app.editedvideosplayer.ui.editvideodetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import eu.app.editedvideosplayer.entities.video.VideoItem
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditVideoDetail(videoItem: VideoItem? = null) {

    val editVideoDetailViewModel: EditVideoDetailViewModel = getViewModel()

    val scaffoldState = rememberBottomSheetScaffoldState()

    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.Gray,
                title = {
                    Text(text = "Edit video detail")
                }
            )
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                val context = LocalContext.current

                val exoPlayer = remember(context) {
                    ExoPlayer.Builder(context).build().apply {

                        val mediaItem = MediaItem.Builder()
                            .setUri(videoItem?.uri?.toUri())
                            .build()

                        setMediaItem(mediaItem)

                        playWhenReady = false
                        prepare()
                    }
                }

                DisposableEffect(
                    AndroidView(
                        factory = {
                            StyledPlayerView(it).apply {
                                player = exoPlayer
                            }
                        })
                ) {
                    onDispose { exoPlayer.release() }
                }
            }
        },
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(unbounded = true)
                    .wrapContentWidth(unbounded = false)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    EditVideoDetailBottomSheetListItem("Title", {})
                    EditVideoDetailBottomSheetListItem("Title", {})
                    EditVideoDetailBottomSheetListItem("Title", {})
                    EditVideoDetailBottomSheetListItem("Title", {})
                }
            }
        },
        sheetGesturesEnabled = true,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier
            .padding(0.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val (x, y) = dragAmount
                    when {
                        y > 0 -> {
                            scope.launch {
                                if (scaffoldState.bottomSheetState.isExpanded) {
                                    scaffoldState.bottomSheetState.collapse()
                                }
                            }
                        }
                        y < 0 -> {
                            scope.launch {
                                if (scaffoldState.bottomSheetState.isCollapsed) {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                        }
                    }
                }
            }
    )
}

@Composable
fun EditVideoDetailBottomSheetListItem(title: String, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .background(Color.Gray)
            .clickable { onItemClick() }
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Action")
    }
}