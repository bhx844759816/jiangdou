package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployRecordPayItem
import com.jxqm.jiangdou.ui.employee.view.ResumeDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * Created By bhx On 2019/9/3 0003 17:18
 */
class EmployRecordPayAdapter(context: Context) : MultiItemTypeAdapter<EmployRecordPayItem>(context) {
    init {
        addItemViewType(object : ItemViewType<EmployRecordPayItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_pay_finish_layout

            override fun isItemClickable(): Boolean = true

            override fun isViewForType(item: EmployRecordPayItem?, position: Int): Boolean = item?.type == 0

            override fun convert(holder: ViewHolder?, t: EmployRecordPayItem?, position: Int) {
                val ivHeadPhoto = holder?.getView<ImageView>(R.id.ivHeadPhoto)
                ivHeadPhoto?.clickWithTrigger {
                    mContext.startActivity<ResumeDetailsActivity>()
                }
            }

        })

        addItemViewType(object : ItemViewType<EmployRecordPayItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_pay_wait_layout

            override fun isItemClickable(): Boolean = true

            override fun isViewForType(item: EmployRecordPayItem?, position: Int): Boolean = item?.type == 1

            override fun convert(holder: ViewHolder?, t: EmployRecordPayItem?, position: Int) {
                val tvMoneyCenterLine = holder?.getView<TextView>(R.id.tvMoneyCenterLine)
                tvMoneyCenterLine?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
            }

        })

        addItemViewType(object : ItemViewType<EmployRecordPayItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_pay_refuse_layout

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: EmployRecordPayItem?, position: Int): Boolean = item?.type == 2

            override fun convert(holder: ViewHolder?, t: EmployRecordPayItem?, position: Int) {
                val tvMoneyCenterLine = holder?.getView<TextView>(R.id.tvMoneyCenterLine)
                tvMoneyCenterLine?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
            }

        })
    }
}