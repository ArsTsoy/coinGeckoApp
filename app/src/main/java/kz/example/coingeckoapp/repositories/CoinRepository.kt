package kz.example.coingeckoapp.repositories

import kotlinx.coroutines.flow.Flow
import kz.example.coingeckoapp.models.CoinModel


interface CoinRepository {
    
    suspend fun getCoinList(
        page: Int
    ): Flow<List<CoinModel>>
}