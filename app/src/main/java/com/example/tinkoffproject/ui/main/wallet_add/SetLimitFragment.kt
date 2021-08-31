package com.example.tinkoffproject.ui.main.wallet_add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.base_fragment.edit.EditMoneyFragment
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.viewmodel.AddWalletViewModel


class SetLimitFragment :
    EditMoneyFragment(R.layout.layout_set_value, toolbarType = ToolbarType.ADD_VALUE) {
    private val viewModel: AddWalletViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupData()
    }

    override fun setupNextButtonImpl() {
        setupNextButton(
            resId = R.id.action_to_newWallet,
            onSuccess = ::saveData,
            onError = ::setDefaultError,
        )
    }

    private fun saveData() {
        viewModel.limit.value = getValueAsInt()
    }

    private fun setupData() {
        viewModel.limit.value?.let {
            setValue(it.toString())
        }
    }

    private fun setupViews() {
        setHint(R.string.limit_desc)
    }
}