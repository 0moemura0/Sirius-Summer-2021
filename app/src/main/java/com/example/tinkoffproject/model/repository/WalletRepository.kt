package com.example.tinkoffproject.model.repository

import com.example.tinkoffproject.model.data.dto.Wallet
import com.example.tinkoffproject.model.data.network.ApiService
import com.example.tinkoffproject.model.data.network.dto.Response
import io.reactivex.rxjava3.core.Single

class WalletRepository(
    private val apiService: ApiService
) {
    fun getAllWallets(): Single<List<Wallet>> {
        //subscribeBy
        return apiService.getUserWallets()
    }

    fun createWallet(newWallet: Wallet): Single<Response> {
        //don't save local on error
        //я хз тут типо налдо подписаться и обработать ошибку или сохранить если ошибки нет
        return apiService.createWallet(newWallet)
    }

    fun updateWallet(wallet: Wallet): Single<Response> = apiService.updateWallet(wallet)
    fun deleteWallet(walletId: Long): Single<Response> = apiService.deleteWallet(walletId)
}