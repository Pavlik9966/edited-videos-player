package eu.app.editedvideosplayer.ui.inputvideossource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class InputVideosSourceViewModel : ViewModel() {

    private val _toastMessage = MutableSharedFlow<String>()

    val toastMessage = _toastMessage.asSharedFlow()

    fun loadFromRemoteSource(msg: String) {
        viewModelScope.launch {
            _toastMessage.emit(msg)
        }
    }
}