package edu.stanford.kassimo.mymaps

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class SwipeDeleteCallback(val context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return super.getMovementFlags(recyclerView, viewHolder)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val canceled = dX == 0f && !isCurrentlyActive
        if (canceled) {
            // Clean background drawings.
            val clearPaint = Paint()
            clearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            c?.drawRect(
                viewHolder.itemView.right + dX,
                viewHolder.itemView.top.toFloat(),
                viewHolder.itemView.right.toFloat(),
                viewHolder.itemView.bottom.toFloat(),
                clearPaint
            )
        } else {
            // val rightMargin = (viewHolder.itemView.width * 0.01).toInt()
            val topBottomMargin = (viewHolder.itemView.height * 0.1).toInt()
            val iconWidth = (viewHolder.itemView.width * 0.1).toInt()

            // Draw red background
            val background = ColorDrawable()
            background.color = Color.RED
            background.setBounds(
                viewHolder.itemView.right + dX.toInt(),
                viewHolder.itemView.top,
                viewHolder.itemView.right,
                viewHolder.itemView.bottom
            )
            background.draw(c)

            // Draw delete icon
            val icon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_delete)
            icon?.setTint(Color.WHITE)
            icon?.alpha = 255
            icon?.setBounds(
                viewHolder.itemView.right - iconWidth,
                viewHolder.itemView.top + topBottomMargin,
                viewHolder.itemView.right,
                viewHolder.itemView.bottom - topBottomMargin
            )
            icon?.draw(c)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}