package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployRecordSignUpItem

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
            }

        })
    }
}