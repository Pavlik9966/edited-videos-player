package eu.app.editedvideosplayer.ui.editvideodetail

import VideoHandle.EpEditor
import VideoHandle.EpVideo
import VideoHandle.OnEditorListener
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.app.editedvideosplayer.entities.video.VideoItem
import eu.app.editedvideosplayer.ui.common.getUriFromPath
import timber.log.Timber
import java.io.File

class EditVideoDetailViewModel(
    selectedVideo: VideoItem,
    filesDir: String
) : ViewModel() {

    private val _state = mutableStateOf(
        EditVideoDetailState(
            currentVideo = selectedVideo,
            editedVideoFile = File("$filesDir/edited_${selectedVideo.fileName}")
        )
    )

    val state: State<EditVideoDetailState> = _state

    private fun saveEditedVideo(epVideo: EpVideo) {
        _state.value.run {
            EpEditor.exec(
                epVideo,
                EpEditor.OutputOption(editedVideoFile.path),
                object : OnEditorListener {
                    override fun onSuccess() {
                        updateComposeState {
                            copy(
                                currentVideo = VideoItem(
                                    getUriFromPath(editedVideoFile.absolutePath).toString(),
                                    editedVideoFile.name,
                                    editedVideoFile.path,
                                    wasEdited = true
                                )
                            )
                        }
                        Timber.d("saveEditedVideo() -> onSuccess")
                        stopEdit()
                        videoWasEdited()
                    }

                    override fun onFailure() {
                        Timber.d("saveEditedVideo() -> onFailure")
                        stopEdit()
                    }

                    override fun onProgress(progress: Float) {
                        Timber.d("saveEditedVideo() -> onProgress -> Progress: $progress %")
                    }
                }
            )
        }
    }

    fun clipVideo(start: Float, duration: Float) {
        startEdit()

        val epVideo = EpVideo(_state.value.currentVideo.path).apply {
            clip(start, duration)
        }

        saveEditedVideo(epVideo)
    }

    fun reverseVideo() {
        startEdit()

        _state.value.run {
            EpEditor.reverse(
                currentVideo.path,
                editedVideoFile.path,
                true,
                true,
                object : OnEditorListener {
                    override fun onSuccess() {
                        updateComposeState {
                            copy(
                                currentVideo = VideoItem(
                                    getUriFromPath(editedVideoFile.absolutePath).toString(),
                                    editedVideoFile.name,
                                    editedVideoFile.path,
                                    wasEdited = true
                                )
                            )
                        }
                        Timber.d("reverseVideo() -> onSuccess")
                        stopEdit()
                        videoWasEdited()
                    }

                    override fun onFailure() {
                        Timber.d("reverseVideo() -> onFailure")
                        stopEdit()
                    }

                    override fun onProgress(progress: Float) {
                        Timber.d("reverseVideo() -> onProgress -> Progress: $progress %")
                    }
                }
            )
        }
    }

    fun rotateVideo() {
        startEdit()

        val epVideo = EpVideo(_state.value.currentVideo.path).apply {
            rotation(90, false)
        }

        saveEditedVideo(epVideo)
    }

    fun changeVideoPlaybackSpeed(speed: Float) {
        startEdit()

        _state.value.run {
            EpEditor.changePTS(
                currentVideo.path,
                editedVideoFile.path,
                speed,
                EpEditor.PTS.ALL,
                object : OnEditorListener {
                    override fun onSuccess() {
                        updateComposeState {
                            copy(
                                currentVideo = VideoItem(
                                    getUriFromPath(editedVideoFile.absolutePath).toString(),
                                    editedVideoFile.name,
                                    editedVideoFile.path,
                                    wasEdited = true
                                )
                            )
                        }
                        Timber.d("changeVideoPlaybackSpeed() -> onSuccess")
                        stopEdit()
                        videoWasEdited()
                    }

                    override fun onFailure() {
                        Timber.d("changeVideoPlaybackSpeed() -> onFailure")
                        stopEdit()
                    }

                    override fun onProgress(progress: Float) {
                        Timber.d("changeVideoPlaybackSpeed() -> onProgress -> Progress: $progress %")
                    }
                }
            )
        }
    }

    fun deleteEditedVideo() {
        _state.value.editedVideoFile.delete()
    }

    fun openVideoPlaybackSpeedDialog() {
        updateComposeState {
            copy(
                isVideoPlaybackSpeedDialogOpen = true
            )
        }
    }

    fun dismissVideoPlaybackSpeedDialog() {
        updateComposeState {
            copy(
                isVideoPlaybackSpeedDialogOpen = false
            )
        }
    }

    fun openVideoClipDialog() {
        updateComposeState {
            copy(
                isVideoClipDialogOpen = true
            )
        }
    }

    fun dismissVideoClipDialog() {
        updateComposeState {
            copy(
                isVideoClipDialogOpen = false
            )
        }
    }

    private fun startEdit() {
        updateComposeState {
            copy(
                isEdited = true
            )
        }
    }

    private fun stopEdit() {
        updateComposeState {
            copy(
                isEdited = false
            )
        }
    }

    fun saveClicked() {
        updateComposeState {
            copy(
                wasSaveClicked = true
            )
        }
    }

    fun openDiscardChangesDialog() {
        updateComposeState {
            copy(
                isDiscardChangesDialogOpen = true
            )
        }
    }

    fun dismissDiscardChangesDialog() {
        updateComposeState {
            copy(
                isDiscardChangesDialogOpen = false
            )
        }
    }

    fun videoWasEdited() {
        updateComposeState {
            copy(
                wasEdited = true
            )
        }
    }

    data class EditVideoDetailState(
        val currentVideo: VideoItem,
        val editedVideoFile: File,
        val isVideoPlaybackSpeedDialogOpen: Boolean = false,
        val isVideoClipDialogOpen: Boolean = false,
        val isEdited: Boolean = false,
        val wasSaveClicked: Boolean = false,
        val isDiscardChangesDialogOpen: Boolean = false,
        val wasEdited: Boolean = false
    )

    private fun updateComposeState(body: EditVideoDetailState.() -> EditVideoDetailState) {
        _state.value = body(_state.value)
    }
}