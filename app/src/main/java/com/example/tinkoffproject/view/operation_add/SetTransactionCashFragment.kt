package com.example.tinkoffproject.view.operation_add

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.NextCustomButton
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddOperationViewModel
import com.google.android.material.textfield.TextInputLayout

class SetTransactionCashFragment : Fragment(R.layout.layout_set_value) {

    private val viewModel: AddOperationViewModel by activityViewModels()
    private lateinit var inputEditText: EditText
    private lateinit var inputTextLayout: TextInputLayout

    private val args: SetTransactionCashFragmentArgs by navArgs()
    private var isNewOperation = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupData()
        setupNextButton()
        setupInputText()
        setupToolbar()
    }

    private fun setupData() {
        if (args.isNewOperation && isNewOperation) {
            isNewOperation = false
            viewModel.init()
        }
        inputEditText.setText(viewModel.amount.value?.toString() ?: "")
    }


    private fun setupInputText() {
        inputEditText.doAfterTextChanged {
            if (!it.isNullOrBlank()) {
                hideError()
            }
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

    private fun initViews() {
        inputEditText = requireView().findViewById(R.id.et_sum)
        inputTextLayout = requireView().findViewById(R.id.input_sum)
    }

    private fun setupNextButton() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
            if (isNextAvailable()) {
                saveData()
                val action =
                    if (args.isFromMain) R.id.action_to_newOperation else R.id.action_setCash_to_chooseType
                findNavController().navigate(action)
            } else {
                setDefaultError()
                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isNextAvailable(): Boolean {
        val str = inputEditText.text.toString()
        return if (str.isBlank()) false
        else str.toInt() > 0
    }

    private fun saveData() {
        viewModel.amount.value = inputEditText.text.toString().toInt()
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.enter_count), ToolbarType.ADD_OPERATION)
    }
}