package com.example.tinkoffproject.view.wallet_add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddWalletViewModel
import com.google.android.material.textfield.TextInputEditText

class SetNameFragment : Fragment(R.layout.layout_set_value) {
    private val viewModel: AddWalletViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        setupNextButton()
        setupToolbar()
        setupNavigation()
        //viewModel.isNextAvailable.value = true
    }

    private fun setupNavigation() {
    }

    private fun setupNextButton() {
        findNavController().navigate(R.id.action_setNameFragment_to_newWalletFragment)
    }

    private fun setData() {
        val input: TextInputEditText = requireView().findViewById(R.id.et_sum)
        input.setHint(R.string.wallet_name)
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.choose_name), ToolbarType.ADD_OPERATION)
    }
}