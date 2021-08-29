package com.example.tinkoffproject.ui.main.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCaller
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tinkoffproject.R
import com.example.tinkoffproject.State
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.viewmodel.SignInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class SignInFragment : Fragment(R.layout.fragment_signin), ActivityResultCaller {

    private val viewModel: SignInViewModel by activityViewModels()
    private lateinit var btn: NextCustomButton

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
        btn = view.findViewById(R.id.btn_login)
        btn.setOnClickListener {
            viewModel.signIn(requireActivity())
        }
    }

    private fun updateUI(state: State<GoogleSignInAccount>) {
        when (state) {
            is State.ErrorState -> {
                btn.changeState(NextCustomButton.State.DEFAULT)
                Log.d("kek", "${state.exception?.stackTraceToString()}")
                Toast.makeText(context, getString(R.string.cant_sign_in), Toast.LENGTH_LONG).show()
            }
            is State.DataState -> {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            is State.LoadingState -> {
                btn.changeState(NextCustomButton.State.LOADING)
            }
        }
    }
}