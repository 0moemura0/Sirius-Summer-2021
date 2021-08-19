package com.example.tinkoffproject.view.carddetails

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R

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
            findViewById<TextView>(R.id.tv_type).text = context.getString(R.string.consumption)
            findViewById<ImageView>(R.id.iv_dot).setImageResource(R.drawable.indicator_dot_red)
            findViewById<TextView>(R.id.tv_cash).text = "12 000 ₽"
        }
        btn.setOnClickListener {
            findNavController().navigate(R.id.action_cardDetailsFragment_to_setCashFragment)
        }
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar("")
    }

}