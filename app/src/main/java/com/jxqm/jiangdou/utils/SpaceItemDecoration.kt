package com.jxqm.jiangdou.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created By bhx On 2019/9/4 0004 13:56
 */
class SpaceItemDecoration internal constructor(private val space: Int) : RecyclerView.ItemDecoration() {

    /**
     * @param outRect 边界
     * @param view    recyclerView ItemView
     * @param parent  recyclerView
     * @param state   recycler 内部数据管理
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = space
    }
}