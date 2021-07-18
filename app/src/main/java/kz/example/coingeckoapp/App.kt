package kz.example.coingeckoapp

import android.app.Application
import kz.example.coingeckoapp.di.ApplicationComponent
import kz.example.coingeckoapp.di.DaggerApplicationComponent

/**
 * @author Arslan Tsoy <t.me/arslantsoy> on 7/18/21
 */

class App: Application() {

    val componentDI: ApplicationComponent = DaggerApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()

    }
}