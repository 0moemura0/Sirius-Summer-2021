package com.example.tinkoffproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.response.IncomeAndExpense
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import com.example.tinkoffproject.data.dto.to_view.Wallet
import com.example.tinkoffproject.data.repository.TransactionRepository
import com.example.tinkoffproject.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    val repository: TransactionRepository,
    private val walletRepository: WalletRepository
) :
    ViewModel() {

    var wallet: Wallet? = null
    val income = MutableLiveData<Int>()
    val expenses = MutableLiveData<Int>()

    fun getTransactionList(): LiveData<State<List<TransactionNetwork>>> {
        val resource = MutableLiveData<State<List<TransactionNetwork>>>(State.LoadingState)
        val disp = wallet!!.id.let {
            repository.getTransactionList(it).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        resource.value = State.DataState(it)
                    },
                    {
                        resource.value = State.ErrorState(it)
                        Log.e("TAG", "getTransactionList: " + it)
                    }
                )
        }
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

    fun getIncomeExpense(id: Int): LiveData<State<IncomeAndExpense>> {
        val resource = MutableLiveData<State<IncomeAndExpense>>(State.LoadingState)
        val disp = walletRepository.getIncomeExpenses(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resource.value = State.DataState(it)
            }, {
                resource.value = State.ErrorState(it)
                Log.e("TAG", "getIncomeExpense: $it")
            })

        return resource
    }
}