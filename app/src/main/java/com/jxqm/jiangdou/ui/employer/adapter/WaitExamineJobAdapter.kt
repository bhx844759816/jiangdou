package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.WaitExamineJobItem
import com.jxqm.jiangdou.model.WaitPublishJobItem

/**
 * Created by Administrator on 2019/9/2.
 */
class WaitExamineJobAdapter(context: Context) : MultiItemTypeAdapter<WaitExamineJobItem>(context) {

    init {
        addItemViewType(object : ItemViewType<WaitExamineJobItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_wait_examine_item
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: WaitExamineJobItem?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, t: WaitExamineJobItem?, position: Int) {
            }
        })
    }

}