package kz.example.data.retrofit

import kz.example.data.models.CoinDataModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Arslan Tsoy <t.me/arslantsoy> on 5/2/21
 */

interface CoinInterface {

    @GET("api/v3/coins/markets")
    suspend fun getCoinList(
        @Query("vs_currency") vsCurrency: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("sparkline") sparkline: Boolean
    ): List<CoinDataModel>
    

}