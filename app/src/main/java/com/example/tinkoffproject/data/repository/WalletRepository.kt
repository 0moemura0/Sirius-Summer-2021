package com.example.tinkoffproject.data.repository

import com.example.tinkoffproject.App.Companion.isNetworkAvailable
import com.example.tinkoffproject.data.network.ApiService
import com.example.tinkoffproject.data.dto.request.CreateWallet
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import com.example.tinkoffproject.data.local.dao.WalletDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface WalletRepository {
    fun getUserWalletList(): Observable<List<WalletNetwork>>
    fun postWallet(createWallet: CreateWallet): Observable<WalletNetwork>
    fun editWallet(id: Int, createWallet: CreateWallet): Observable<WalletNetwork>
    fun deleteWallet(id: Int): Completable
}

class WalletRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    val dao: WalletDao
) :
    WalletRepository {

    override fun getUserWalletList(): Observable<List<WalletNetwork>> {
        return Observable.concatArrayEager(
            dao.getAll().subscribeOn(Schedulers.io()),
            Observable.defer {
                if (isNetworkAvailable())
                    apiService.getWallets().subscribeOn(Schedulers.io()).flatMap {
                        dao.removeAll().andThen(Completable.fromCallable { dao.addAll(it) }
                            .andThen(Observable.just(it)))
                    }
                else
                    Observable.empty()
            }.subscribeOn(Schedulers.io())
        )
    }

    override fun postWallet(createWallet: CreateWallet): Observable<WalletNetwork> {
        return Observable.defer {
            if (isNetworkAvailable())
                apiService.postWallet(createWallet).subscribeOn(Schedulers.io())
                    .flatMap {
                        Completable.fromCallable { dao.add(it) }.andThen(Observable.just(it))
                    }
            else
                Observable.error(IllegalAccessError("You are offline"))
        }
    }

    override fun editWallet(id: Int, createWallet: CreateWallet): Observable<WalletNetwork> {
        return Observable.defer {
            if (isNetworkAvailable())
                apiService.putWallet(id, createWallet).subscribeOn(Schedulers.io())
                    .flatMap {
                        Completable.fromCallable { dao.update(it) }.andThen(Observable.just(it))
                    }
            else
                Observable.error(IllegalAccessError("You are offline"))
        }
    }

    override fun deleteWallet(id: Int): Completable {
        return Completable.defer {
            if (isNetworkAvailable())
                apiService.deleteWallet(id).subscribeOn(Schedulers.io())
                    .andThen(dao.deleteByUserId(id))
            else
                Completable.error(IllegalAccessError("You are offline"))
        }
    }
}