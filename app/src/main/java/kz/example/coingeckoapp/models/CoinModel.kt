package kz.example.coingeckoapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * @author Arslan Tsoy <t.me/arslantsoy> on 5/2/21
 */

class CoinModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("current_price")
    val price: Double,

    @SerializedName("market_cap")
    val marketCap: Long,

    @SerializedName("total_volume")
    val volume: Long,

    @SerializedName("image")
    val logo: String
)