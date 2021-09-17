package kz.example.data.models

import com.google.gson.annotations.SerializedName
import kz.example.domain.models.CoinDomainModel

/**
 * @author Arslan Tsoy <t.me/arslantsoy> on 5/2/21
 */

class CoinDataModel(
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

fun CoinDataModel.toDomain(): CoinDomainModel {
    return CoinDomainModel(
        id, name, price, marketCap, volume, logo
    )
}