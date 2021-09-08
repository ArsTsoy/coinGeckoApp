package kz.example.coingeckoapp

import android.app.Application
import kz.example.coingeckoapp.di.ApplicationComponent
import kz.example.coingeckoapp.di.DaggerApplicationComponent
import kz.example.coingeckoapp.di.modules.AppModule
import kz.example.coingeckoapp.di.modules.NetworkModule

/**
 * @author Arslan Tsoy <t.me/arslantsoy> on 7/18/21
 */

class App: Application() {

    val componentDI: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()

    }
}