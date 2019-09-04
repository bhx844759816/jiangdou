package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.widget.ImageView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployRecordEmploymentItem
import com.jxqm.jiangdou.ui.employee.view.ResumeDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * 雇佣记录 - 已录用-适配器
 * Created By bhx On 2019/9/3 0003 13:50
 */
class EmployRecordEmploymentAdapter(context: Context) : MultiItemTypeAdapter<EmployRecordEmploymentItem>(context) {
    init {
        addItemViewType(object : ItemViewType<EmployRecordEmploymentItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_employment
            override fun isItemClickable(): Boolean = false
            override fun isViewForType(item: EmployRecordEmploymentItem?, position: Int): Boolean = true
            override fun convert(holder: ViewHolder?, t: EmployRecordEmploymentItem?, position: Int) {
                val ivHeadPhoto = holder?.getView<ImageView>(R.id.ivHeadPhoto)
                ivHeadPhoto?.clickWithTrigger {
                    mContext.startActivity<ResumeDetailsActivity>()
                }
            }

        })
    }
}