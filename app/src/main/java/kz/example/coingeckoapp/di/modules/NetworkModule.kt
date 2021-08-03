package kz.example.coingeckoapp.di.modules

import com.jaredsburrows.retrofit2.adapter.synchronous.SynchronousCallAdapterFactory
import dagger.Module
import dagger.Provides
import kz.example.coingeckoapp.retrofit.CoinInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Arslan Tsoy <t.me/arslantsoy> on 7/18/21
 */

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/")
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()
            )
            .addCallAdapterFactory(SynchronousCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideCoinInterface(retrofit: Retrofit): CoinInterface {
        return retrofit.create(CoinInterface::class.java)
    }


}