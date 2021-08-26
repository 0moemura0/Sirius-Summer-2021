package com.example.tinkoffproject.view.wallet_add

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.NextCustomButton
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddWalletViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class SetLimitFragment : Fragment(R.layout.layout_set_value) {
    private val viewModel: AddWalletViewModel by activityViewModels()

    private lateinit var inputEditText: TextInputEditText
    private lateinit var inputTextLayout: TextInputLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupInputText()
        setupViews()
        setupData()
        setupNextButton()
        setupToolbar()
    }

    private fun initViews() {
        inputEditText = requireView().findViewById(R.id.et_sum)
        inputTextLayout = requireView().findViewById(R.id.input_sum)
    }

    private fun setupNextButton() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
            if (isNextAvailable()) {
                saveData()
                findNavController().navigate(R.id.action_to_newWallet)
            } else setDefaultError()
        }
    }

    private fun setDefaultError() {
        val text = requireContext().getString(R.string.wrong_input)
        setError(text)
    }

    private fun setError(text: String?) {
        inputTextLayout.error = text
    }

    private fun hideError() = setError(null)

    private fun setupInputText() {
        inputEditText.doAfterTextChanged {
            if (!it.isNullOrBlank()) {
                hideError()
            }
        }
        inputEditText.setText(viewModel.limit.value.toString())
    }

    private fun isNextAvailable(): Boolean {
        val str = inputEditText.text.toString()
        return str.isNotBlank() && str.toInt() != 0
    }

    private fun saveData() {
        val str = inputEditText.text.toString()
        viewModel.limit.value = str.toInt()
    }

    private fun setupData() {
        inputEditText.setText(viewModel.limit.value?.toString() ?: "")
    }

    private fun setupViews() {
        inputTextLayout.setHint(R.string.limit_desc)
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar("", ToolbarType.ADD_VALUE)
    }

}