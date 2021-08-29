package com.example.tinkoffproject.ui.main.wallet_add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.carddetails.UpdatableToolBar
import com.example.tinkoffproject.utils.formatMoney
import com.example.tinkoffproject.viewmodel.AddWalletViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class SetLimitFragment : Fragment(R.layout.layout_set_value) {
    private val viewModel: AddWalletViewModel by activityViewModels()
    private lateinit var btn: NextCustomButton

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

    private fun updateButtonState() {
        btn.changeState(if (isNextAvailable()) NextCustomButton.State.DEFAULT else NextCustomButton.State.DISABLED)
    }

    private fun initViews() {
        inputEditText = requireView().findViewById(R.id.et_sum)
        inputTextLayout = requireView().findViewById(R.id.input_sum)
        btn = requireView().findViewById(R.id.btn)
    }

    private fun setupNextButton() {
        btn.setOnClickListener {
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
        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if(p0 != null && !p0.isNullOrBlank()) {
                    inputEditText.removeTextChangedListener(this)
                    inputEditText.setText(formatMoney(p0))
                    inputEditText.setSelection(inputEditText.length())
                    inputEditText.addTextChangedListener(this)
                }
            }

        })
        inputEditText.doAfterTextChanged {
            updateButtonState()
            if (!it.isNullOrBlank()) {
                hideError()
            }
        }
    }

    private fun isNextAvailable(): Boolean {
        val str = inputEditText.text?.toString()
        return !str.isNullOrBlank() && str.replace(" ", "").toInt() != 0
    }

    private fun saveData() {
        val str = inputEditText.text.toString()
        viewModel.limit.value = str.replace(" ", "").toInt()
    }

    private fun setupData() {
        viewModel.limit.value?.let {
            inputEditText.setText(it.toString())
        }
    }

    private fun setupViews() {
        inputTextLayout.setHint(R.string.limit_desc)
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar("", ToolbarType.ADD_VALUE)
    }

}