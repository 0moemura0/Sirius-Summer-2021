package com.example.tinkoffproject.view.operation_add.category_add

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.view.carddetails.MainActivity
import com.example.tinkoffproject.view.carddetails.ToolbarType
import com.example.tinkoffproject.view.carddetails.UpdatableToolBar
import com.example.tinkoffproject.viewmodel.AddCategoryViewModel

//TODO layout
class NewCategoryFragment : Fragment() {
    private val viewModel: AddCategoryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupNavigation()
    }

    private fun setupNavigation() {
        requireView().findViewById<TextView>(R.id.btn).setOnClickListener(::createNewCategory)
        /*requireView().findViewById<LinearLayout>(R.id.ll_name_container)
            .setOnClickListener(::toSetNameFragment)*/
        requireView().findViewById<LinearLayout>(R.id.ll_type_container)
            .setOnClickListener(::toChooseTypeFragment)
    }

    private fun createNewCategory(TextView: View) {
        //TODO
    }

    private fun toSetNameFragment(LinearLayout: View) {
        findNavController().navigate(R.id.action_newCategoryFragment_to_setCategoryNameFragment)
    }

    private fun toChooseTypeFragment(LinearLayout: View) {
        findNavController().navigate(R.id.action_newCategoryFragment_to_chooseNewCategoryTypeFragment)
    }

    private fun setupToolbar() {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(R.string.operation_new), ToolbarType.ADD_OPERATION)
    }
}