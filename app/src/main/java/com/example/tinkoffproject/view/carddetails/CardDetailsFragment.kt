package com.example.tinkoffproject.view.carddetails

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.model.adapter.transaction.TransactionAdapter
import com.example.tinkoffproject.model.dto.Transaction
import com.example.tinkoffproject.model.dto.TransactionCategory
import com.example.tinkoffproject.model.dto.TransactionType

private val data = listOf(
    Transaction(
        id = 10,
        date = 1629294379553,
        type = TransactionType.INCOME,
        category = TransactionCategory.INCOME_PART_TIME_JOB,
        amount = 10000,
    ),
    Transaction(
        id = 10,
        date = 1629208049321,
        type = TransactionType.EXPENSES,
        category = TransactionCategory.INCOME_PART_TIME_JOB,
        amount = 10000,
    )
)

class CardDetailsFragment : Fragment(R.layout.fragment_card_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cashName: TextView = view.findViewById(R.id.tv_cash_name)
        val cashSum: TextView = view.findViewById(R.id.tv_cash_sum)
        val laouyt1: View = view.findViewById(R.id.income)
        val laouyt2: View = view.findViewById(R.id.consumption)
        val btn: TextView = view.findViewById(R.id.tv_add)

        cashSum.text = "0 ₽"
        cashName.text = "Кошелек 1"

        laouyt1.apply {
            findViewById<TextView>(R.id.tv_type).text = context.getString(R.string.income)
            findViewById<ImageView>(R.id.iv_dot).setImageResource(R.drawable.indicator_dot_green)
            findViewById<TextView>(R.id.tv_cash).text = "0 ₽"
        }
        laouyt2.apply {
            findViewById<TextView>(R.id.tv_type).text = context.getString(R.string.expenses)
            findViewById<ImageView>(R.id.iv_dot).setImageResource(R.drawable.indicator_dot_red)
            findViewById<TextView>(R.id.tv_cash).text = "12 000 ₽"
        }
        btn.setOnClickListener {
            Toast.makeText(view.context, getString(R.string.added), Toast.LENGTH_LONG).show()
        }

        setupRecyclerView(view)
    }

    private fun setupRecyclerView(view: View) {
        val transactionAdapter = TransactionAdapter().apply {
            setHasStableIds(true)
        }
        val recycler: RecyclerView = view.findViewById(R.id.rcv_transaction)
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }
        Handler(Looper.getMainLooper()).postDelayed({
            transactionAdapter.setData(data)
        }, 3000)

        /*val itemTouchHelper = ItemTouchHelper(SampleTouchCallback(sampleAdapter))
        itemTouchHelper.attachToRecyclerView(recycler)*/


    }
}