package kz.example.data.di

import android.app.Application
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


private const val SHARED_PREFERENCES_NAME = "DEFAULT"

@Module
class AppModule(
    private val application: Application
) {

    @Singleton
    @Provides
    fun providesSharedPreferences(): SharedPreferences {
        return application.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Application.MODE_PRIVATE
        )
    }
}