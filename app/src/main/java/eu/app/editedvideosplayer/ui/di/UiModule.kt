package eu.app.editedvideosplayer.ui.di

import eu.app.editedvideosplayer.entities.video.VideoItem
import eu.app.editedvideosplayer.ui.editedvideodetail.EditedVideoDetailViewModel
import eu.app.editedvideosplayer.ui.editedvideoslist.EditedVideosListViewModel
import eu.app.editedvideosplayer.ui.editvideodetail.EditVideoDetailViewModel
import eu.app.editedvideosplayer.ui.inputvideoslist.InputVideosListViewModel
import eu.app.editedvideosplayer.ui.inputvideossource.InputVideosSourceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel { (videos: List<VideoItem>) ->
        InputVideosListViewModel(videos)
    }
    viewModel {
        InputVideosSourceViewModel()
    }
    viewModel {
        EditVideoDetailViewModel()
    }
    viewModel { (videos: List<VideoItem>) ->
        EditedVideosListViewModel(videos)
    }
    viewModel {
        EditedVideoDetailViewModel()
    }
}

val uiModule = listOf(viewModelModule)