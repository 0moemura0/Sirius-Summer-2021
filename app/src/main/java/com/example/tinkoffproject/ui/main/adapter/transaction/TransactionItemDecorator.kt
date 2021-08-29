package com.example.tinkoffproject.ui.main.adapter.transaction

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.view.View
import androidx.annotation.Px
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffproject.*
import com.example.tinkoffproject.data.dto.to_view.Transaction
import java.util.*


class TransactionItemDecorator(
    @Px private val offsetBetween: Int,
    @Px private val marginTop: Int = offsetBetween,
    @Px private val marginBottom: Int = offsetBetween,
    @Px private val marginHorizontal: Int = offsetBetween,
) : RecyclerView.ItemDecoration() {

    private val paintItemHeader: Paint by lazy {
        Paint()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        val adapter = (parent.adapter as TransactionAdapter)
        val data = adapter.data
        val viewType = adapter.getItemViewType(position)

        if (isNotValidTransaction(position, viewType)) return

        val lineHeight = getLineHeightHeader(view.context)

        val isFirstInDay =
            viewType == TransactionAdapter.TYPE_TRANSACTION && isItemAtPositionFirstInDay(
                position,
                data
            )
        val isFirst = position == 0
        val isLast = position == itemCount - 1
        when {
            isLast -> {
                outRect.top = offsetBetween / 2
                outRect.bottom = marginBottom
            }
            isFirst -> {
                outRect.top = marginTop
                outRect.bottom = offsetBetween / 2
            }
            else -> {
                outRect.top = offsetBetween / 2
                outRect.bottom = offsetBetween / 2
            }
        }

        if (isFirstInDay) outRect.top = offsetBetween + (offsetBetween * 2 + lineHeight)
        outRect.left = marginHorizontal
        outRect.right = marginHorizontal
    }

    private fun isItemAtPositionFirstInDay(position: Int, data: List<Transaction>): Boolean {
        val transactionDate = data[position].ts
        val prevTransactionDate = data.getOrNull(position - 1)?.ts
        return prevTransactionDate == null || !isOneDay(prevTransactionDate, transactionDate)
    }

    private fun getLineHeightHeader(context: Context) =
        context.resources.getDimension(R.dimen.text_line_height_normal).toInt()

    private fun isNotValidTransaction(position: Int, viewType: Int) =
        position == RecyclerView.NO_POSITION || viewType == TransactionAdapter.TYPE_NO_TRANSACTION

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        val adapter = (parent.adapter as TransactionAdapter)
        val data = adapter.data

        paintItemHeader.apply {
            textSize = parent.context.resources.getDimension(R.dimen.view_text_normal)
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        parent.children.iterator().forEach { view ->
            val position = parent.getChildAdapterPosition(view)
            val viewType = adapter.getItemViewType(position)

            if (isNotValidTransaction(position, viewType)) return@forEach
            if (viewType != TransactionAdapter.TYPE_TRANSACTION) return@forEach

            if (isItemAtPositionFirstInDay(position, data)) {
                val itemDate = data[position].ts

                val text: String = when {
                    isToday(itemDate) -> parent.context.getString(R.string.today)
                    isYesterday(itemDate) -> parent.context.getString(R.string.yesterday)
                    else -> formatDate(parent.context, itemDate, R.string.date_format_month_full)
                }

                val bounds = Rect()
                parent.getDecoratedBoundsWithMargins(view, bounds)

                val lineHeight = getLineHeightHeader(view.context).toFloat()

                val x = bounds.left.toFloat() + marginHorizontal
                val y = (bounds.top + offsetBetween + lineHeight)
                c.drawText(text, x, y, paintItemHeader)
            }
        }
        super.onDraw(c, parent, state)
    }

}