package com.example.tinkoffproject.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.network.ApiService
import com.example.tinkoffproject.data.UserData
import com.example.tinkoffproject.data.dto.request.CreateUser
import com.example.tinkoffproject.data.dto.response.UserNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

import org.json.JSONObject


interface UserRepository {
    fun getUser(id: Int): LiveData<State<UserNetwork>>
    fun postUser(email: String): LiveData<State<UserNetwork>>
}

class UserRepositoryImpl @Inject constructor(val apiService: ApiService) : UserRepository {
    override fun getUser(id: Int): LiveData<State<UserNetwork>> {
        val resource = MutableLiveData<State<UserNetwork>>(State.LoadingState)
        val disposable =
            apiService.getUser(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe({
                    UserData.id = it.id
                    UserData.email = it.username
                    resource.value = State.DataState(it)
                }, {
                    resource.value = State.ErrorState(it)
                })
        return resource
    }

    override fun postUser(email: String): LiveData<State<UserNetwork>> {
        val resource = MutableLiveData<State<UserNetwork>>(State.LoadingState)
        val disposable =
            apiService.postUser(CreateUser(email))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe({
                    resource.value = State.DataState(it)
                }, {
                    if (it is HttpException) {
                        if (it.code() == 409) {
                            val jObjError =
                                JSONObject(it.response()?.errorBody()?.string())
                            val message = jObjError.getString("message")
                            val id = message.substring(
                                32, message.indexOf(',')
                            )
                            val a: Int = message.indexOf("username\":\"") + "username\":\"".length
                            val b = message.indexOf(
                                "\"}"
                            )
                            val email = message.substring(
                                a,
                                b
                            )
                            UserData.id = id.toInt()
                            UserData.email = email

                            resource.value =
                                State.DataState(UserNetwork(id.toInt(), email)) //TODO
                        }
                    }
                })
        return resource
    }

}