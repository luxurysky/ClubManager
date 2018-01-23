package luxurysky.clubmanager.view

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import luxurysky.clubmanager.R

class AutoFitGridLayoutManager : GridLayoutManager {

    private var mColumnWidth = 0

    constructor(context: Context, columnWidth: Int) : super(context, 1) {
        setColumnWidth(checkedColumnWidth(context, columnWidth))
    }

    constructor(context: Context, columnWidth: Int, orientation: Int, reverseLayout: Boolean) : super(context, 1, orientation, reverseLayout) {
        setColumnWidth(checkedColumnWidth(context, columnWidth))
    }

    private fun checkedColumnWidth(context: Context, columnWidth: Int): Int {
        var newColumnWidth = columnWidth
        if (newColumnWidth <= 0) {
            newColumnWidth = context.resources.getDimension(R.dimen.default_auto_fit_grid_width).toInt()
        }
        return newColumnWidth
    }

    fun setColumnWidth(columnWidth: Int) {
        if (columnWidth > 0 && mColumnWidth != columnWidth) {
            mColumnWidth = columnWidth
        }
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (mColumnWidth > 0) {
            val totalSpace: Int = if (orientation == RecyclerView.VERTICAL) {
                width - paddingRight - paddingLeft
            } else {
                height - paddingTop - paddingBottom
            }
            val spanCount = Math.max(1, totalSpace / mColumnWidth)
            setSpanCount(spanCount)
        }
        super.onLayoutChildren(recycler, state)
    }
}