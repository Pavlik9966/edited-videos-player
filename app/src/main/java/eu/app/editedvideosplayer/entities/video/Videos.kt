package eu.app.editedvideosplayer.entities.video

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class Videos(
    val videos: List<VideoItem>
) : Parcelable

class VideosNavType : NavType<Videos>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): Videos? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Videos {
        return Gson().fromJson(value, Videos::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Videos) {
        bundle.putParcelable(key, value)
    }
}