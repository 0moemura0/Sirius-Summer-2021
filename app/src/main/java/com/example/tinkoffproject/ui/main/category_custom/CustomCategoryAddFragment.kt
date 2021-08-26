package com.example.tinkoffproject.ui.main.category_custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.R
import com.example.tinkoffproject.State
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.NotificationType
import com.example.tinkoffproject.ui.main.adapter.category_custom.CustomCategoryAdapter
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.carddetails.UpdatableToolBar
import com.example.tinkoffproject.ui.main.data.CategoryType
import com.example.tinkoffproject.ui.main.dialog.ChooseColorDialogFragment
import com.example.tinkoffproject.utils.COLOR
import com.example.tinkoffproject.viewmodel.CustomCategoryViewModel

class CustomCategoryAddFragment : Fragment(R.layout.fragment_categoty_add) {
    val viewModel: CustomCategoryViewModel by activityViewModels()
    private lateinit var btn: NextCustomButton

    private val args: CustomCategoryAddFragmentArgs by navArgs()
    private val customAdapter: CustomCategoryAdapter by lazy { CustomCategoryAdapter() }
    private var isNewOperation = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) isNewOperation =
            savedInstanceState.getBoolean(IS_NEW_OPERATION)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(IS_NEW_OPERATION, isNewOperation)
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn = view.findViewById(R.id.btn)
        setupData()
        setupNextButton()
        setupToolbar()
        setupRecycler()
        setupBtnObserver()

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

        if (args.isNewOperation && isNewOperation) {
            isNewOperation = false
            viewModel.type.value = if (args.isIncome) CategoryType.INCOME else CategoryType.EXPENSE
        }

        nameValue.text = viewModel.name.value ?: getString(R.string.dont_set)
        typeValue.setText(viewModel.type.value?.nameResId ?: R.string.dont_set)
        colorValue.setText(R.string.choose_color)

        colorValue.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                viewModel.color.value ?: COLOR.BLUE_MAIN.color
            )
        )

        dialog.setOnItemClickListener { color ->
            val realColor = ContextCompat.getColor(requireContext(), color.color)
            viewModel.color.value = realColor
            colorValue.setTextColor(realColor)
            customAdapter.setCurrentColor(color)
            dialog.dismiss()
        }

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

    private fun setupBtnObserver() {
        viewModel.color.observe(viewLifecycleOwner, {
            updateButtonState()
        })
        viewModel.name.observe(viewLifecycleOwner, {
            updateButtonState()
        })
        viewModel.type.observe(viewLifecycleOwner, {
            updateButtonState()
        })
        viewModel.iconId.observe(viewLifecycleOwner, {
            updateButtonState()
        })
    }

    private fun updateButtonState() {
        btn.changeState(if (isNextAvailable()) NextCustomButton.State.DEFAULT else NextCustomButton.State.DISABLED)
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

        customAdapter.setOnItemClickListener { position ->
            viewModel.iconId.value =
                if (customAdapter.data[position].isChecked) viewModel.icons[position] else null
        }
        viewModel.iconId.value?.let {
            val pos = viewModel.icons.indexOf(it)
            customAdapter.onItemSelect(pos, false)
        }
    }


    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.category_new), ToolbarType.ADD_OPERATION)
    }

    private fun setupNextButton() {
        btn.setOnClickListener {
            if (isNextAvailable()) {
                viewModel.addCategory().observe(viewLifecycleOwner, {
                    when (it) {
                        is State.DataState -> {
                            btn.changeState(NextCustomButton.State.DEFAULT)
                            findNavController().navigate(R.id.action_to_chooseTransactionCategory)
                        }
                        is State.LoadingState -> {
                            btn.changeState(NextCustomButton.State.LOADING)
                        }
                        is State.ErrorState -> {
                            btn.changeState(NextCustomButton.State.DEFAULT)

                            val notType =
                                if (it.exception is IllegalAccessError) NotificationType.INTERNET_PROBLEM_ERROR else NotificationType.UNKNOWN_ERROR
                            (requireActivity() as MainActivity).showNotification(notType)
                        }
                    }
                })

            } else {
                Toast.makeText(context, getString(R.string.enter_value), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isNextAvailable() = viewModel.color.value != null
            && viewModel.name.value != null
            && viewModel.type.value != null
            && viewModel.iconId.value != null


    companion object {
        const val IS_NEW_OPERATION = "IS_NEW_OPERATION"
        private const val COLUMNS_COUNT = 6
    }
}