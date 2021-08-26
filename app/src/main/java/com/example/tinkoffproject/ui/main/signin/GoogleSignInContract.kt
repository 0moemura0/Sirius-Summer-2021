package com.example.tinkoffproject.ui.main.signin

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task

class GoogleSignInContract :
    ActivityResultContract<GoogleSignInClient, Task<GoogleSignInAccount>>() {
    override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount> =
        GoogleSignIn.getSignedInAccountFromIntent(intent)

    override fun createIntent(context: Context, input: GoogleSignInClient): Intent =
        input.signInIntent
}