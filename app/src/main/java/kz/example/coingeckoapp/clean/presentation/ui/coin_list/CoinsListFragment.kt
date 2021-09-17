package kz.example.coingeckoapp.clean.presentation.ui.coin_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import kz.example.coingeckoapp.clean.presentation.App
import kz.example.coingeckoapp.R
import kz.example.coingeckoapp.clean.presentation.recycler_views.CoinListAdapter


class CoinsListFragment : Fragment(R.layout.fragment_coins_list) {

    //region Vars
    private val coinAdapter: CoinListAdapter = CoinListAdapter()
    private var swipeLayout: SwipeRefreshLayout? = null

    private val viewModel: CoinsListVM by viewModels {
        (requireActivity().application as App).componentDI.getCoinsListVMFactory()
    }
    //endregion

    //region Overridden Methods
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareUI()
    }
    //endregion

    //region Support
    private fun prepareUI() {
        setupSwipeRefreshLayout()
        setupRV()

        setupVMObservers()
    }

    private fun setupSwipeRefreshLayout() {
        swipeLayout = view?.findViewById(R.id.swipeRefreshLayout)
        swipeLayout?.isRefreshing = true
        swipeLayout?.setOnRefreshListener {
            viewModel.onRefreshPulled()
        }
    }

    private fun setupRV() {

        val rvCoins = view?.findViewById<RecyclerView>(R.id.rvCoinList)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        rvCoins?.layoutManager = linearLayoutManager
        rvCoins?.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    Log.i("myCoins", "onScrolled: dx: $dx, dy: $dy")
                    val visibleItemCount = linearLayoutManager.childCount
                    val totalItemCount = linearLayoutManager.itemCount
                    val firstVisibleItemPosition =
                        linearLayoutManager.findFirstVisibleItemPosition()

                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        viewModel.getNextPage()
                    }
                }
            })
        rvCoins?.adapter = coinAdapter
    }

    private fun setupVMObservers() {
        viewModel.observeIsLoading().observe(this.viewLifecycleOwner, {
            swipeLayout?.isRefreshing = it
        })

        viewModel.observeCoinList().observe(this.viewLifecycleOwner, {
            coinAdapter.refreshItems(it)
        })
    }
    //endregion
}
