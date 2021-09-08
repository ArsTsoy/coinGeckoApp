package kz.example.coingeckoapp.ui.coin_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kz.example.coingeckoapp.models.CoinModel
import kz.example.coingeckoapp.repositories.BaseCoinRepository

private const val DEFAULT_START_PAGE = 1

class CoinsListVM(
    private val coinRepository: BaseCoinRepository
): ViewModel() {

    //region Vars
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading.postValue(true)
                coinRepository
                    .getCoinList(pageNumber)
                    .onCompletion {
                        pageNumber++
                        isLoading.postValue(false)
                    }
                    .collectLatest {
                        if(isRefresh) {
                            listOfCoins.clear()
                        }
                        listOfCoins.addAll(it)
                        this@CoinsListVM.coins.postValue(listOfCoins)
                    }
            } catch (e: Exception) {
                isLoading.postValue(false)
                Log.i("myCoinsListFragment", "$e")
            }
        }
    }

}