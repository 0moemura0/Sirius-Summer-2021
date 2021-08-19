package com.example.tinkoffproject.viewmodel

import android.app.Activity
import android.content.Context
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.model.utils.State
import com.example.tinkoffproject.view.signin.GoogleSignInContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task


class SignInViewModel :
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
                if (result.isSuccessful) _signInState.value = State.DataState(result.result)
                else _signInState.value = State.ErrorState(result.exception)
            }
    }

    fun checkIsSignIn(context: Context) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null)
            _signInState.value = State.DataState(account)
    }
}
