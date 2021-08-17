package com.example.tinkoffproject.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.utils.State
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val activityLauncher =
        registerForActivityResult(GoogleSignInContract()) { result: Task<GoogleSignInAccount>? ->
            handleSignInResult(result)
        }

    override fun onStart() {
        super.onStart()

        val account = checkIsUserSignIn()
        if (account != null)
            updateUI(State.DataState(account))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupGoogleSignIn()
    }

    // Check for existing Google Sign In account, if the user is already signed in
    // the GoogleSignInAccount will be non-null.
    private fun checkIsUserSignIn(): GoogleSignInAccount? =
        GoogleSignIn.getLastSignedInAccount(this)


    private fun setupGoogleSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<SignInButton>(R.id.sign_in_button).setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {

        activityLauncher.launch(mGoogleSignInClient)
    }

    private fun updateUI(state: State<GoogleSignInAccount?>) {
        //TODO
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>?) {
        try {
            val account = completedTask?.getResult(ApiException::class.java)

            if (account == null) updateUI(State.ErrorState(null))
            else updateUI(State.DataState(account))
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            updateUI(State.ErrorState(e))
        }
    }

}