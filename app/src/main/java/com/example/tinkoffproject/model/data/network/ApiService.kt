package com.example.tinkoffproject.model.data.network

import com.example.tinkoffproject.model.data.dto.Currency
import com.example.tinkoffproject.model.data.dto.Wallet
import com.example.tinkoffproject.model.data.network.dto.CategoryNetwork
import com.example.tinkoffproject.model.data.network.dto.Response
import com.example.tinkoffproject.model.data.network.dto.TransactionNetwork
import com.example.tinkoffproject.model.utils.setClient
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.reactivex.rxjava3.core.Single
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ApiService {
    companion object {
        private const val BASE_URL = ""
        private val CONTENT_TYPE = "application/json".toMediaType()
        private const val AUTH_HEADER = "header"

        private val json: Json by lazy {
            Json { ignoreUnknownKeys = true }
        }


        @ExperimentalSerializationApi
        fun create(authValue: String = ""): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(json.asConverterFactory(CONTENT_TYPE))
                .setClient(Pair(AUTH_HEADER, authValue))
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.crea)
                .build()
                .create(ApiService::class.java)
        }
    }

    //wallet
    fun createWallet(newWallet: Wallet): Single<Response>
    fun getUserWallets(): Single<List<Wallet>>
    fun updateWallet(wallet: Wallet): Single<Response>
    fun deleteWallet(walletId: Long): Single<Response>

    //transaction
    fun createUserTransactions(newTransaction: TransactionNetwork): Single<Response>
    fun getUserTransactions(): Single<List<TransactionNetwork>>
    fun updateTransaction(transaction: TransactionNetwork): Single<Response>
    fun deleteTransactions(transactionId: Long): Single<Response>

    //currency
    fun getAllCurrency(): Single<List<Currency>>
    fun getMainCurrency(): Single<List<Currency>>

    //categories
    fun getIncomeCategories(): Single<List<CategoryNetwork>>
    fun getExpensesCategories(): Single<List<CategoryNetwork>>

}