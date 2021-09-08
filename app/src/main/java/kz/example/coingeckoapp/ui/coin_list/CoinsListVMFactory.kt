package kz.example.coingeckoapp.ui.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kz.example.coingeckoapp.repositories.BaseCoinRepository
import javax.inject.Inject


class CoinsListVMFactory @Inject constructor(
    private val coinRepository: BaseCoinRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CoinsListVM(coinRepository) as T
    }

}