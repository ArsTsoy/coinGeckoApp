package kz.example.coingeckoapp.clean.presentation.recycler_views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.example.coingeckoapp.R
import kz.example.domain.models.CoinDomainModel

/**
 * @author Arslan Tsoy <t.me/arslantsoy> on 5/2/21
 */

class CoinListAdapter: RecyclerView.Adapter<CoinListVH>() {

    //region Vars
    private val items: MutableList<CoinDomainModel> = mutableListOf()
    //endregion

    //region Overridden Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListVH {
        return CoinListVH(
            LayoutInflater.from(parent.context).inflate(R.layout.coin_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CoinListVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
    //endregion

    fun refreshItems(items: List<CoinDomainModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<CoinDomainModel>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

}