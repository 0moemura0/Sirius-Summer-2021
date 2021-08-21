package com.example.tinkoffproject.model.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class UserRepository {
    private var accountGoogle: GoogleSignInAccount? = null

    val userEmail: String?
        get() = accountGoogle?.email

    fun saveUser(user: GoogleSignInAccount) {
        accountGoogle = user
    }
}