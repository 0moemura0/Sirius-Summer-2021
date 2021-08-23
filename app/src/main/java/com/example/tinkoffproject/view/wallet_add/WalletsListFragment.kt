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

class WalletsListFragment : Fragment(R.layout.fragment_wallets_list) {
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
        findNavController().navigate(R.id.action_walletsListFragment_to_setNameFragment)
    }

    private fun setData() {
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.choose_name), ToolbarType.INVISIBLE)
    }
}