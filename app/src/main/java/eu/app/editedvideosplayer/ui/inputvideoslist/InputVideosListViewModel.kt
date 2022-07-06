package eu.app.editedvideosplayer.ui.inputvideoslist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.app.editedvideosplayer.entities.video.VideoItem

class InputVideosListViewModel : ViewModel() {

    private val _state = mutableStateOf(InputVideosListState())

    val state: State<InputVideosListState> = _state

    fun addVideos(videos: List<VideoItem>) {
        updateComposeState {
            copy(
                inputVideos = videos
            )
        }
    }

    data class InputVideosListState(
        val inputVideos: List<VideoItem> = emptyList()
    )

    private fun updateComposeState(body: InputVideosListState.() -> InputVideosListState) {
        _state.value = body(_state.value)
    }
}