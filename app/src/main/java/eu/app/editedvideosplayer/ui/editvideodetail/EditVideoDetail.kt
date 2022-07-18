package eu.app.editedvideosplayer.ui.editvideodetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import eu.app.editedvideosplayer.entities.video.VideoItem
import eu.app.editedvideosplayer.ui.confirmationdialog.ConfirmationDialog
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditVideoDetail(navController: NavHostController, videoItem: VideoItem? = null) {

    val context = LocalContext.current

    val editVideoDetailViewModel: EditVideoDetailViewModel =
        getViewModel { parametersOf(videoItem, context.filesDir.absolutePath) }

    val scaffoldState = rememberBottomSheetScaffoldState()

    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.Gray,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(color = Color.Black, text = "Edit video detail")
                        Spacer(modifier = Modifier.weight(1f))
                        if (editVideoDetailViewModel.state.value.isEdited) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(end = 16.dp),
                                color = Color.Red,
                                strokeWidth = 6.dp
                            )
                        }
                    }
                }
            )
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                val exoPlayer = remember(context) {
                    ExoPlayer.Builder(context).build().apply {

                        val mediaItem = MediaItem.Builder()
                            .setUri(editVideoDetailViewModel.state.value.currentVideo.uri.toUri())
                            .build()

                        setMediaItem(mediaItem)

                        playWhenReady = true
                        prepare()
                    }
                }

                LaunchedEffect(
                    key1 = editVideoDetailViewModel.state.value,
                    block = {
                        exoPlayer.setMediaItem(
                            MediaItem
                                .Builder()
                                .setUri(editVideoDetailViewModel.state.value.currentVideo.uri.toUri())
                                .build()
                        )
                    }
                )

                DisposableEffect(
                    AndroidView(
                        factory = {
                            StyledPlayerView(it).apply {
                                player = exoPlayer
                            }
                        },
                        modifier = Modifier.background(Color.Gray)
                    )
                ) {
                    onDispose { exoPlayer.release() }
                }
            }
        },
        sheetContent = {
            if (!editVideoDetailViewModel.state.value.isEdited) {
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
                        EditVideoDetailBottomSheetListItem(
                            Icons.Outlined.Save,
                            "Save",
                            editVideoDetailViewModel::saveClicked
                        )
                        if (!editVideoDetailViewModel.state.value.wasEdited) {
                            Divider()
                            EditVideoDetailBottomSheetListItem(
                                Icons.Outlined.Edit,
                                "Clip",
                                editVideoDetailViewModel::openVideoClipDialog
                            )
                            Divider()
                            EditVideoDetailBottomSheetListItem(
                                Icons.Outlined.Edit,
                                "Reverse",
                                editVideoDetailViewModel::reverseVideo
                            )
                            Divider()
                            EditVideoDetailBottomSheetListItem(
                                Icons.Outlined.Edit,
                                "Rotation 90°",
                                editVideoDetailViewModel::rotateVideo
                            )
                            Divider()
                            EditVideoDetailBottomSheetListItem(
                                Icons.Outlined.Edit,
                                "Playback speed",
                                editVideoDetailViewModel::openVideoPlaybackSpeedDialog
                            )
                        }
                    }
                }
            }

            if (editVideoDetailViewModel.state.value.isVideoClipDialogOpen) {
                EditVideoClipDialog(
                    onCancelClick = editVideoDetailViewModel::dismissVideoClipDialog,
                    onOKClick = editVideoDetailViewModel::clipVideo,
                    onDismiss = editVideoDetailViewModel::dismissVideoClipDialog
                )
            }

            if (editVideoDetailViewModel.state.value.isVideoPlaybackSpeedDialogOpen) {
                EditVideoPlaybackSpeedDialog(
                    onCancelClick = editVideoDetailViewModel::dismissVideoPlaybackSpeedDialog,
                    onOKClick = editVideoDetailViewModel::changeVideoPlaybackSpeed,
                    onDismiss = editVideoDetailViewModel::dismissVideoPlaybackSpeedDialog
                )
            }

            BackHandler(enabled = true) {
                if (!editVideoDetailViewModel.state.value.isEdited) {
                    if (editVideoDetailViewModel.state.value.wasSaveClicked) {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("video_item", editVideoDetailViewModel.state.value.currentVideo)
                        navController.popBackStack()
                    } else {
                        if (editVideoDetailViewModel.state.value.wasEdited) {
                            editVideoDetailViewModel.openDiscardChangesDialog()
                        } else {
                            navController.popBackStack()
                        }
                    }
                }
            }

            if (editVideoDetailViewModel.state.value.isDiscardChangesDialogOpen) {
                ConfirmationDialog(
                    onCancelClick = editVideoDetailViewModel::dismissDiscardChangesDialog,
                    onOKClick = {
                        editVideoDetailViewModel.deleteEditedVideo()
                        navController.popBackStack()
                    },
                    onDismiss = editVideoDetailViewModel::dismissDiscardChangesDialog,
                    text = "Do you really want to leave this without saving?"
                )
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