package com.example.tinkoffproject.viewmodel

import android.app.Activity
import android.content.Context
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultRegistry
import androidx.lifecycle.ViewModel
import com.example.tinkoffproject.model.repository.AccountRepository
import com.example.tinkoffproject.model.utils.State
import com.example.tinkoffproject.view.signin.GoogleSignInContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val accountRepository: AccountRepository) : ViewModel() {
    private fun getSignInClient(activity: Activity): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(activity, gso)
    }

    private fun signIn() {
        activityLauncher.launch(getSignInClient())
    }

    fun initActivityLauncher(caller: ActivityResultCaller){
        caller.registerForActivityResult(GoogleSignInContract()) { result: Task<GoogleSignInAccount>? ->
            val state = State.DataState(result)

            updateUI(state)
        }
    }
}