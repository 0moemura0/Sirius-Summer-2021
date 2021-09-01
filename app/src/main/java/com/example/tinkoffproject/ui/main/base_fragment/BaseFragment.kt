package com.example.tinkoffproject.ui.main.base_fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.MainActivity
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.NotificationType
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.ui.main.carddetails.UpdatableToolBar

abstract class BaseFragment(
    @LayoutRes res: Int,
    @StringRes private val toolbarRes: Int = R.string.empty,
    private val toolbarType: ToolbarType = ToolbarType.DEFAULT,
) : Fragment(res), WithNextButton {
    override lateinit var btn: NextCustomButton
    override val navController: NavController
        get() = findNavController()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn = view.findViewById(R.id.btn)
        setupNextButtonImpl()
        setupToolbar(toolbarRes, toolbarType)
    }

    private fun setupToolbar(@StringRes res: Int, type: ToolbarType) {
        val update: UpdatableToolBar = (activity as MainActivity)
        update.updateToolbar(getString(res), type)
    }

    abstract fun setupNextButtonImpl()

    open fun initViews() {}

    open fun onInternetError(e: Throwable?) {
        updateButtonState()

        val notType =
            if (e is IllegalAccessError) NotificationType.INTERNET_PROBLEM_ERROR else NotificationType.UNKNOWN_ERROR
        (requireActivity() as MainActivity).showNotification(notType)
    }

    fun setupNextButton(
        isDefaultErrorMessage: Boolean = false,
        onError: () -> Unit = {},
        onSuccess: () -> Unit = {},
        action: NavDirections? = null,
        @IdRes resId: Int? = null
    ) {
        var myOnError: () -> Unit = onError
        if (isDefaultErrorMessage) {
            myOnError = {
                onError()
                Toast.makeText(
                    context,
                    requireContext().getString(R.string.enter_value),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        super.setupNextButton(myOnError, onSuccess, action, resId)
    }
}