package com.example.tinkoffproject.view.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultCaller
import androidx.fragment.app.Fragment
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.utils.State
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task

class SignInFragment : Fragment(R.layout.fragment_login), ActivityResultCaller {
    private val activityLauncher =
        registerForActivityResult(GoogleSignInContract()) { result: Task<GoogleSignInAccount>? ->
            //TODO delete
            val state = State.DataState(result!!.result)

            updateUI(state)
        }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account != null)
            updateUI(State.DataState(account))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<SignInButton>(R.id.btn_login).setOnClickListener {
            signIn()
        }
    }

    private fun getSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun signIn() {
        activityLauncher.launch(getSignInClient())
    }

    private fun updateUI(state: State<GoogleSignInAccount>) {
        when (state) {
            is State.ErrorState -> {
                Log.d("kek", "error")
                //TODO Text view
            }
            is State.DataState -> {
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
            }
            else -> {
            }
        }
    }

    companion object {
        fun newInstance(): SignInFragment {
            val args = Bundle()

            val fragment = SignInFragment()
            fragment.arguments = args
            return fragment
        }
    }
}