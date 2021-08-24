package com.example.tinkoffproject.view.category_custom

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.NextCustomButton
import com.example.tinkoffproject.view.adapter.category_custom.CustomCategoryAdapter
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.view.data.CategoryType
import com.example.tinkoffproject.view.data.OnItemSelectListener
import com.example.tinkoffproject.view.dialog.ChooseColorDialogFragment
import com.example.tinkoffproject.viewmodel.CustomCategoryViewModel

class CustomCategoryAddFragment : Fragment(R.layout.fragment_categoty_add) {
    private val viewModel: CustomCategoryViewModel by activityViewModels()

    private val args: CustomCategoryAddFragmentArgs by navArgs()
    val customAdapter: CustomCategoryAdapter by lazy { CustomCategoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupNextButton()
        setupToolbar()
        setupRecycler()

        val dialog = ChooseColorDialogFragment()

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

        if (args.isNewOperation)
            viewModel.type.value = if (args.isIncome) CategoryType.INCOME else CategoryType.EXPENSE

        nameValue.text = viewModel.name.value ?: getString(R.string.category_new)
        typeValue.setText(viewModel.type.value?.nameResId ?: R.string.dont_set)
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
            findNavController().navigate(R.id.action_newCategory_to_setCategoryName)
        }

        typeLayout.setOnClickListener {
            findNavController().navigate(R.id.action_newCategory_to_chooseNewCategoryType)
        }
    }

    private fun setupData() {
        if (args.isNewOperation) viewModel.init()
    }

    private fun setupRecycler() {
        val recycler: RecyclerView = requireView().findViewById(R.id.rv_icons)
        customAdapter.setData(viewModel.icons)

        val manager = GridLayoutManager(context, COLUMNS_COUNT)
        recycler.adapter = customAdapter
        recycler.layoutManager = manager

        customAdapter.setOnItemClickListener(object : OnItemSelectListener {
            override fun onItemSelect(position: Int) {
                viewModel.iconId.value = viewModel.icons[position]
            }
        })
    }


    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.category_new), ToolbarType.ADD_OPERATION)
    }

    private fun setupNextButton() {
        requireView().findViewById<NextCustomButton>(R.id.btn).setOnClickListener {
            if (isNextAvailable()) {
                viewModel.addCategory()
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isNextAvailable() = viewModel.colorId.value != null
            && viewModel.name.value != null
            && viewModel.type.value != null
            && viewModel.iconId.value != null


    companion object {
        private const val COLUMNS_COUNT = 6
    }
}