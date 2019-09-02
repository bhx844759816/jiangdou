package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.WaitPublishJobItem

/**
 * 雇主 - 等待发布
 * Created by Administrator on 2019/9/2.
 */
class WaitPublishJobAdapter(context: Context) : MultiItemTypeAdapter<WaitPublishJobItem>(context) {

    init {
        addItemViewType(object : ItemViewType<WaitPublishJobItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_wait_publish_item
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: WaitPublishJobItem?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, t: WaitPublishJobItem?, position: Int) {
            }
        })
    }
}