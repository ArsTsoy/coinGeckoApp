package kz.example.coingeckoapp.retrofit

import kz.example.coingeckoapp.models.CoinModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Arslan Tsoy <t.me/arslantsoy> on 5/2/21
 */

interface CoinInterface {

//    api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=10&page=1&sparkline=false

    @GET("api/v3/coins/markets")
    fun getCoinList(
        @Query("vs_currency") vsCurrency: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("sparkline") sparkline: Boolean
    ): Call<List<CoinModel>>
    

}