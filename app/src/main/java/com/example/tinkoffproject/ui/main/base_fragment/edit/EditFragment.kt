package com.example.tinkoffproject.ui.main.base_fragment.edit

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.base_fragment.BaseFragment
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType

abstract class EditFragment(
    @LayoutRes res: Int,
    @StringRes private val toolbarRes: Int = R.string.empty,
    toolbarType: ToolbarType = ToolbarType.ADD_OPERATION
) : BaseFragment(res, toolbarRes, toolbarType){
    abstract fun getValue(): Any?
}