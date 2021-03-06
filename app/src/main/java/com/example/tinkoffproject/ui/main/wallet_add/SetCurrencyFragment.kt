package com.example.tinkoffproject.ui.main.wallet_add

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.data.dto.to_view.Currency
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.adapter.currency.CurrencyAdapter
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddWalletViewModel

class SetCurrencyFragment : Fragment(R.layout.fragment_set_currency) {
    private val viewModel: AddWalletViewModel by activityViewModels()
    private lateinit var btn: NextCustomButton
    val DEFAULT_COUNT = 3

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn = requireView().findViewById(R.id.btn)

        setupRecyclerView()
        setupNextButton()
        setupToolbar()
        setupBtnObserver()
    }

    private fun onCurrencySelect(currency: Currency, isSelected: Boolean = true) {
        viewModel.currency.value = if (isSelected) currency else null
    }

    private fun isNextAvailable(): Boolean = viewModel.currency.value != null

    private fun setupBtnObserver() {
        viewModel.currency.observe(viewLifecycleOwner, {
            updateButtonState()
        })
    }

    private fun updateButtonState() {
        btn.changeState(if (isNextAvailable()) NextCustomButton.State.DEFAULT else NextCustomButton.State.DISABLED)
    }

    private fun setupNextButton() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
            if (isNextAvailable()) {
                findNavController().navigate(R.id.action_to_newWallet)
            } else {
                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
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

        viewModel.currency.value?.let { currency ->
            val i = data.indexOfFirst { it.shortName == currency.shortName }

            if (i >= 0) {
                adapter.onItemSelect(i)
            }
        }
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar("", ToolbarType.ADD_VALUE)
    }
}