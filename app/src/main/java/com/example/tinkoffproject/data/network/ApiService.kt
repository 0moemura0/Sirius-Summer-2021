package com.example.tinkoffproject.data.network


import com.example.tinkoffproject.data.dto.request.CreateCategory
import com.example.tinkoffproject.data.dto.request.CreateTransaction
import com.example.tinkoffproject.data.dto.request.CreateUser
import com.example.tinkoffproject.data.dto.request.CreateWallet
import com.example.tinkoffproject.data.dto.response.CategoryNetwork
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import com.example.tinkoffproject.data.dto.response.UserNetwork
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*


interface ApiService {
    //wallets

    @GET("wallets/")
    fun getWallets(): Observable<List<WalletNetwork>>

    @POST("wallets/")
    fun postWallet(@Body newWallet: CreateWallet): Observable<WalletNetwork>

    @GET("wallets/{id}/")
    fun getWallet(@Path("id") id: Int): Observable<WalletNetwork>

    @PUT("wallets/{id}/")
    fun putWallet(@Path("id") id: Int, @Body wallet: CreateWallet): Observable<WalletNetwork>

    @DELETE("wallets/{id}/")
    fun deleteWallet(@Path("id") id: Int): Completable

    //transactions

    @POST("transactions/")
    fun postTransactions(@Body newWallet: CreateTransaction): Observable<TransactionNetwork>

    @GET("transactions/{id}/")
    fun getTransaction(@Path("id") id: Int): Observable<TransactionNetwork>

    @PUT("transactions/{id}/")
    fun putTransaction(
        @Path("id") id: Int,
        @Body newTransaction: CreateTransaction
    ): Observable<TransactionNetwork>

    @DELETE("transactions/{id}/")
    fun deleteTransaction(@Path("id") id: Int): Completable

    @GET("wallets/{id}/listTransactions/")
    fun getWalletsTransactions(@Path("id") id: Int): Observable<List<TransactionNetwork>>


    //categories

    @GET("categories/")
    fun getCategories(): Observable<List<CategoryNetwork>>

    @POST("categories/")
    fun postCategories(@Body newCategory: CreateCategory): Observable<CategoryNetwork>

    @GET("categories/{id}")
    fun getCategory(@Path("id") id: Int): Observable<CategoryNetwork>

    @PUT("categories/{id}")
    fun putCategory(
        @Path("id") id: Int,
        @Body newCategory: CreateCategory
    ): Observable<CategoryNetwork>

    @DELETE("categories/{id}")
    fun deleteCategory(@Path("id") id: Int): Completable

    //users
    @GET("users/")
    fun getUsers(): Observable<List<UserNetwork>>

    @PUT("users/")
    fun putUser(@Body newUser: CreateUser): Observable<UserNetwork>

    @POST("users/")
    fun postUser(@Body newUser: CreateUser): Observable<UserNetwork>

    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): Observable<UserNetwork>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int): Completable
}