package com.example.tinkoffproject.ui.main.base_fragment.edit

import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.example.tinkoffproject.utils.formatMoney
import java.math.BigDecimal

abstract class EditMoneyFragment(
    @LayoutRes res: Int,
    @StringRes private val toolbarRes: Int = R.string.enter_count,
    toolbarType: ToolbarType = ToolbarType.ADD_OPERATION
) : EditTextFragment(res, toolbarRes, toolbarType) {
    override fun setupInputText() {
        updateButtonState()

        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                updateButtonState()
                if (!p0.isNullOrBlank()) {
                    inputEditText.removeTextChangedListener(this)
                    inputEditText.setText(formatMoney(p0))
                    inputEditText.setSelection(inputEditText.length())
                    inputEditText.addTextChangedListener(this)
                    hideError()
                }
            }
        })
    }

    override fun isNextAvailable(): Boolean {
        val value = getValueAsBigDecimal()
        return if (value == null) false
        else value > BigDecimal.ZERO
    }

    fun getValueAsBigDecimal(): BigDecimal? {
        val str: String? = getValue()
        if (str.isNullOrBlank()) return null
        return try {
            BigDecimal(str.replace(" ", ""))
        } catch (e: NumberFormatException) {
            null
        }
    }

    fun getValueAsInt(): Int {
        return try {
            getValueAsBigDecimal()!!.intValueExact()
        } catch (e: NumberFormatException) {
            Int.MAX_VALUE
        } catch (e: NullPointerException) {
            0
        }
    }

}