package com.example.tinkoffproject.ui.main.operation_add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddTransactionViewModel
import com.google.android.material.textfield.TextInputLayout

class SetTransactionCashFragment : Fragment(R.layout.layout_set_value) {

    val viewModel: AddTransactionViewModel by activityViewModels()
    private lateinit var inputEditText: EditText
    private lateinit var inputTextLayout: TextInputLayout
    private lateinit var btn: NextCustomButton

    private val args: SetTransactionCashFragmentArgs by navArgs()
    private var isNewOperation = true

    companion object{
        const val IS_NEW_OPERATION = "IS_NEW_OPERATION"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(savedInstanceState != null) isNewOperation = savedInstanceState.getBoolean(IS_NEW_OPERATION)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

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
            viewModel.init(args.transaction, args.wallet)
        }
        if (args.transaction != null) {
            findNavController().navigate(R.id.action_setCash_to_changeTransaction)
        }
        inputEditText.setText(viewModel.amount.value?.toString() ?: "")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(IS_NEW_OPERATION, isNewOperation)
        super.onSaveInstanceState(outState)
    }


    private fun setupInputText() {
        updateButtonState()
        inputEditText.doAfterTextChanged {
            updateButtonState()
            if (!it.isNullOrBlank()) {
                hideError()
            }
        }
    }
    private fun updateButtonState() {
        btn.changeState(if (isNextAvailable()) NextCustomButton.State.DEFAULT else NextCustomButton.State.DISABLED)
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
        btn = requireView().findViewById(R.id.btn)

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
        val str = inputEditText.text?.toString()
        return if (str.isNullOrBlank()) false else str.toInt() > 0
    }

    private fun saveData() {
        viewModel.amount.value = inputEditText.text.toString().toInt()
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.enter_count), ToolbarType.ADD_OPERATION)
    }
}