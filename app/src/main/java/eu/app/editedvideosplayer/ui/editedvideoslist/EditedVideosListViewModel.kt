package eu.app.editedvideosplayer.ui.editedvideoslist

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.app.editedvideosplayer.entities.video.VideoItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream

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
        dismissOutputDialog()
    }

    fun saveIntoRemoteSource(msg: String) {
        viewModelScope.launch {
            _toastMessage.emit(msg)
        }
    }

    fun removeVideo(fileName: String) {
        updateComposeState {
            copy(
                inputVideos = inputVideos.filter { it.fileName != fileName }
            )
        }
    }

    fun saveVideosToGallery(context: Context) {
        _state.value.inputVideos.forEach { video ->
            val fileName = "Edited_${System.currentTimeMillis()}.mp4"

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/Camera/")
                put(MediaStore.MediaColumns.TITLE, fileName)
            }

            val contentResolver = context.contentResolver

            val videoUri = contentResolver.insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues
            )

            videoUri?.let {
                val fis = FileInputStream(File(video.path))
                val fos = contentResolver.openOutputStream(videoUri)
                val buffer = ByteArray(8192)
                var len: Int
                while (fis.read(buffer).also { len = it } > 0) {
                    fos?.write(buffer, 0, len)
                }
                fos?.flush()
                fos?.close()
                fis.close()
                contentValues.clear()
                context.contentResolver.update(videoUri, contentValues, null, null)
            }
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