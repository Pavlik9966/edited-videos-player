package eu.app.editedvideosplayer.ui.di

import eu.app.editedvideosplayer.ui.inputvideoslist.InputVideosListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module {
    viewModel {
        InputVideosListViewModel()
    }
}

val uiModule = listOf(viewModelModule)