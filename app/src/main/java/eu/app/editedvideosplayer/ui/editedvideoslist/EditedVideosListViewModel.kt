package eu.app.editedvideosplayer.ui.editedvideoslist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.app.editedvideosplayer.entities.video.VideoItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class EditedVideosListViewModel(private val videos: List<VideoItem>) : ViewModel() {

    private val _state = mutableStateOf(EditedVideosListState())

    val state: State<EditedVideosListState> = _state

    private val _toastMessage = MutableSharedFlow<String>()

    val toastMessage = _toastMessage.asSharedFlow()

    init {
        updateComposeState {
            copy(
                inputVideos = videos
            )
        }
    }

    fun openOutputDialog() {
        updateComposeState {
            copy(
                isOutputDialogOpen = true
            )
        }
    }

    fun dismissOutputDialog() {
        updateComposeState {
            copy(
                isOutputDialogOpen = false
            )
        }
    }

    fun saveIntoLocalSource() {

    }

    fun saveIntoRemoteSource(msg: String) {
        viewModelScope.launch {
            _toastMessage.emit(msg)
        }
    }

    data class EditedVideosListState(
        val inputVideos: List<VideoItem> = emptyList(),
        val isOutputDialogOpen: Boolean = false
    )

    private fun updateComposeState(body: EditedVideosListState.() -> EditedVideosListState) {
        _state.value = body(_state.value)
    }
}