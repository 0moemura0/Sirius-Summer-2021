package com.example.tinkoffproject.view.operation_add

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddOperationViewModel
import com.google.android.material.textfield.TextInputLayout

class SetCashFragment : Fragment(R.layout.operation_set_cash) {

    private val viewModel: AddOperationViewModel by activityViewModels()
    private lateinit var inputEditText: EditText
    private lateinit var inputTextLayout: TextInputLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setupNextButton()
        setupInputText()
        setupToolbar()
    }

    private fun setupInputText() {
        inputEditText.doAfterTextChanged {
            val isNullOrEmpty = it.isNullOrEmpty()
            if (isNullOrEmpty) inputTextLayout.error =
                requireContext().getString(R.string.wrong_input)
            viewModel.isNextAvailable.value = !isNullOrEmpty
        }
    }

    private fun initViews() {
        inputEditText = requireView().findViewById(R.id.et_sum)
        inputTextLayout = requireView().findViewById(R.id.input_sum)

    }

    private fun setupNextButton() {
        requireView().findViewById<TextView>(R.id.btn).setOnClickListener {
            if (viewModel.isNextAvailable.value == true) {
                viewModel.amount.value = inputEditText.text.toString().toInt()
                findNavController().navigate(R.id.action_setCashFragment_to_chooseTypeFragment)
            } else {
                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.enter_count), ToolbarType.ADD_OPERATION)
    }
}