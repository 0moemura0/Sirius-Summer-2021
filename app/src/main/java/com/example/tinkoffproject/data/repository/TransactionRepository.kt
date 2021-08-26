package com.example.tinkoffproject.data.repository

import android.util.Log
import com.example.tinkoffproject.App
import com.example.tinkoffproject.data.local.dao.TransactionDao
import com.example.tinkoffproject.data.network.ApiService
import com.example.tinkoffproject.data.dto.request.CreateTransaction
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface TransactionRepository {
    fun getTransactionList(walletId: Int): Observable<List<TransactionNetwork>>
    fun postTransaction(createTransaction: CreateTransaction): Observable<TransactionNetwork>
    fun editTransaction(
        id: Int,
        createTransaction: CreateTransaction
    ): Observable<TransactionNetwork>

    fun deleteTransaction(id: Int): Completable
}

class TransactionRepositoryImpl @Inject constructor(
    val apiService: ApiService,
    val dao: TransactionDao
) : TransactionRepository {
    override fun getTransactionList(walletId: Int): Observable<List<TransactionNetwork>> {
        return Observable.concat(
            dao.getAll(walletId).toObservable(),
            Observable.defer {
                if (App.isNetworkAvailable())
                    apiService.getWalletsTransactions(walletId)
                        .flatMap {
                            dao.removeAll(walletId)
                                .andThen(Completable.fromCallable { dao.addAll(it) }
                                    .andThen(Observable.just(it)))
                        }
                else
                    Observable.empty()
            }.subscribeOn(Schedulers.io())
        )
    }

    override fun postTransaction(createTransaction: CreateTransaction): Observable<TransactionNetwork> {
        return Observable.defer {
            if (App.isNetworkAvailable())
                apiService.postTransactions(createTransaction).doOnEach {
                    Log.d("TAG", "postTransaction: " + it.value?.categoryId)
                }
                    .flatMap {
                        Completable.fromCallable { dao.add(it) }.andThen(Observable.just(it))
                    }
            else
                Observable.error(IllegalAccessError("You are offline"))
        }
    }

    override fun editTransaction(
        id: Int,
        createTransaction: CreateTransaction
    ): Observable<TransactionNetwork> {
        return Observable.defer {
            if (App.isNetworkAvailable())
                apiService.putTransaction(id, createTransaction)
                    .flatMap {
                        Completable.fromCallable { dao.update(it) }.andThen(Observable.just(it))
                    }
            else
                Observable.error(IllegalAccessError("You are offline"))
        }
    }

    override fun deleteTransaction(id: Int): Completable {
        return Completable.defer {
            if (App.isNetworkAvailable())
                apiService.deleteTransaction(id)
                    .andThen(dao.deleteById(id))
            else
                Completable.error(IllegalAccessError("You are offline"))
        }
    }
}