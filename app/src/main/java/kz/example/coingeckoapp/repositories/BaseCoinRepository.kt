package kz.example.coingeckoapp.repositories

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.example.coingeckoapp.models.CoinModel
import kz.example.coingeckoapp.retrofit.CoinInterface
import javax.inject.Inject

private const val COIN_LIST_KEY = "COIN_LIST_KEY"

// In MVVM -> model
class BaseCoinRepository
@Inject constructor(
    private val coinInterface: CoinInterface,
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : CoinRepository {

    override suspend fun getCoinList(page: Int): Flow<List<CoinModel>> {
        return flow {
            if(page == 1) {
                emit(getCachedCoinList())
            }
            kotlinx.coroutines.delay(10_000)
            val list = coinInterface.getCoinList(
                "usd",
                "market_cap_desc",
                20,
                page,
                false
            )
            if (page == 1) {
                cacheCoinList(list)
            }
            emit(list)
        }

    }

    private suspend fun cacheCoinList(coins: List<CoinModel>) {
        sharedPreferences.edit()
            .putString(COIN_LIST_KEY, gson.toJson(coins))
            .apply()
    }

    private suspend fun getCachedCoinList(): List<CoinModel> {
        val cachedCoinListAsString = sharedPreferences
            .getString(COIN_LIST_KEY, "")

        if (cachedCoinListAsString.isNullOrBlank()) return listOf()
        return gson.fromJson(
            cachedCoinListAsString,
            object : TypeToken<List<CoinModel>>() {}.type
        )
    }
}