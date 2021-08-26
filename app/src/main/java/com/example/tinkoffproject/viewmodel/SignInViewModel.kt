package com.example.tinkoffproject.viewmodel

import android.app.Activity
import android.content.Context
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.UserData
import com.example.tinkoffproject.data.repository.UserRepository
import com.example.tinkoffproject.ui.main.signin.GoogleSignInContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val repository: UserRepository
) :
    ViewModel() {
    private val _signInState: MutableLiveData<State<GoogleSignInAccount>> =
        MutableLiveData(State.LoadingState)
    val signInState: LiveData<State<GoogleSignInAccount>> = _signInState
    private lateinit var activityLauncher: ActivityResultLauncher<GoogleSignInClient>

    private val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }

    fun signIn(activity: Activity) {
        val client = GoogleSignIn.getClient(activity, gso)
        activityLauncher.launch(client)
    }

    fun initActivityLauncher(caller: ActivityResultCaller) {
        activityLauncher =
            caller.registerForActivityResult(GoogleSignInContract()) { result: Task<GoogleSignInAccount> ->
                if (result.isSuccessful && result.result != null && !result.result.email.isNullOrEmpty()) {
                    tryToPost(result.result)
                } else _signInState.value = State.ErrorState(result.exception)
            }
    }


    fun checkIsSignIn(context: Context) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
            getFromServer(account)
        }
    }

    private fun getFromServer(account: GoogleSignInAccount): LiveData<State<GoogleSignInAccount>> {
        val resource = MutableLiveData<State<GoogleSignInAccount>>(State.LoadingState)
        val disp = repository.getUser(UserData.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                UserData.id = it.id
                UserData.email = it.username
                _signInState.value = State.DataState(account)
            }, {
                tryToPost(account)
            })
        return resource
    }

    fun tryToPost(result: GoogleSignInAccount) {
        val second =
            repository.postUser(result.email).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe({
                    _signInState.value = State.DataState(result)
                }, {
                    if (it is HttpException) {
                        if (it.code() == 409) {
                            val jObjError =
                                JSONObject(it.response()?.errorBody()?.string())
                            val message = jObjError.getString("message")
                            val id = message.substring(
                                32, message.indexOf(',')
                            )
                            val a: Int =
                                message.indexOf("username\":\"") + "username\":\"".length
                            val b = message.indexOf(
                                "\"}"
                            )
                            val email = message.substring(
                                a,
                                b
                            )
                            UserData.id = id.toInt()
                            UserData.email = email

                            _signInState.value = State.DataState(result)
                        }
                    } else _signInState.value = State.ErrorState(it)
                })
    }
}
