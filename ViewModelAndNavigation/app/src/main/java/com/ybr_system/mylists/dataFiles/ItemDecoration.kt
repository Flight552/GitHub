package com.ybr_system.mylists.dataFiles

import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(private val context: Context, private val dp: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val offset = dp.getPixels(context)
        with(outRect) {
            left = offset
            right = offset
            top = offset
            bottom = offset
        }
    }

    private fun Int.getPixels(context: Context): Int {
        val density = context.resources.displayMetrics.densityDpi
        return density / DisplayMetrics.DENSITY_DEFAULT
    }
}