package eu.app.editedvideosplayer.domain.usecase

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import eu.app.editedvideosplayer.entities.video.VideoItem
import timber.log.Timber
import java.io.File
import java.io.FileInputStream

class SaveVideosIntoGalleryUseCase(
    private val context: Context
) : suspend (SaveVideosIntoGalleryUseCase.Params) -> Unit {

    override suspend fun invoke(params: Params) {
        params.videos.forEach { video ->
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
                Timber.d("TIMBER: SaveVideosIntoGalleryUseCase() -> saved")
            }
        }
    }

    data class Params(val videos: List<VideoItem>)
}