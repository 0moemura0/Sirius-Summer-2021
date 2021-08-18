package com.example.tinkoffproject.view.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCaller
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.utils.State
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.viewmodel.SignInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class SignInFragment : Fragment(R.layout.fragment_signin), ActivityResultCaller {

    private val viewModel: SignInViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewModel.signInState.observe(viewLifecycleOwner, {
            updateUI(it)
        })
        viewModel.checkIsSignIn(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initActivityLauncher(this)

        view.findViewById<TextView>(R.id.btn_login).setOnClickListener {
            viewModel.signIn(requireActivity())
        }
    }

    private fun updateUI(state: State<GoogleSignInAccount>) {
        when (state) {
            is State.ErrorState -> {
                Toast.makeText(context, getString(R.string.cant_sign_in), Toast.LENGTH_LONG).show()
            }
            is State.DataState -> {
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
            }
            is State.LoadingState -> {
            }
        }
    }
}