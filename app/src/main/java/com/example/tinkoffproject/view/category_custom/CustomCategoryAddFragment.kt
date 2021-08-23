package com.example.tinkoffproject.view.category_custom

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.NextCustomButton
import com.example.tinkoffproject.view.adapter.category.CustomCategoryAdapter
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.view.data.OnItemSelectListener
import com.example.tinkoffproject.view.dialog.ChooseColorDialogFragment

class CustomCategoryAddFragment : Fragment(R.layout.fragment_categoty_add) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = ChooseColorDialogFragment()

        val icons = listOf(
            R.drawable.ic_shop,
            R.drawable.ic_plain,
            R.drawable.ic_diamond,
            R.drawable.ic_cafe,
            R.drawable.ic_medicine,
            R.drawable.ic_cup,
            R.drawable.ic_wi_fi,
            R.drawable.ic_theather,
            R.drawable.ic_train,
            R.drawable.ic_pet,
            R.drawable.ic_sport,
            R.drawable.ic_net,
            R.drawable.ic_bus,
            R.drawable.ic_palma,
            R.drawable.ic_hands,
            R.drawable.ic_music,
            R.drawable.ic_cap,
            R.drawable.ic_gift,
            R.drawable.ic_phone,
            R.drawable.ic_garden,
            R.drawable.ic_movie,
            R.drawable.ic_car,
            R.drawable.ic_medicine_bag,
            R.drawable.ic_education,
            R.drawable.ic_tv,
            R.drawable.ic_icon,
            R.drawable.ic_wear,
            R.drawable.ic_money,
            R.drawable.ic_balloon,
            R.drawable.ic_dots
        )
        val recycler: RecyclerView = view.findViewById(R.id.rv_icons)
        val customAdapter = CustomCategoryAdapter()
        customAdapter.setData(icons)

        val manager = GridLayoutManager(context, COLUMNS_COUNT)
        recycler.adapter = customAdapter
        recycler.layoutManager = manager

        val nameLayout: View = view.findViewById(R.id.ll_name_container)
        val typeLayout: View = view.findViewById(R.id.ll_type_container)
        val colorLayout: View = view.findViewById(R.id.ll_icon_color_container)

        val nameTitle: TextView = nameLayout.findViewById(R.id.tv_title)
        val typeTitle: TextView = typeLayout.findViewById(R.id.tv_title)
        val colorTitle: TextView = colorLayout.findViewById(R.id.tv_title)

        nameTitle.setText(R.string.name)
        typeTitle.setText(R.string.type)
        colorTitle.setText(R.string.icon)

        val nameValue: TextView = nameLayout.findViewById(R.id.tv_value)
        val typeValue: TextView = typeLayout.findViewById(R.id.tv_value)
        val colorValue: TextView = colorLayout.findViewById(R.id.tv_value)

        nameValue.setText(R.string.category_new)
        typeValue.setText(R.string.income)
        colorValue.setText(R.string.choose_color)

        dialog.setOnItemClickListener(object : OnItemSelectListener {
            override fun onItemSelect(position: Int) {
                colorValue.setTextColor(ColorStateList.valueOf(position))
                customAdapter.setCurrentColor(position)
                dialog.dismiss()
            }
        })

        colorLayout.setOnClickListener {
            if (!dialog.isAdded)
                dialog.show(childFragmentManager, ChooseColorDialogFragment.TAG)
        }

        nameLayout.setOnClickListener {
            findNavController().navigate(R.id.action_categoryAddFragment_to_categoryChooseName)
        }

        typeLayout.setOnClickListener {
            findNavController().navigate(R.id.action_categoryAddFragment_to_chooseTypeFragment)
        }
        setupNextButton()
        setupToolbar()
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.category_new), ToolbarType.ADD_OPERATION)
    }

    private fun setupNextButton() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
//            if (viewModel.isNextAvailable.value == true) {
//                viewModel.prepareNext()
//                findNavController().navigate(R.id.action_chooseCategoryFragment_to_newOperationFragment)
//            } else {
//                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
//                    .show()
//            }
        }
    }

    companion object {
        private const val COLUMNS_COUNT = 6
    }
}