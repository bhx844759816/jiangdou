package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.widget.ImageView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployRecordWaitPayItem
import com.jxqm.jiangdou.model.EmployeeResumeModel
import com.jxqm.jiangdou.ui.employee.view.ResumeDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * 待结算
 * Created By bhx On 2019/9/3 0003 18:11
 */
class EmployRecordWaitPayAdapter(context: Context) : MultiItemTypeAdapter<EmployeeResumeModel>(context) {
    init {
        addItemViewType(object : ItemViewType<EmployeeResumeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_wait_pay
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: EmployeeResumeModel?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, model: EmployeeResumeModel, position: Int) {
                val ivHeadPhoto = holder?.getView<ImageView>(R.id.ivHeadPhoto)
                ivHeadPhoto?.clickWithTrigger {
                    mContext.startActivity<ResumeDetailsActivity>()
                }
            }
        })

    }
}