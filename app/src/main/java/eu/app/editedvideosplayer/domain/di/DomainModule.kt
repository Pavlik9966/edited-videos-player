package eu.app.editedvideosplayer.domain.di

import eu.app.editedvideosplayer.domain.usecase.DeleteRedundantFilesUseCase
import eu.app.editedvideosplayer.domain.usecase.SaveVideosIntoGalleryUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val useCasesModule = module {
    single {
        SaveVideosIntoGalleryUseCase(androidContext())
    }
    single {
        DeleteRedundantFilesUseCase()
    }
}

val domainModule = listOf(useCasesModule)