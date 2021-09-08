package kz.example.coingeckoapp.ui.coin_list

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kz.example.coingeckoapp.models.CoinModel
import kz.example.coingeckoapp.retrofit.CoinInterface

private const val DEFAULT_START_PAGE = 1

class CoinsListVM(
    private val coinInterface: CoinInterface
): ViewModel() {

    //region Vars
    private val compositeDisposable = CompositeDisposable()
    private var pageNumber: Int = DEFAULT_START_PAGE
    private val listOfCoins = mutableListOf<CoinModel>()
    //endregion

    //region LiveData
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun observeIsLoading(): LiveData<Boolean> = isLoading

    private val coins: MutableLiveData<List<CoinModel>> = MutableLiveData()
    fun observeCoinList(): LiveData<List<CoinModel>> = coins
    //endregion

    init {
        onRefreshPulled()
    }

    fun onRefreshPulled() {
        pageNumber = DEFAULT_START_PAGE
        getCoins(true)
    }

    fun getNextPage() {
        if (isLoading.value == true) {
            return
        }
        getCoins(false)
    }

    private fun getCoins(isRefresh: Boolean) {
        isLoading.postValue(true)
        val disposableGetCoinList = Single.fromCallable {
            coinInterface.getCoinList(
                "usd",
                "market_cap_desc",
                20,
                pageNumber,
                false
            )
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { coins ->

                    pageNumber++
                    isLoading.postValue(false)
                    if(isRefresh) {
                        listOfCoins.clear()
                    }
                    listOfCoins.addAll(coins)
                    this.coins.postValue(listOfCoins)
                }, {
                    isLoading.postValue(false)
                    Log.i("myCoinsListFragment", "$it")
                })
        compositeDisposable.add(disposableGetCoinList)
    }


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


}