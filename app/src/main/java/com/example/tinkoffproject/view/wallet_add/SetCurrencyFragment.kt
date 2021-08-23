package com.example.tinkoffproject.view.wallet_add

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.dto.Currency
import com.example.tinkoffproject.model.data.network.dto.CurrencyNetwork
import com.example.tinkoffproject.view.adapter.category.CategoryAdapter
import com.example.tinkoffproject.view.adapter.currency.CurrencyAdapter
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.view.data.OnItemSelectListener
import com.example.tinkoffproject.view.data.SelectableCurrency
import com.example.tinkoffproject.viewmodel.AddWalletViewModel

class SetCurrencyFragment : Fragment(R.layout.fragment_set_currency) {
    private val viewModel: AddWalletViewModel by activityViewModels()

    val DEFAULT_COUNT = 3

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
        val data = listOf(
            CurrencyNetwork("RUS", "Российский рубль"),
            CurrencyNetwork("USD", "Доллар США"),
            CurrencyNetwork("EUR", "Евро"),
            CurrencyNetwork("CHF", "Швейцарские франки"),
            CurrencyNetwork("KWD", "Кувейтский динар"),
            CurrencyNetwork("BHD", "Бахрейнский динар"),
            CurrencyNetwork("OMR", "Оманский риал"),
            CurrencyNetwork("JPY", "Японская иена"),
            CurrencyNetwork("SEK", "Шведская крона")
        )
        val recycler: RecyclerView = requireView().findViewById(R.id.rv_currency)
        val adapter = CurrencyAdapter()
        adapter.setData(data.subList(0, DEFAULT_COUNT))
        adapter.setOnItemClickListener(object : OnItemSelectListener {
            override fun onItemSelect(position: Int) {

            }
        })
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
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar("", ToolbarType.ADD_VALUE)
    }
}