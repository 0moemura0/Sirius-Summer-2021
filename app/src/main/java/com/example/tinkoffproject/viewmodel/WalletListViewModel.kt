package com.example.tinkoffproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import com.example.tinkoffproject.data.dto.to_view.Currency
import com.example.tinkoffproject.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class WalletListViewModel @Inject constructor(val repository: WalletRepository) : ViewModel() {

    val currency: MutableLiveData<List<Currency>> = MutableLiveData()

    fun loadCurrencyInfo() {
        currency.value = listOf(
            Currency(
                shortName = "USD",
                longName = "Доллар США",
                isUp = false,
                rate = 72.65,
                symbol = "$"
            ),
            Currency(
                shortName = "EUR",
                longName = "Евро",
                isUp = false,
                rate = 86.60,
                symbol = "€"
            ),
            Currency(
                shortName = "CHF",
                longName = "Швейцарские франки",
                isUp = true,
                rate = 80.17,
                symbol = "₣"
            ),
        )
    }

    fun getWalletsList(): LiveData<State<List<WalletNetwork>>> {
        val resource = MutableLiveData<State<List<WalletNetwork>>>(State.LoadingState)
        val disp = repository.getUserWalletList().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    resource.value = State.DataState(it)
                },
                {
                    resource.value = State.ErrorState(it)
                    Log.e("TAG", "getWalletsList: " + it)
                }
            )
        return resource
    }

    fun deleteWallet(id: Int): LiveData<State<String>> {
        val resource = MutableLiveData<State<String>>(State.LoadingState)
        val disp = repository.deleteWallet(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resource.value = State.DataState("OK")
            }, {
                resource.value = State.ErrorState(it)
                Log.e("TAG", "deleteWallet: " + it)
            })
        return resource
    }
}