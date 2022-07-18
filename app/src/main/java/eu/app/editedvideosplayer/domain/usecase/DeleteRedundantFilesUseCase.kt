package eu.app.editedvideosplayer.domain.usecase

import eu.app.editedvideosplayer.entities.video.VideoItem
import timber.log.Timber
import java.io.File

class DeleteRedundantFilesUseCase : suspend (DeleteRedundantFilesUseCase.Params) -> Unit {

    override suspend fun invoke(params: Params) {
        params.videos.forEach {
            val file = File(it.path)
            file.delete()
            Timber.d("TIMBER: DeleteRedundantFilesUseCase() -> deleted")
        }
    }

    data class Params(val videos: List<VideoItem>)
}