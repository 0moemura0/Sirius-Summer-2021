package com.example.tinkoffproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.to_view.Currency
import com.example.tinkoffproject.data.dto.request.CreateWallet
import com.example.tinkoffproject.data.dto.response.CurrencyNetwork
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import com.example.tinkoffproject.data.repository.WalletRepository
import com.example.tinkoffproject.utils.toLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AddWalletViewModel @Inject constructor(val repository: WalletRepository) : ViewModel() {
    var name = MutableLiveData<String>()
    var currency = MutableLiveData<Currency>()
    var limit = MutableLiveData<Int>()

    val isNextAvailable: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        init()
    }

    fun init() {
        name = MutableLiveData()
        currency = MutableLiveData()
        limit = MutableLiveData()
        isNextAvailable.value = false
    }

    fun getAllCurrency(): List<Currency> {
        return listOf(
            CurrencyNetwork("RUS", "Российский рубль"),
            CurrencyNetwork("USD", "Доллар США"),
            CurrencyNetwork("EUR", "Евро"),
            CurrencyNetwork("CHF", "Швейцарские франки"),
            CurrencyNetwork("KWD", "Кувейтский динар"),
            CurrencyNetwork("BHD", "Бахрейнский динар"),
            CurrencyNetwork("OMR", "Оманский риал"),
            CurrencyNetwork("JPY", "Японская иена"),
            CurrencyNetwork("SEK", "Шведская крона")
        ).map { it.toLocal() }
    }

    fun addWallet(): LiveData<State<WalletNetwork>> {
        val resource = MutableLiveData<State<WalletNetwork>>(State.LoadingState)
        val disp = repository.postWallet(
            CreateWallet(
                limit.value,
                name.value,
                currency.value?.shortName
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    resource.value = State.DataState(it)
                },
                {
                    resource.value = State.ErrorState(it)
                }
            )
        return resource
    }
}