package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.WaitPublishJobItem
import com.jxqm.jiangdou.ui.order.view.OrderPaymentActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

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
                val tvAccept = holder?.getView<TextView>(R.id.tvAccept)
                tvAccept?.clickWithTrigger {
                    mContext.startActivity<OrderPaymentActivity>()
                }
            }
        })
    }
}