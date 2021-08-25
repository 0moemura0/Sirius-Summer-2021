package com.example.tinkoffproject.view.wallet_add

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.dto.Currency
import com.example.tinkoffproject.model.data.dto.Transaction
import com.example.tinkoffproject.model.data.dto.Wallet
import com.example.tinkoffproject.model.data.mapper.WALLET_AS_CATEGORY
import com.example.tinkoffproject.model.utils.State
import com.example.tinkoffproject.model.utils.formatMoney
import com.example.tinkoffproject.view.NextCustomButton
import com.example.tinkoffproject.view.adapter.transaction.TransactionAdapter
import com.example.tinkoffproject.view.adapter.transaction.TransactionItemDecorator
import com.example.tinkoffproject.view.carddetails.*
import com.example.tinkoffproject.viewmodel.WalletListViewModel
import java.text.DecimalFormat

class WalletsListFragment : Fragment(R.layout.fragment_wallets_list) {
    private val viewModel: WalletListViewModel by activityViewModels()

    private lateinit var layoutIncome: View
    private lateinit var layoutIncomeCash: TextView
    private lateinit var layoutExpenses: View
    private lateinit var layoutExpensesCash: TextView

    private lateinit var currencyContainer1: View
    private lateinit var currencyContainer2: View
    private lateinit var currencyContainer3: View

    private var transactionAdapter: TransactionAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupToolbar()
        setupExpensesIncomeLayout()
        setupNavigation()
        setupRecycler()
        setupCurrency()
    }

    private fun setupCurrency() {
        viewModel.loadCurrencyInfo()
        viewModel.currency.observe(viewLifecycleOwner, {
            updateCurrencyInfo(it[0], currencyContainer1)
            updateCurrencyInfo(it[1], currencyContainer2)
            updateCurrencyInfo(it[2], currencyContainer3)
        })
    }

    private fun updateCurrencyInfo(currency: Currency, container: View) {
        val dec = DecimalFormat("#.##")
        container.findViewById<TextView>(R.id.tv_name).text = currency.shortName
        container.findViewById<TextView>(R.id.tv_delta).text = dec.format(currency.rate)
        val arrow = container.findViewById<ImageView>(R.id.uv_arrow)
        val arrayResId = if (currency.isUp) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
        arrow.setImageResource(arrayResId)
    }

    private fun setupRecycler() {
        transactionAdapter = TransactionAdapter(::onWalletClick, false)

        transactionAdapter.apply {
            //setHasStableIds(true)
        }
        val recycler: RecyclerView = requireView().findViewById(R.id.rv_wallets)

        val offsetNormal = resources.getDimension(R.dimen.view_dimen_normal).toInt()
        val decorator = TransactionItemDecorator(offsetNormal)
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
            addItemDecoration(decorator)
        }
        val swipe = object : MySwipeHelper(context, recycler, 180) {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder?,
                buffer: MutableList<MyButton>
            ) {

                buffer.apply {
                    add(
                        MyButton(
                            context!!,
                            R.drawable.ic_delete,
                            object : MyButtonClickListener {
                                override fun onClick(pos: Int) {
                                    Toast.makeText(
                                        context!!,
                                        "DLETE Clicked $pos",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    )
                    add(
                        MyButton(
                            context!!,
                            R.drawable.ic_edit,
                            object : MyButtonClickListener {
                                override fun onClick(pos: Int) {
                                    Toast.makeText(
                                        context!!,
                                        "EDIT Clicked $pos",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    )
                    add(
                        MyButton(
                            context!!,
                            R.drawable.ic_hide,
                            object : MyButtonClickListener {
                                override fun onClick(pos: Int) {
                                    Toast.makeText(
                                        context!!,
                                        "HIDE Clicked $pos",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    )

                }

            }

        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(recycler)


        viewModel.wallets.observe(viewLifecycleOwner, ::updateWallets)
    }

    private fun onWalletClick(position: Int) {
        val wallet = transactionAdapter?.data?.getOrNull(position)
        if (wallet != null) {
            val action = WalletsListFragmentDirections.actionToTransactions(wallet.id)
            findNavController().navigate(action)
        }
    }

    private fun updateWallets(state: State<List<Wallet>>) {
        when (state) {
            is State.LoadingState -> {
            }
            is State.ErrorState -> onError(state.exception)
            is State.DataState -> {
                transactionAdapter?.setData(state.data.map {
                    Transaction(
                        id = it.id,
                        date = 0,
                        isIncome = false,
                        category = WALLET_AS_CATEGORY,
                        amount = it.incomeAmount - it.expensesAmount,
                        amountFormatted = formatMoney(
                            it.incomeAmount - it.expensesAmount,
                            it.currency
                        )
                    )
                })
            }
        }
    }

    private fun onError(e: Throwable?) {
        //TODO интерфейс активити, который бы воказывал сообщение об ошибке
    }

    private fun initViews() {
        layoutIncome = requireView().findViewById(R.id.wallets_income)
        layoutIncomeCash = layoutIncome.findViewById(R.id.tv_cash)
        layoutExpenses = requireView().findViewById(R.id.wallets_expenses)
        layoutExpensesCash = layoutExpenses.findViewById(R.id.tv_cash)
        currencyContainer1 = requireView().findViewById(R.id.l_currency_1)
        currencyContainer2 = requireView().findViewById(R.id.l_currency_2)
        currencyContainer3 = requireView().findViewById(R.id.l_currency_3)
    }

    private fun setupExpensesIncomeLayout() {
        layoutIncome.apply {
            findViewById<TextView>(R.id.tv_type).setText(R.string.income)
            findViewById<ImageView>(R.id.iv_dot).setImageResource(R.drawable.indicator_dot_green)
        }
        layoutExpenses.apply {
            findViewById<TextView>(R.id.tv_type).setText(R.string.expenses)
            findViewById<ImageView>(R.id.iv_dot).setImageResource(R.drawable.indicator_dot_red)
        }
    }

    private fun setupNavigation() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
            val action = WalletsListFragmentDirections.actionToAddWallet(true)
            findNavController().navigate(action)
        }
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar("", ToolbarType.INVISIBLE)
    }
}