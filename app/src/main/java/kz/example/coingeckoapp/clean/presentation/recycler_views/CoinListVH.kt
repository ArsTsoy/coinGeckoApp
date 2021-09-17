package kz.example.coingeckoapp.clean.presentation.recycler_views

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.example.coingeckoapp.R
import kz.example.domain.models.CoinDomainModel

/**
 * @author Arslan Tsoy <t.me/arslantsoy> on 5/2/21
 */

class CoinListVH(
    private val itemView: View
): RecyclerView.ViewHolder(itemView) {


    fun bind(model: CoinDomainModel) {
        with(itemView) {
            val ivLogo = findViewById<ImageView>(R.id.ivCoin)
            val tvPrice = findViewById<TextView>(R.id.tvCoinPrice)
            val tvMarketCap = findViewById<TextView>(R.id.tvCoinMarketCap)
            val tvName = findViewById<TextView>(R.id.tvCoinName)
            val tvVolume = findViewById<TextView>(R.id.tvCoinVolume)

            tvPrice.text = model.price.toString()
            tvMarketCap.text = model.marketCap.toString()
            tvName.text = model.name
            tvVolume.text = model.volume.toString()

            Glide.with(itemView)
                .load(model.logo)
                .into(ivLogo)
        }
    }

}