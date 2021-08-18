package com.example.tinkoffproject.view.operation_add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.viewmodel.AddOperationViewModel

class ChooseCategoryFragment : Fragment(R.layout.operation_choose_category) {
    private val viewModel: AddOperationViewModel by activityViewModels()

    enum class IncomeType(val stringId: Int, val iconId: Int) {
        GIFT(R.string.gift, R.drawable.ic_gift),
        PART_TIME(R.string.part_time_job, R.drawable.ic_card),
        PERSENTS(R.string.percents, R.drawable.ic_percent),
        SALARY(R.string.salary, R.drawable.ic_card),
    }

    data class IncomeObject(val type: IncomeType, var isChecked: Boolean = false)

    private val categoryAdapter = CategoryAdapter()

    private val keysIncome = listOf(
        IncomeObject(IncomeType.GIFT),
        IncomeObject(IncomeType.PART_TIME),
        IncomeObject(IncomeType.PERSENTS),
        IncomeObject(IncomeType.SALARY)
    )

    companion object {
        fun isSelectedDefault(selected: Int) = selected == INT_DEFAULT
        private const val INT_DEFAULT = -1
        private const val KEY_CURRENT_SELECTED = "CURRENT_SELECTED"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_SELECTED, categoryAdapter.currentSelected)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler: RecyclerView = view.findViewById(R.id.rv_category)
        var isNextAvailable: Boolean

        val oldSelected = savedInstanceState
            ?.getInt(KEY_CURRENT_SELECTED) ?: INT_DEFAULT
        if (!isSelectedDefault(oldSelected))
            keysIncome[oldSelected].isChecked = true
        isNextAvailable = !isSelectedDefault(categoryAdapter.currentSelected)


        categoryAdapter.apply {
            setData(keysIncome)
            currentSelected = oldSelected
            setOnItemClickListener(object : OnItemSelectListener {
                override fun onItemSelect(position: Int) {
                    isNextAvailable = true
                }
            })
        }

        recycler.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = categoryAdapter
        }

        val btn: TextView = view.findViewById(R.id.btn)
        btn.setOnClickListener {
            if (isNextAvailable) {
                val stringId = keysIncome[categoryAdapter.currentSelected].type.stringId
                viewModel._category.value =
                    resources.getString(stringId)
                findNavController().navigate(R.id.action_chooseCategoryFragment_to_newOperationFragment)
            } else {
                Toast.makeText(view.context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        updateToolbar()
    }

    private fun updateToolbar() {
        activity?.findViewById<TextView>(R.id.title)?.text = getString(R.string.choose_category)
        activity?.findViewById<ImageView>(R.id.iv_settings)?.visibility = View.INVISIBLE
    }

    interface OnItemSelectListener {
        fun onItemSelect(position: Int)
    }

    class CategoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        lateinit var listener: OnItemSelectListener

        private val data = mutableListOf<IncomeObject>()
        var currentSelected = INT_DEFAULT

        inner class CategoryViewHolder(root: View) : RecyclerView.ViewHolder(root) {

            private val icon: ImageView = root.findViewById(R.id.iv_category_icon)
            private val textView: TextView = root.findViewById(R.id.tv_category_name)
            private val checked: ImageView = root.findViewById(R.id.iv_checked)

            fun bind(i: IncomeObject) {
                icon.setImageResource(i.type.iconId)
                textView.setText(i.type.stringId)
                checked.visibility = if (i.isChecked) View.VISIBLE else View.INVISIBLE
            }

        }

        fun setData(_data: List<IncomeObject>) {
            data.addAll(_data)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val holder = CategoryViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
            )
            holder.itemView.setOnClickListener {
                if (!isSelectedDefault(currentSelected)) {
                    data[currentSelected].isChecked = false
                    notifyItemChanged(currentSelected)
                }
                currentSelected = holder.adapterPosition
                data[currentSelected].isChecked = true
                notifyItemChanged(currentSelected)
                listener.onItemSelect(currentSelected)
            }
            return holder
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as CategoryViewHolder).bind(data[position])
        }

        override fun getItemCount(): Int = data.size

        fun setOnItemClickListener(_listener: OnItemSelectListener) {
            listener = _listener
        }
    }
}