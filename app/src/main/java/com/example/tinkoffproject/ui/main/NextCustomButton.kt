package com.example.tinkoffproject.ui.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.TextViewCompat
import android.view.animation.Animation

import android.view.animation.LinearInterpolator

import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.example.tinkoffproject.R


class NextCustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private val title: TextView by lazy {
        findViewById(R.id.tv_title)
    }
    private val progressNew: ImageView by lazy {
        findViewById(R.id.iv_progress)
    }

    private var state: State = State.DEFAULT
    private var titleText: String = ""

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.view_next_button, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.NextCustomButton)
        try {
            val text = ta.getString(R.styleable.NextCustomButton_text)
            title.text = text

            changeState(_title = text)
        } finally {
            ta.recycle()
        }

        val anim = RotateAnimation(
            360F,
            0F,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.interpolator = LinearInterpolator()
        anim.repeatCount = Animation.INFINITE
        anim.duration = 400
        progressNew.startAnimation(anim)
    }

    /*Изменяет внешний вид по сотоянию*/
    fun changeState(_state: State = State.DEFAULT, _title: String?) {
        state = _state
        titleText = _title ?: ""
        setTitle()
        setState()
    }

    fun changeState(_state: State = State.DEFAULT) {
        state = _state
        setState()
    }

    fun setTitle(titleStr: String = titleText) {
        this.title.text = titleStr
    }

    fun setTitle(titleResId: Int) {
        this.title.text = context.getString(titleResId)
    }

    private fun setState() {
        setBackgroundResource(state.bgResource)
        isEnabled = state.isEnabled
        isClickable = state.isClickable
        progressNew.visibility = if (state.isProgressVisible) View.VISIBLE else View.GONE
        title.visibility = if (state.isTitleVisible) View.VISIBLE else View.GONE
        TextViewCompat.setTextAppearance(title, state.titleStyle)
    }

    enum class State(
        val bgResource: Int = R.drawable.rect_gray_8dp,
        val isEnabled: Boolean = true,
        val isClickable: Boolean = true,
        val isProgressVisible: Boolean = false,
        val isTitleVisible: Boolean = true,
        val titleStyle: Int = R.style.NextButtonText,
    ) {
        DEFAULT,
        DISABLED(
            R.drawable.rect_white_stroke_8dp,
            false,
            false,
            false,
            true,
            R.style.NextButtonText_Disable
        ),
        LOADING(
            isProgressVisible = true,
            isTitleVisible = false
        ),
        PRESTATE(
            R.drawable.rect_black_8dp,
            isEnabled = false,
            isClickable = false
        )

    }
}
