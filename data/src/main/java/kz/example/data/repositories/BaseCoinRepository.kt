package kz.example.data.repositories

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kz.example.data.models.CoinDataModel
import kz.example.data.models.toDomain
import kz.example.data.retrofit.CoinInterface
import kz.example.domain.models.CoinDomainModel
import kz.example.domain.repositories.CoinRepository

import javax.inject.Inject

private const val COIN_LIST_KEY = "COIN_LIST_KEY"

// In MVVM -> model
class BaseCoinRepository
@Inject constructor(
    private val coinInterface: CoinInterface,
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : CoinRepository {

    override suspend fun getCoinList(page: Int): Flow<List<CoinDomainModel>> {
        return flow {
            if (page == 1) {
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
        }.map {
            it.map { dataModel ->
                dataModel.toDomain()
            }
        }

    }

    private suspend fun cacheCoinList(coins: List<CoinDataModel>) {
        sharedPreferences.edit()
            .putString(COIN_LIST_KEY, gson.toJson(coins))
            .apply()
    }

    private suspend fun getCachedCoinList(): List<CoinDataModel> {
        val cachedCoinListAsString = sharedPreferences
            .getString(COIN_LIST_KEY, "")

        if (cachedCoinListAsString.isNullOrBlank()) return listOf()
        return gson.fromJson(
            cachedCoinListAsString,
            object : TypeToken<List<CoinDataModel>>() {}.type
        )
    }
}