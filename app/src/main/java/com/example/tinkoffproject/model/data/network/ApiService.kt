package com.example.tinkoffproject.model.data.network

import com.example.tinkoffproject.model.data.dto.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


import com.example.tinkoffproject.model.data.network.dto.response.CategoryNetwork
import com.example.tinkoffproject.model.data.network.dto.request.CreateWallet
import com.example.tinkoffproject.model.data.network.dto.Response
import com.example.tinkoffproject.model.data.network.dto.request.CreateCategory
import com.example.tinkoffproject.model.data.network.dto.request.CreateTransaction
import com.example.tinkoffproject.model.data.network.dto.request.CreateUser
import com.example.tinkoffproject.model.data.network.dto.response.TransactionNetwork
import com.example.tinkoffproject.model.data.network.dto.response.UserNetwork
import com.example.tinkoffproject.model.data.network.dto.response.WalletNetwork
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.reactivex.Single
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.*


interface ApiService {
    companion object {
        private val client = provideOkhttpClient()

        private const val BASE_URL = "http://34.88.25.70/"
        private val CONTENT_TYPE: MediaType = "application/json".toMediaType()

        @ExperimentalSerializationApi
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(Json.asConverterFactory(CONTENT_TYPE))
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java);
        }

        private fun provideOkhttpClient(): OkHttpClient {
            val client = OkHttpClient.Builder()
            client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            client.connectTimeout(20, TimeUnit.SECONDS)
            client.readTimeout(30, TimeUnit.SECONDS)
            client.writeTimeout(30, TimeUnit.SECONDS)
            return client.build()
        }
    }

    //wallets

    @GET("wallets/")
    fun getWallets(): Single<List<WalletNetwork?>?>

    @POST("wallets/")
    fun postWallet(@Body newWallet: CreateWallet): Single<WalletNetwork?>

    @GET("wallets/{id}/")
    fun getUserWallets(@Path("id") id: Int): Single<WalletNetwork?>

    @PUT("wallets/{id}/")
    fun putWallet(@Path("id") id: Int, @Body wallet: CreateWallet): Single<WalletNetwork?>

    @DELETE("wallets/{id}/")
    fun deleteWallet(@Path("id") id: Int): Single<Response?>

    //transactions

    @POST("transactions/")
    fun postTransactions(@Body newWallet: CreateTransaction): Single<TransactionNetwork?>

    @GET("transactions/{id}/")
    fun getTransaction(@Path("id") id: Int): Single<TransactionNetwork?>

    @PUT("transactions/{id}/")
    fun putTransaction(
        @Path("id") id: Int,
        @Body newTransaction: CreateTransaction
    ): Single<TransactionNetwork?>

    @DELETE("transactions/{id}/")
    fun deleteTransaction(@Path("id") id: Int): Single<Response?>

    @GET("wallets/{id}/listTransactions/")
    fun getUserTransactions(@Path("id") id: Int): Single<List<TransactionNetwork?>?>


    //categories

    @GET("categories/")
    fun getCategories(): Single<List<CategoryNetwork?>?>

    @POST("categories/")
    fun postCategories(@Body newCategory: CreateCategory): Single<CategoryNetwork?>

    @GET("categories/{id}")
    fun getCategory(@Path("id") id: Int): Single<CategoryNetwork?>

    @PUT("categories/{id}")
    fun putCategory(
        @Path("id") id: Int,
        @Body newCategory: CreateCategory
    ): Single<CategoryNetwork?>

    @DELETE("categories/{id}")
    fun deleteCategory(@Path("id") id: Int): Single<Response?>

    //users
    @GET("users/")
    fun getUsers(): Single<List<UserNetwork?>?>

    @PUT("users/")
    fun putUser(@Body newUser: CreateUser): Single<User?>

    @POST("users/")
    fun postUser(@Body newUser: CreateUser): Single<User?>

    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): Single<UserNetwork?>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int): Single<Response?>
}