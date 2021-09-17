package kz.example.coingeckoapp.clean.presentation.ui.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kz.example.data.repositories.BaseCoinRepository
import javax.inject.Inject


class CoinsListVMFactory @Inject constructor(
    private val coinRepository: kz.example.data.repositories.BaseCoinRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CoinsListVM(coinRepository) as T
    }

}