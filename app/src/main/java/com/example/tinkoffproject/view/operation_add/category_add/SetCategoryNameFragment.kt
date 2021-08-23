package com.example.tinkoffproject.view.operation_add.category_add

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddCategoryViewModel
import com.google.android.material.textfield.TextInputLayout

class SetCategoryNameFragment: Fragment(R.layout.layout_set_value) {
    private val viewModel: AddCategoryViewModel by activityViewModels()

    private lateinit var inputEditText: EditText
    private lateinit var inputTextLayout: TextInputLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupView()
        setupData()
        setupInputText()
        setupToolbar()
    }

    private fun initViews() {
        inputEditText = requireView().findViewById(R.id.et_sum)
        inputTextLayout = requireView().findViewById(R.id.input_sum)
    }

    private fun setupInputText() {
        inputEditText.doAfterTextChanged {
            if(!it.isNullOrBlank())
                viewModel.name.value = it.toString()
        }
    }

    private fun setupData() {
        viewModel.name.observe(viewLifecycleOwner, {
            if(!it.isNullOrBlank())
                viewModel.isNextAvailable.value = true
        })
    }

    private fun setupView(){
        inputEditText.hint = requireContext().getString(R.string.category_name)
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.imaging_name), ToolbarType.ADD_OPERATION)
    }
}