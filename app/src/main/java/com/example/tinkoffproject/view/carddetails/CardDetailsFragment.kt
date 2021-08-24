package com.example.tinkoffproject.view.carddetails

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.data.dto.Category
import com.example.tinkoffproject.model.data.dto.Currency
import com.example.tinkoffproject.model.data.dto.Transaction
import com.example.tinkoffproject.model.data.dto.Wallet
import com.example.tinkoffproject.model.utils.State
import com.example.tinkoffproject.model.utils.formatMoney
import com.example.tinkoffproject.view.NextCustomButton
import com.example.tinkoffproject.view.adapter.transaction.TransactionAdapter
import com.example.tinkoffproject.view.adapter.transaction.TransactionItemDecorator
import com.example.tinkoffproject.viewmodel.CardDetailsViewModel

private val data: List<Transaction> = listOf(
    Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
    Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
    Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
    Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
    Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
    Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
    Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
    Transaction(0, 112213214, false, Category("Имя", R.color.blue_main, 1, false), 123, "235 P"),
)

class CardDetailsFragment : Fragment(R.layout.fragment_card_details) {
    private val viewModel: CardDetailsViewModel by viewModels()

    private lateinit var walletAmount: TextView
    private lateinit var layoutIncome: View
    private lateinit var layoutIncomeCash: TextView
    private lateinit var layoutExpenses: View
    private lateinit var layoutExpensesCash: TextView
    private lateinit var walletName: TextView
    private lateinit var walletLimit: TextView

    private var transactionAdapter: TransactionAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setupExpensesIncomeLayout()
        setupNavigation()
        setupToolbar()
        setupDataObservers()

        setupRecyclerView(view)
    }

    private fun initViews() {
        walletAmount = requireView().findViewById(R.id.tv_cash_sum)
        layoutIncome = requireView().findViewById(R.id.income)
        layoutIncomeCash = layoutIncome.findViewById(R.id.tv_cash)
        layoutExpenses = requireView().findViewById(R.id.consumption)
        layoutExpensesCash = layoutExpenses.findViewById(R.id.tv_cash)
        walletName = requireView().findViewById(R.id.tv_cash_name)
        walletLimit = layoutExpenses.findViewById(R.id.tv_cash_limit)

    }

    private fun setupDataObservers() {
        viewModel.wallet.observe(viewLifecycleOwner, ::updateWalletInfo)
        viewModel.transaction.observe(viewLifecycleOwner, ::updateTransaction)
    }

    private fun setupExpensesIncomeLayout() {
        layoutIncome.apply {
            findViewById<TextView>(R.id.tv_type).text = context.getString(R.string.income)
            findViewById<ImageView>(R.id.iv_dot).setImageResource(R.drawable.indicator_dot_green)
        }
        layoutExpenses.apply {
            findViewById<TextView>(R.id.tv_type).text = context.getString(R.string.expenses)
            findViewById<ImageView>(R.id.iv_dot).setImageResource(R.drawable.indicator_dot_red)
        }
    }

    private fun setupNavigation() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
            val action = CardDetailsFragmentDirections.actionCardDetailsToAddTransaction(true)
            findNavController().navigate(action)
        }
    }

    private fun setupToolbar() {
        val updateActivity = activity as MainActivity
        updateActivity.updateToolbar("")
    }

    private fun updateWalletInfo(state: State<Wallet>) {
        when (state) {
            is State.ErrorState -> onError(state.exception)
            is State.LoadingState -> {
                //TODO анимация либо на кнопке, либо свайп энд рефреш
            }
            is State.DataState -> {
                val wallet = state.data
                walletName.text = wallet.name
                walletAmount.text =
                    formatMoney(wallet.incomeAmount - wallet.expensesAmount, wallet.currency)

                layoutIncomeCash.text = formatMoney(wallet.incomeAmount, wallet.currency)
                layoutExpensesCash.text = formatMoney(wallet.expensesAmount, wallet.currency)

                transactionAdapter?.currency = wallet.currency

                updateLimitInfo(wallet.limit, wallet.expensesAmount, wallet.currency)
            }
        }
    }

    private fun updateLimitInfo(limit: Int?, expenses: Int, currency: Currency) {
        val colorId: Int
        val alpha: Float
        val text: String
        if (limit == null) {
            text = ""
            colorId = R.color.white
            alpha = 1f
        } else {
            text = " / ${formatMoney(limit, currency)}"
            if (limit > expenses) {
                colorId = R.color.red_main
                alpha = 1f
            } else {
                colorId = R.color.white
                alpha = 0.6f
            }
        }

        walletLimit.alpha = alpha
        walletLimit.text = text
        walletLimit.setTextColor(ContextCompat.getColor(walletLimit.context, colorId))
    }

    private fun updateTransaction(state: State<List<Transaction>>) {
        when (state) {
            is State.LoadingState -> {
            }
            is State.ErrorState -> onError(state.exception)
            is State.DataState -> {
                transactionAdapter?.setData(state.data)
            }
        }
    }

    private fun onError(e: Throwable?) {
        //TODO интерфейс активити, который бы воказывал сообщение об ошибке
    }


    private fun setupRecyclerView(view: View) {
        transactionAdapter = TransactionAdapter()

        transactionAdapter.apply {
            //setHasStableIds(true)
        }
        val recycler: RecyclerView = view.findViewById(R.id.rcv_transaction)

        val offsetNormal = resources.getDimension(R.dimen.view_dimen_normal).toInt()
        val decorator = TransactionItemDecorator(offsetNormal)
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
            addItemDecoration(decorator)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            transactionAdapter?.setData(data)
        }, 2000)

        val swipe = object : MySwipeHelper(context, recycler, 180) {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder?,
                buffer: MutableList<MyButton>
            ) {
                buffer.add(
                    MyButton(
                        context!!,
                        R.drawable.ic_edit,
                        object: MyButtonClickListener {
                            override fun onClick(pos: Int) {
                                Toast.makeText(
                                    context!!,
                                    "EDIT Clicked $pos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                )

                buffer.add(
                    MyButton(context!!,
                        R.drawable.ic_delete,
                        object: MyButtonClickListener {
                            override fun onClick(pos: Int) {
                                Toast.makeText(
                                    context!!,
                                    "DELETE Clicked $pos",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                        }
                    )
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(recycler)

    }
}