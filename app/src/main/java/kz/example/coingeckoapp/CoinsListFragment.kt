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
    //endregion

    //region Support
    private fun setupRV() {
        val rvCoins = view?.findViewById<RecyclerView>(R.id.rvCoinList)
        rvCoins?.layoutManager = LinearLayoutManager(requireContext())
        rvCoins?.adapter = coinAdapter
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        coinInterface = retrofit?.create(CoinInterface::class.java)
    }

    private fun getListCoins(page: Int) {
        val call = coinInterface?.getCoinList(
            "usd",
            "market_cap_desc",
            50,
            page,
            false
        )

        call?.enqueue(object: Callback<List<CoinModel>> {
            override fun onResponse(
                call: Call<List<CoinModel>>,
                response: Response<List<CoinModel>>
            ) {
                response.body()?.let {
                    coinAdapter.addItems(it)
                }
                progress?.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<CoinModel>>, t: Throwable) {
                Log.i("myCoinsListFragment", "$t")
                t.printStackTrace()
                Toast.makeText(requireContext(), "Error: $t", Toast.LENGTH_SHORT).show()
                progress?.visibility = View.GONE
            }

        })

    }
    //endregion
}
