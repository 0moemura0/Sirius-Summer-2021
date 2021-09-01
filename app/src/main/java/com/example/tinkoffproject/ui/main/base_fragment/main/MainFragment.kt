package com.example.tinkoffproject.ui.main.base_fragment.main

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.example.tinkoffproject.R
import com.example.tinkoffproject.ui.main.NextCustomButton
import com.example.tinkoffproject.ui.main.base_fragment.BaseFragment
import com.example.tinkoffproject.ui.main.carddetails.ToolbarType
import com.facebook.shimmer.ShimmerFrameLayout

abstract class MainFragment(
    @LayoutRes res: Int,
    @StringRes private val toolbarRes: Int = R.string.empty,
    toolbarType: ToolbarType = ToolbarType.DEFAULT
) : BaseFragment(res, toolbarRes, toolbarType) {
    lateinit var mShimmerViewContainer: ShimmerFrameLayout
    lateinit var container: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mShimmerViewContainer = requireView().findViewById(R.id.shimmer_container)
        container = requireView().findViewById(R.id.container)
    }

    fun onShimmerLoading() {
        super.onLoading()
        container.visibility = View.INVISIBLE
        mShimmerViewContainer.startShimmer()
        mShimmerViewContainer.visibility = View.VISIBLE
    }

    fun stopShimmerLoading() {
        container.visibility = View.VISIBLE
        mShimmerViewContainer.stopShimmer()
        mShimmerViewContainer.visibility = View.GONE
    }


    fun onInternetError(e: Throwable?, stopShimmer: Boolean = true) {
        super.onInternetError(e)
        if (stopShimmer)
            stopShimmerLoading()
    }

    override fun updateButtonState() {
        btn.changeState(NextCustomButton.State.DEFAULT)
    }


}