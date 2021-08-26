package com.example.tinkoffproject.ui.main.signin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tinkoffproject.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity(R.layout.activity_signin) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.login_fragment_container, SignInFragment())
                .commit()
        }
    }
}