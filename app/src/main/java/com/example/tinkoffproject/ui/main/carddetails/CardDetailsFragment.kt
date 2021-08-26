package com.example.tinkoffproject.ui.main.carddetails

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.State
import com.example.tinkoffproject.data.dto.response.CurrencyNetwork
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.utils.formatMoney
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.adapter.transaction.TransactionAdapter
import com.example.tinkoffproject.ui.main.adapter.transaction.TransactionItemDecorator
import com.example.tinkoffproject.ui.main.dialog.ChooseColorDialogFragment
import com.example.tinkoffproject.ui.main.dialog.ConfirmRemoveDialog
import com.example.tinkoffproject.utils.toLocal
import com.example.tinkoffproject.viewmodel.TransactionListViewModel

class CardDetailsFragment : Fragment(R.layout.fragment_card_details) {
    val viewModel: TransactionListViewModel by viewModels()

    private lateinit var walletAmount: TextView
    private lateinit var layoutIncome: View
    private lateinit var layoutIncomeCash: TextView
    private lateinit var layoutExpenses: View
    private lateinit var layoutExpensesCash: TextView
    private lateinit var walletName: TextView
    private lateinit var walletLimit: TextView

    private var transactionAdapter: TransactionAdapter? = null

    private val confirmDialog: ConfirmRemoveDialog by lazy {
        ConfirmRemoveDialog(R.string.confirm_remove_transaction)
    }

    private val args: CardDetailsFragmentArgs by navArgs()

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
        args.walletId
        //viewModel.wallet.observe(viewLifecycleOwner, ::updateWalletInfo)
        //viewModel.getTransactionList().observe(viewLifecycleOwner, ::updateTransaction)
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

    private fun updateWalletInfo(state: State<WalletNetwork>) {
        when (state) {
            is State.ErrorState -> onError(state.exception)
            is State.LoadingState -> {
                //TODO анимация либо на кнопке, либо свайп энд рефреш
            }
            is State.DataState -> {
                val wallet = state.data
                walletName.text = wallet.name
                walletAmount.text =
                    wallet.balance?.let { formatMoney(it.toInt(), wallet.currency.toLocal()) }

                layoutIncomeCash.text = formatMoney(0, wallet.currency.toLocal())
                layoutExpensesCash.text = formatMoney(0, wallet.currency.toLocal())


                wallet.balance?.let {
                    updateLimitInfo(wallet.limit.toDouble(),
                        it, wallet.currency)
                }
            }
        }
    }

    private fun onTransactionClick(position: Int) {
        val transaction = transactionAdapter?.data?.getOrNull(position)
        if (transaction != null) {

        }
    }

    private fun updateLimitInfo(limit: Double?, expenses: Double, currency: CurrencyNetwork) {
        val colorId: Int
        val alpha: Float
        val text: String
        if (limit == null) {
            text = ""
            colorId = R.color.white
            alpha = 1f
        } else {
            text = " / ${formatMoney(limit.toInt(), currency.toLocal())}"
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

    private fun updateTransaction(state: State<List<TransactionNetwork>>) {
        when (state) {
            is State.LoadingState -> {
            }
            is State.ErrorState -> onError(state.exception)
            is State.DataState -> {
                //transactionAdapter?.setData(state.dat)
            }
        }
    }

    private fun onError(e: Throwable?) {
        //TODO интерфейс активити, который бы воказывал сообщение об ошибке
    }


    private fun setupRecyclerView(view: View) {
        transactionAdapter = TransactionAdapter(::onTransactionClick)

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

        val swipe = object : MySwipeHelper(context, recycler, 180) {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder?,
                buffer: MutableList<MyButton>
            ) {
                buffer.add(
                    MyButton(
                        context!!,
                        R.drawable.ic_delete
                    ) { pos -> onRemoveClicked(pos) }
                )

                buffer.add(
                    MyButton(
                        context!!,
                        R.drawable.ic_edit
                    ) { pos -> onChangeClicked(pos) }
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(recycler)
    }

    private fun onRemoveClicked(pos: Int) {
        if (!confirmDialog.isAdded)
            confirmDialog.show(childFragmentManager, ChooseColorDialogFragment.TAG)

        confirmDialog.setOnItemClickListener {
            val text: String
            if (it == 0) text = "cancel"
            else text = "confirm"
            Toast.makeText(requireContext(), "DELETE $text $pos", Toast.LENGTH_SHORT).show()
            confirmDialog.dismiss()
        }
    }

    private fun onChangeClicked(pos: Int) {
        val transaction = transactionAdapter?.data?.getOrNull(pos)
        val action = CardDetailsFragmentDirections.actionToChangeTransaction(transaction)
        findNavController().navigate(action)
    }
}