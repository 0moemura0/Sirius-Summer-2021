package com.example.tinkoffproject.ui.main.category_custom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.base_fragment.edit.EditTypeFragment
import com.example.tinkoffproject.ui.main.base_fragment.WithNextButton
import com.example.tinkoffproject.viewmodel.CustomCategoryViewModel

class CustomCategoryChooseTypeFragment
    : EditTypeFragment(R.layout.operation_choose_type), WithNextButton {
    val viewModel: CustomCategoryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
    }

    private fun setupData() {
        viewModel.type.value?.let {
            setValue(it)
        }
    }

    override fun setupNextButtonImpl() {
        setupNextButton(
            resId = R.id.action_to_newCategory,
            onSuccess = ::saveData,
            context = context
        )
    }

    private fun saveData() {
        viewModel.type.value = getValue()
    }

    override fun isNextAvailable() = viewModel.type.value != null
}