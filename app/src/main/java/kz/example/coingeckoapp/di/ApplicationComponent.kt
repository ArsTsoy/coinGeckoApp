package kz.example.coingeckoapp.di

import dagger.Component
import kz.example.coingeckoapp.di.modules.AppModule
import kz.example.coingeckoapp.di.modules.NetworkModule
import kz.example.coingeckoapp.ui.coin_list.CoinsListFragment
import kz.example.coingeckoapp.ui.coin_list.CoinsListVMFactory
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

    fun provideIn(fragment: CoinsListFragment)

    fun getCoinsListVMFactory(): CoinsListVMFactory
}