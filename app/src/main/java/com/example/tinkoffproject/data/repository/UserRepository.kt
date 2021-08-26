package com.example.tinkoffproject.data.repository

import com.example.tinkoffproject.data.dto.request.CreateUser
import com.example.tinkoffproject.data.dto.response.UserNetwork
import com.example.tinkoffproject.data.network.ApiService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


interface UserRepository {
    fun getUser(id: Int): Observable<UserNetwork>
    fun postUser(email: String): Observable<UserNetwork>
}

class UserRepositoryImpl @Inject constructor(val apiService: ApiService) : UserRepository {
    override fun getUser(id: Int): Observable<UserNetwork> {
        return apiService.getUser(id).subscribeOn(Schedulers.io())
    }

    override fun postUser(email: String): Observable<UserNetwork> {
        return apiService.postUser(CreateUser(email)).subscribeOn(Schedulers.io())

    }

}