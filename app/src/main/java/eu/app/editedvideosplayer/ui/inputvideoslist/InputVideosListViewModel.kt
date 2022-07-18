package eu.app.editedvideosplayer.ui.inputvideoslist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.app.editedvideosplayer.entities.video.VideoItem

class InputVideosListViewModel(private val videos: List<VideoItem>) : ViewModel() {

    private val _state = mutableStateOf(InputVideosListState())

    val state: State<InputVideosListState> = _state

    init {
        updateComposeState {
            copy(
                inputVideos = videos
            )
        }
    }

    fun updateVideos(video: VideoItem) {
        var updated: MutableList<VideoItem>

        _state.value.run {
            updated = inputVideos.toMutableList().apply {
                removeIf { it.path == editedVideo?.path }
                add(video)
            }
        }

        updateComposeState {
            copy(
                inputVideos = updated.toList(),
                editedVideo = null
            )
        }
    }

    fun isEdited(video: VideoItem) {
        updateComposeState {
            copy(
                editedVideo = video
            )
        }
    }

    data class InputVideosListState(
        val inputVideos: List<VideoItem> = emptyList(),
        val editedVideo: VideoItem? = null
    )

    private fun updateComposeState(body: InputVideosListState.() -> InputVideosListState) {
        _state.value = body(_state.value)
    }
}