package com.example.tinkoffproject.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.UserData
import com.example.tinkoffproject.data.dto.response.UserNetwork
import com.example.tinkoffproject.data.repository.UserRepository
import com.example.tinkoffproject.ui.main.signin.GoogleSignInContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
                    val email: String = result.result.email ?: ""
                    repository.postUser(email)
                    _signInState.value = State.DataState(result.result)
                } else _signInState.value = State.ErrorState(result.exception)
            }
    }


    fun checkIsSignIn(context: Context) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
            getFromServer(account)
        }
    }

    private fun getFromServer(account: GoogleSignInAccount) {
        val re = repository.getUser(UserData.id)
    }
}
