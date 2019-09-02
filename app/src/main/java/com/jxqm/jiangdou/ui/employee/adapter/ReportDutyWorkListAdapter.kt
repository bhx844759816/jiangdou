package com.jxqm.jiangdou.ui.employee.adapter

import android.content.Context
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.ReportDutyWorkItem

/**
 * Created By bhx On 2019/9/2 0002 10:09
 */
class ReportDutyWorkListAdapter(context: Context) : MultiItemTypeAdapter<ReportDutyWorkItem>(context) {

    init {
        addItemViewType(object : ItemViewType<ReportDutyWorkItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_report_duty_work_item

            override fun isItemClickable(): Boolean = true

            override fun isViewForType(item: ReportDutyWorkItem?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, t: ReportDutyWorkItem?, position: Int) {

            }
        })
    }
}