package eu.app.editedvideosplayer.ui.editedvideoslist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.app.editedvideosplayer.domain.usecase.DeleteRedundantFilesUseCase
import eu.app.editedvideosplayer.domain.usecase.SaveVideosIntoGalleryUseCase
import eu.app.editedvideosplayer.entities.video.VideoItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class EditedVideosListViewModel(
    private val videos: List<VideoItem>,
    private val saveVideosIntoGalleryUseCase: SaveVideosIntoGalleryUseCase,
    private val deleteRedundantFilesUseCase: DeleteRedundantFilesUseCase
) : ViewModel() {

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
        viewModelScope.launch {
            _state.value.run {
                saveVideosIntoGalleryUseCase(SaveVideosIntoGalleryUseCase.Params(getVideosForSave()))
                deleteRedundantFilesUseCase(DeleteRedundantFilesUseCase.Params(getVideosForDelete()))
            }
        }
    }

    fun saveIntoRemoteSource(msg: String) {
        viewModelScope.launch {
            _toastMessage.emit(msg)
            deleteRedundantFilesUseCase(DeleteRedundantFilesUseCase.Params(getVideosForDelete()))
        }
    }

    private fun getVideosForSave(): List<VideoItem> =
        _state.value.inputVideos.filter { it.saveItem }

    private fun getVideosForDelete(): List<VideoItem> =
        _state.value.inputVideos.filter { it.wasEdited }

    data class EditedVideosListState(
        val inputVideos: List<VideoItem> = emptyList(),
        val isOutputDialogOpen: Boolean = false,
        val videosForSave: List<VideoItem> = emptyList()
    )

    private fun updateComposeState(body: EditedVideosListState.() -> EditedVideosListState) {
        _state.value = body(_state.value)
    }
}