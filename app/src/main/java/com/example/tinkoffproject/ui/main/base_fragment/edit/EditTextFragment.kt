package com.example.tinkoffproject.ui.main.base_fragment.edit

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

abstract class EditTextFragment(
    @LayoutRes res: Int,
    @StringRes private val toolbarRes: Int = R.string.choose_name,
    toolbarType: ToolbarType = ToolbarType.ADD_OPERATION
) : EditFragment(res, toolbarRes, toolbarType) {

    lateinit var inputEditText: TextInputEditText
    private lateinit var inputTextLayout: TextInputLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupInputText()
    }

    override fun initViews() {
        inputEditText = requireView().findViewById(R.id.et_sum)
        inputTextLayout = requireView().findViewById(R.id.input_sum)
    }
    open fun setupInputText() {
        updateButtonState()
        inputEditText.inputType = InputType.TYPE_CLASS_TEXT

        inputEditText.doAfterTextChanged {
            updateButtonState()
            if (!it.isNullOrBlank()) {
                hideError()
            }
        }
    }

    override fun getValue(): String? = inputEditText.text?.toString()

    fun setDefaultError() {
        val text = requireContext().getString(R.string.wrong_input)
        setError(text)
    }

    fun setError(text: String?) {
        inputTextLayout.error = text
    }

    fun hideError() = setError(null)


    fun setHint(@StringRes res: Int){
        inputTextLayout.setHint(res)
    }
    fun setValue(str: String){
        inputEditText.setText(str)
    }

    override fun isNextAvailable() = !getValue().isNullOrBlank()
}