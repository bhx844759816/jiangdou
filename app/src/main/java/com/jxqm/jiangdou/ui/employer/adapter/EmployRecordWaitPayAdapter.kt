package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployRecordWaitPayItem

/**
 * 待结算
 * Created By bhx On 2019/9/3 0003 18:11
 */
class EmployRecordWaitPayAdapter(context: Context) : MultiItemTypeAdapter<EmployRecordWaitPayItem>(context) {
    init {
        addItemViewType(object : ItemViewType<EmployRecordWaitPayItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_wait_pay
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: EmployRecordWaitPayItem?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, t: EmployRecordWaitPayItem?, position: Int) {
            }
        })

    }
}