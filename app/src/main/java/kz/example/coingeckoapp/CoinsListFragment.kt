package kz.example.coingeckoapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kz.example.coingeckoapp.recycler_views.CoinListAdapter
import kz.example.coingeckoapp.retrofit.CoinInterface
import kz.example.coingeckoapp.test_constructor_injection.ClassA
import kz.example.coingeckoapp.test_constructor_injection.ClassC
import javax.inject.Inject


class CoinsListFragment : Fragment() {

    //region DI
    @Inject
    lateinit var coinInterface: CoinInterface

    @Inject
    lateinit var classC: ClassC
    //endregion

    //region Vars
    private val coinAdapter: CoinListAdapter = CoinListAdapter()

    private var swipeLayout: SwipeRefreshLayout? = null

    private val compositeDisposable = CompositeDisposable()

    private var pageNumber: Int = 1
    private var isLoading: Boolean = false

    //endregion

    //region Overridden Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coins_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeLayout?.isRefreshing = true
        swipeLayout?.setOnRefreshListener {
            refresh()
        }
        (requireActivity().application as App).componentDI.provideIn(this)
        setupRV()
        getListCoins(pageNumber, false)
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }
    //endregion

    //region Support
    private fun setupRV() {

        Log.i("myCoinsListFragment", classC.toString())
        val rvCoins = view?.findViewById<RecyclerView>(R.id.rvCoinList)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        rvCoins?.layoutManager = linearLayoutManager
        rvCoins?.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    Log.i("myCoins", "onScrollStateChanged: $newState")
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    Log.i("myCoins", "onScrolled: dx: $dx, dy: $dy")
                    val visibleItemCount = linearLayoutManager.childCount
                    val totalItemCount = linearLayoutManager.itemCount
                    val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()

                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        getListCoins(pageNumber, false)
                    }
                }
            })
        rvCoins?.adapter = coinAdapter

    }

    private fun refresh() {
        pageNumber = 1
        getListCoins(pageNumber, true)
    }

    private fun getListCoins(page: Int, isRefresh: Boolean) {
        if(isLoading) {
            return
        }
        isLoading = true
        swipeLayout?.isRefreshing = true
        val disposableGetCoinList = Single.fromCallable {
            coinInterface?.getCoinList(
                "usd",
                "market_cap_desc",
                20,
                page,
                false
            )
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { nullableCoins ->
                    pageNumber++
                    isLoading = false
                    nullableCoins?.let { nonNullableCoins ->
                        if(isRefresh) {
                            coinAdapter.refreshItems(nonNullableCoins)
                        } else {
                            coinAdapter.addItems(nonNullableCoins)
                        }
                    }
                    swipeLayout?.isRefreshing = false
                }, {
                    isLoading = false
                    Log.i("myCoinsListFragment", "$it")
                    it.printStackTrace()
                    Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
                    swipeLayout?.isRefreshing = false
                })
        compositeDisposable.add(disposableGetCoinList)
    }
    //endregion
}
