package com.jxqm.jiangdou.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * grid设置Item的间隔
 * Created By bhx On 2019/7/24 0024 19:45
 */
class GridItemSpaceDecoration internal constructor(private val space: Int) : RecyclerView.ItemDecoration() {

    /**
     * @param outRect 边界
     * @param view    recyclerView ItemView
     * @param parent  recyclerView
     * @param state   recycler 内部数据管理
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        //当前位置
        val position = parent.getChildAdapterPosition(view)
        val layoutManager = parent.layoutManager
        //列数
        val spanCount = (layoutManager as GridLayoutManager).spanCount
        //总数
        val itemCount = parent.adapter!!.itemCount

        if (!isLastLine(position, spanCount, itemCount)) {
            outRect.set(0, 0, 0, space)
        }
    }

    /**
     * 是否是最后一行
     *
     * @param position  位置
     * @param spanCount 列数
     * @param itemCount 总数
     */
    private fun isLastLine(position: Int, spanCount: Int, itemCount: Int): Boolean {
        val maxLine: Int
        if (itemCount % spanCount == 0) {
            maxLine = itemCount / spanCount
        } else {
            maxLine = itemCount / spanCount + 1
        }

        val posLine: Int
        if ((position + 1) % spanCount == 0) {
            posLine = (position + 1) / spanCount
        } else {
            posLine = (position + 1) / spanCount + 1
        }

        return posLine == maxLine

    }
}
