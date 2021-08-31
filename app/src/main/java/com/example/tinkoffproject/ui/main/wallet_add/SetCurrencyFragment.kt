package com.example.tinkoffproject.ui.main.wallet_add

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.data.dto.to_view.Currency
import com.example.tinkoffproject.ui.main.adapter.currency.CurrencyAdapter
import com.example.tinkoffproject.ui.main.base_fragment.edit.EditFragment
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.viewmodel.AddWalletViewModel

class SetCurrencyFragment :
    EditFragment(R.layout.fragment_set_currency, toolbarType =  ToolbarType.ADD_VALUE) {
    private val viewModel: AddWalletViewModel by activityViewModels()

    companion object {
        const val DEFAULT_COUNT = 3
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupNextButton()
        setupBtnObserver()
    }

    private fun onCurrencySelect(currency: Currency, isSelected: Boolean = true) {
        viewModel.currency.value = if (isSelected) currency else null
    }

    override fun isNextAvailable(): Boolean = getValue() != null

    private fun setupBtnObserver() {
        viewModel.currency.observe(viewLifecycleOwner, {
            updateButtonState()
        })
    }

    override fun setupNextButtonImpl() {
        setupNextButton(
            context = context,
            resId = R.id.action_to_newWallet
        )
    }

    private fun setupRecyclerView() {
        val data = viewModel.getAllCurrency()
        val recycler: RecyclerView = requireView().findViewById(R.id.rv_currency)
        val adapter = CurrencyAdapter()
        adapter.setData(data.subList(0, DEFAULT_COUNT))
        adapter.setOnItemClickListener { position ->
            onCurrencySelect(
                data[position],
                adapter.isItemSelected(position)
            )
        }
        val manager = LinearLayoutManager(view?.context)
        recycler.adapter = adapter
        recycler.layoutManager = manager

        val showMore: View = requireView().findViewById(R.id.l_show_more)
        val showMoreTitle: TextView = showMore.findViewById(R.id.tv_title)
        val showMoreIcon: ImageView = showMore.findViewById(R.id.iv_arrow)
        showMoreTitle.setText(R.string.show_more)
        showMoreIcon.rotation = 0F

        showMore.setOnClickListener {
            if (adapter.itemCount > DEFAULT_COUNT) {
                adapter.removeData(data.size - DEFAULT_COUNT)
                showMoreTitle.setText(R.string.show_more)
                showMoreIcon.rotation = 0F
            } else {
                adapter.updateData(data.subList(DEFAULT_COUNT, data.size))
                showMoreTitle.setText(R.string.turn)
                showMoreIcon.rotation = 180F
            }
        }

        getValue()?.let { currency ->
            val i = data.indexOfFirst { it.shortName == currency.shortName }

            if (i >= 0) {
                adapter.onItemSelect(i)
            }
        }
    }

    override fun getValue(): Currency? = viewModel.currency.value
}