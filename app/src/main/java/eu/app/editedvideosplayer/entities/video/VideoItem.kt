package eu.app.editedvideosplayer.entities.video

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoItem(
    val uri: String,
    val fileName: String,
    val path: String,
    var saveItem: Boolean = true,
    var wasEdited: Boolean = false
) : Parcelable

class VideoItemNavType : NavType<VideoItem>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): VideoItem? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): VideoItem {
        return Gson().fromJson(value, VideoItem::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: VideoItem) {
        bundle.putParcelable(key, value)
    }
}