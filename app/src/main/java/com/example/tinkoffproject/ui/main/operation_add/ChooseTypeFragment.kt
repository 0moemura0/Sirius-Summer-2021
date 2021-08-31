package com.example.tinkoffproject.ui.main.operation_add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.base_fragment.edit.EditTypeFragment
import com.example.tinkoffproject.viewmodel.AddTransactionViewModel

class ChooseTypeFragment : EditTypeFragment(R.layout.operation_choose_type) {
    val viewModel: AddTransactionViewModel by activityViewModels()

    private val args: ChooseTypeFragmentArgs by navArgs()

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
        val action =
            if (args.isFromMain) R.id.action_to_newOperation else R.id.action_chooseType_to_chooseCategory
        setupNextButton(
            resId = action,
            context = context,
            onSuccess = ::saveData
        )
    }

    private fun saveData() {
        viewModel.type.value = getValue()
    }

    override fun isNextAvailable() = viewModel.type.value != null
}