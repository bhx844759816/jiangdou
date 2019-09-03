package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.AdvertiseJobItem
import com.jxqm.jiangdou.ui.employer.view.EmployRecordActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * 雇主- 正在招聘
 * Created by Administrator on 2019/9/2.
 */
class AdvertiseJobAdapter(context: Context) : MultiItemTypeAdapter<AdvertiseJobItem>(context) {
    init {
        addItemViewType(object : ItemViewType<AdvertiseJobItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_advertise_job_item
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: AdvertiseJobItem?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, t: AdvertiseJobItem?, position: Int) {
                val tvAccept = holder?.getView<TextView>(R.id.tvAccept)
                tvAccept?.clickWithTrigger {
                    mContext.startActivity<EmployRecordActivity>()
                }
            }

        })
    }
}