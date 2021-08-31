package com.example.tinkoffproject.ui.main.category_custom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.base_fragment.edit.EditTextFragment
import com.example.tinkoffproject.viewmodel.CustomCategoryViewModel

class CustomCategorySetNameFragment : EditTextFragment(R.layout.layout_set_value) {
    val viewModel: CustomCategoryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    override fun setupNextButtonImpl() {
        setupNextButton(
            resId = R.id.action_to_newCategory,
            onSuccess = ::saveData,
            onError = ::setDefaultError,
        )
    }

    private fun setupView() {
        setHint(R.string.category_name)
    }

    private fun saveData() {
        viewModel.name.value = getValue()
    }
}