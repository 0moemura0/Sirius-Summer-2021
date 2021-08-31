package com.example.tinkoffproject.ui.main.wallet_add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.base_fragment.edit.EditTextFragment
import com.example.tinkoffproject.viewmodel.AddWalletViewModel

class SetWalletNameFragment : EditTextFragment(R.layout.layout_set_value) {
    private val viewModel: AddWalletViewModel by activityViewModels()

    private val args: SetWalletNameFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
    }


    override fun setupNextButtonImpl() {
        setupNextButton(
            resId = R.id.action_to_newWallet,
            onSuccess = ::saveData,
            onError = ::setDefaultError
        )
    }

    private fun setupData() {
        if (args.isNewOperation) viewModel.init(args.wallet)
        if (args.wallet != null) {
            findNavController().navigate(R.id.action_setName_to_changeWallet)
        }
        setValue(viewModel.name.value ?: "")
    }

    private fun saveData() {
        viewModel.name.value = getValue()
    }
}