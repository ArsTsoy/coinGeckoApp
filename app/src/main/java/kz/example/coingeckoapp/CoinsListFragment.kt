package kz.example.coingeckoapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaredsburrows.retrofit2.adapter.synchronous.SynchronousCallAdapterFactory
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kz.example.coingeckoapp.models.CoinModel
import kz.example.coingeckoapp.recycler_views.CoinListAdapter
import kz.example.coingeckoapp.retrofit.CoinInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CoinsListFragment : Fragment() {

    //region Vars
    private val coinAdapter: CoinListAdapter = CoinListAdapter()
    private var retrofit: Retrofit? = null
    private var coinInterface: CoinInterface? = null

    private var progress: ProgressBar? = null

    private val compositeDisposable = CompositeDisposable()
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
        progress = view.findViewById(R.id.progressBar)
        progress?.visibility = View.VISIBLE
        setupRV()
        getListCoins(1)
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }
    //endregion

    //region Support
    private fun setupRV() {
        val rvCoins = view?.findViewById<RecyclerView>(R.id.rvCoinList)
        rvCoins?.layoutManager = LinearLayoutManager(requireContext())
        rvCoins?.adapter = coinAdapter
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/")
            .addCallAdapterFactory(SynchronousCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        coinInterface = retrofit?.create(CoinInterface::class.java)
    }

    private fun getListCoins(page: Int) {
        val disposableGetCoinList = Single.fromCallable {
            coinInterface?.getCoinList(
                "usd",
                "market_cap_desc",
                50,
                page,
                false
            )
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { nullableCoins ->
                    nullableCoins?.let { nonNullableCoins ->
                        coinAdapter.addItems(nonNullableCoins)
                    }
                    progress?.visibility = View.GONE
                }, {
                    Log.i("myCoinsListFragment", "$it")
                    it.printStackTrace()
                    Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
                    progress?.visibility = View.GONE
                })
        compositeDisposable.add(disposableGetCoinList)
    }
    //endregion
}
