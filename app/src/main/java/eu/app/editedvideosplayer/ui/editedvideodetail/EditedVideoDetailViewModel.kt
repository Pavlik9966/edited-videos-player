package eu.app.editedvideosplayer.ui.editedvideodetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.app.editedvideosplayer.entities.video.VideoItem

class EditedVideoDetailViewModel(selectedVideo: VideoItem) : ViewModel() {

    private val _state = mutableStateOf(EditedVideoDetailState(video = selectedVideo))

    val state: State<EditedVideoDetailState> = _state

    data class EditedVideoDetailState(
        val video: VideoItem
    )

    private fun updateComposeState(body: EditedVideoDetailState.() -> EditedVideoDetailState) {
        _state.value = body(_state.value)
    }
}