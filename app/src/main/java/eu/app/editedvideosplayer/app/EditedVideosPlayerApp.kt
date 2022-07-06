package eu.app.editedvideosplayer.app

import android.app.Application
import eu.app.editedvideosplayer.app.di.app
import eu.app.editedvideosplayer.data.di.dataModule
import eu.app.editedvideosplayer.domain.di.domainModule
import eu.app.editedvideosplayer.infrastructure.di.infrastructureModule
import eu.app.editedvideosplayer.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EditedVideosPlayerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EditedVideosPlayerApp)
            modules(app + dataModule + domainModule + infrastructureModule + uiModule)
        }
    }
}