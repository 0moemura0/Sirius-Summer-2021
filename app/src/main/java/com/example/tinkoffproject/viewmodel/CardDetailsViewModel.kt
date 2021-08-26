package com.example.tinkoffproject.viewmodel

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.R
import com.example.tinkoffproject.data.dto.to_view.Category
import com.example.tinkoffproject.data.dto.to_view.Transaction
import com.example.tinkoffproject.data.dto.to_view.Wallet
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import com.example.tinkoffproject.data.repository.TransactionRepository
import com.example.tinkoffproject.utils.DEFAULT_COLOR
import com.example.tinkoffproject.utils.DEFAULT_CURRENCY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CardDetailsViewModel @Inject constructor(val repository: TransactionRepository) :
    ViewModel() {
    private val _transactions = MutableLiveData<State<List<Transaction>>>(
        State.DataState(
            listOf(
                Transaction(
                    0,
                    112213214,
                    false,
                    Category(id = 0,"Имя", R.drawable.ic_shop, Color.parseColor(DEFAULT_COLOR), false),
                    123,
                    "235 P"
                ),
                Transaction(
                    1,
                    112213214,
                    false,
                    Category(id = 0,"Имя", R.drawable.ic_sport, Color.parseColor(DEFAULT_COLOR), false),
                    123,
                    "235 P"
                ),
                Transaction(
                    2,
                    112213214,
                    false,
                    Category(id = 0,"Имя", R.drawable.ic_sport, Color.parseColor(DEFAULT_COLOR), false),
                    123,
                    "235 P"
                ),
                Transaction(
                    3,
                    112213214,
                    false,
                    Category(id = 0,"Имя", R.drawable.ic_sport, Color.parseColor(DEFAULT_COLOR), false),
                    123,
                    "235 P"
                ),
                Transaction(
                    4,
                    112213214,
                    false,
                    Category(id = 0,"Имя", R.drawable.ic_sport, Color.parseColor(DEFAULT_COLOR), false),
                    123,
                    "235 P"
                ),
                Transaction(
                    5,
                    112213214,
                    false,
                    Category(id = 0,"Имя", R.drawable.ic_sport, Color.parseColor(DEFAULT_COLOR), false),
                    123,
                    "235 P"
                ),
                Transaction(
                    6,
                    112213214,
                    false,
                    Category(id = 0,"Имя", R.drawable.ic_sport, Color.parseColor(DEFAULT_COLOR), false),
                    123,
                    "235 P"
                ),
                Transaction(
                    7,
                    112213214,
                    true,
                    Category(id = 0,"Имя", R.drawable.ic_income, Color.parseColor(DEFAULT_COLOR), true),
                    123,
                    "235 P"
                ),
            )
        )
    )
    val transaction: LiveData<State<List<Transaction>>> = _transactions

    private val _wallet = MutableLiveData<State<Wallet>>(
        State.DataState(
            Wallet(
                id = 0, name = "Кошелёк 1", incomeAmount = 10000964, expensesAmount = 10,
                currency = DEFAULT_CURRENCY,
                limit = 11
            )
        )
    )
    val wallet: LiveData<State<Wallet>> = _wallet

    fun getTransactionList(): LiveData<State<List<TransactionNetwork>>> {
        val resource = MutableLiveData<State<List<TransactionNetwork>>>(State.LoadingState)
        val disp = repository.getTransactionList(0).subscribeOn(Schedulers.io())//wallet.id
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


    fun deleteTransaction(id: Int): LiveData<State<String>> {
        val resource = MutableLiveData<State<String>>(State.LoadingState)
        val disp = repository.deleteTransaction(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resource.value = State.DataState("OK")
            }, {
                resource.value = State.ErrorState(it)
            })
        return resource
    }
}