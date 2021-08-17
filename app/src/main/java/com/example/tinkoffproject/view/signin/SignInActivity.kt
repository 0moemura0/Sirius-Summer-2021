package com.example.tinkoffproject.view.signin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tinkoffproject.R


class SignInActivity : AppCompatActivity(R.layout.activity_signin) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .add(R.id.login_fragment_container, SignInFragment.newInstance())
            .commit()
    }
}