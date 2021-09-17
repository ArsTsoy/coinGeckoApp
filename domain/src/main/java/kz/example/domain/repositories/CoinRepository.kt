package kz.example.domain.repositories

import kotlinx.coroutines.flow.Flow
import kz.example.domain.models.CoinDomainModel


interface CoinRepository {
    
    suspend fun getCoinList(
        page: Int
    ): Flow<List<CoinDomainModel>>
}