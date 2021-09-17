package kz.example.coingeckoapp.di

import dagger.Component
import kz.example.coingeckoapp.clean.presentation.ui.coin_list.CoinsListVMFactory
import kz.example.data.di.AppModule
import kz.example.data.di.NetworkModule
import javax.inject.Singleton

/**
 * @author Arslan Tsoy <t.me/arslantsoy> on 7/18/21
 */

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        AppModule::class
    ]
)
interface ApplicationComponent {

    fun getCoinsListVMFactory(): CoinsListVMFactory
}