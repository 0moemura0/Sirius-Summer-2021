package com.example.tinkoffproject.view.wallet_add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.adapter.category.CategoryAdapter
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddWalletViewModel

class SetCurrencyFragment : Fragment(R.layout.fragment_set_currency) {
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

    }

    private fun setData() {

        val recycler: RecyclerView = requireView().findViewById(R.id.rv_currency)

        val adapter = CategoryAdapter()

    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar("", ToolbarType.ADD_VALUE)
    }
}