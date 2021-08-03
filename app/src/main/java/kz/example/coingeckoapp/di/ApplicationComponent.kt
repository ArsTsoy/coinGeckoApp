package kz.example.coingeckoapp.di

import dagger.Component
import kz.example.coingeckoapp.CoinsListFragment
import kz.example.coingeckoapp.di.modules.NetworkModule
import javax.inject.Singleton

/**
 * @author Arslan Tsoy <t.me/arslantsoy> on 7/18/21
 */

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun provideIn(fragment: CoinsListFragment)
}