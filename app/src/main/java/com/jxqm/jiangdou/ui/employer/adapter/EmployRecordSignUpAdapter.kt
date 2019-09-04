package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.widget.ImageView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployRecordSignUpItem
import com.jxqm.jiangdou.ui.employee.view.ResumeDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * 雇佣记录 - 报名
 * Created By bhx On 2019/9/3 0003 10:03
 */
class EmployRecordSignUpAdapter(context: Context) : MultiItemTypeAdapter<EmployRecordSignUpItem>(context) {

    init {
        addItemViewType(object : ItemViewType<EmployRecordSignUpItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_sign_up_item

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: EmployRecordSignUpItem?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, t: EmployRecordSignUpItem?, position: Int) {
                val ivHeadPhoto = holder?.getView<ImageView>(R.id.ivHeadPhoto)
                ivHeadPhoto?.clickWithTrigger {
                    mContext.startActivity<ResumeDetailsActivity>()
                }
            }

        })
    }
}