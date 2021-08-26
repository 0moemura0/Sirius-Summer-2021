package com.example.tinkoffproject.ui.main.wallet_add

import android.os.Bundle
import android.text.InputType
import android.view.View
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
import com.example.tinkoffproject.viewmodel.AddWalletViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SetWalletNameFragment : Fragment(R.layout.layout_set_value) {
    private val viewModel: AddWalletViewModel by activityViewModels()
    private lateinit var btn: NextCustomButton

    private lateinit var inputEditText: TextInputEditText
    private lateinit var inputTextLayout: TextInputLayout

    private val args: SetWalletNameFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupData()

        setupViews()
        setupNextButton()
        setupInputText()
        setupToolbar()
        setupNextButton()
    }
    private fun updateButtonState() {
        btn.changeState(if (isNextAvailable()) NextCustomButton.State.DEFAULT else NextCustomButton.State.DISABLED)
    }

    private fun initViews() {
        inputEditText = requireView().findViewById(R.id.et_sum)
        inputTextLayout = requireView().findViewById(R.id.input_sum)
        btn = requireView().findViewById(R.id.btn)
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
        updateButtonState()
        inputEditText.doAfterTextChanged {
            updateButtonState()
            if (!it.isNullOrBlank()) {
                hideError()
            }
        }
    }

    private fun setupData() {
        if (args.isNewOperation) viewModel.init(args.wallet)
        if (args.wallet != null) {
            findNavController().navigate(R.id.action_setName_to_changeWallet)
        }
        inputEditText.setText(viewModel.name.value)
    }

    private fun isNextAvailable(): Boolean {
        val str = inputEditText.text.toString()
        return str.isNotBlank()
    }

    private fun saveData() {
        viewModel.name.value = inputEditText.text?.toString()
    }


    private fun setupViews() {
        inputTextLayout.setHint(R.string.wallet_name)
        inputEditText.inputType = InputType.TYPE_CLASS_TEXT
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.choose_name), ToolbarType.ADD_OPERATION)
    }
}