package com.example.tinkoffproject.ui.main.category_custom

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.CustomCategoryViewModel
import com.google.android.material.textfield.TextInputLayout

class CustomCategorySetNameFragment : Fragment(R.layout.layout_set_value) {
    val viewModel: CustomCategoryViewModel by activityViewModels()
    private lateinit var btn: NextCustomButton

    private lateinit var inputEditText: EditText
    private lateinit var inputTextLayout: TextInputLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupView()
        setupInputText()
        setupToolbar()
        setupNextButton()
    }

    private fun setupNextButton() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
            if (isNextAvailable()) {
                viewModel.name.value = inputEditText.text.toString()
                findNavController().navigate(R.id.action_to_newCategory)
            } else {
                setDefaultError()
            }
        }
    }

    private fun isNextAvailable() = !inputEditText.text.isNullOrBlank()

    private fun initViews() {
        inputEditText = requireView().findViewById(R.id.et_sum)
        inputTextLayout = requireView().findViewById(R.id.input_sum)
        btn = requireView().findViewById(R.id.btn)
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

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.choose_name), ToolbarType.ADD_OPERATION)
    }

    private fun setupView() {
        inputTextLayout.setHint(R.string.category_name)
        inputEditText.inputType = InputType.TYPE_CLASS_TEXT
    }

    private fun setDefaultError() {
        val text = requireContext().getString(R.string.wrong_input)
        setError(text)
    }

    private fun setError(text: String?) {
        inputTextLayout.error = text
    }

    private fun hideError() = setError(null)
}