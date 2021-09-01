package com.example.tinkoffproject.ui.main.base_fragment

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.example.tinkoffproject.ui.main.NextCustomButton

interface WithNextButton {
    val btn: NextCustomButton
    val navController: NavController

    fun setupNextButton(
        onError: () -> Unit = {},
        onSuccess: () -> Unit = {},
        action: NavDirections? = null,
        @IdRes resId: Int? = null
    ) {
        btn.setOnClickListener {
            if (isNextAvailable()) {
                onSuccess()
                if (action != null)
                    navController.navigate(action)
                if (resId != null)
                    navController.navigate(resId)
            } else onError()
        }
    }

    fun isNextAvailable(): Boolean = true

    fun updateButtonState() {
        btn.changeState(if (isNextAvailable()) NextCustomButton.State.DEFAULT else NextCustomButton.State.DISABLED)
    }

    fun onLoading() {
        btn.changeState(NextCustomButton.State.LOADING)
    }
}