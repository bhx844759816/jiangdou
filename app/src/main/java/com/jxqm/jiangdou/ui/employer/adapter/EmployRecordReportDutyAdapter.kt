package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.widget.ImageView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployRecordReportDutyItem
import com.jxqm.jiangdou.ui.employee.view.ResumeDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * 雇佣记录 - 到岗 -
 * Created By bhx On 2019/9/3 0003 16:24
 */
class EmployRecordReportDutyAdapter(context: Context) : MultiItemTypeAdapter<EmployRecordReportDutyItem>(context) {
    init {
        addItemViewType(object : ItemViewType<EmployRecordReportDutyItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_report_duty

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: EmployRecordReportDutyItem?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, t: EmployRecordReportDutyItem?, position: Int) {
                val ivHeadPhoto = holder?.getView<ImageView>(R.id.ivHeadPhoto)
                ivHeadPhoto?.clickWithTrigger {
                    mContext.startActivity<ResumeDetailsActivity>()
                }
            }

        })

    }
}