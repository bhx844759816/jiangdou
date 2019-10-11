package com.jxqm.jiangdou.ui.job.adapter

import android.content.Context
import com.bhx.common.adapter.rv.LoadMoreAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.AttestationStatusModel

/**
 * 搜素公司列表
 * Created by Administrator on 2019/8/18.
 */
class CompanyListAdapter(context: Context) : LoadMoreAdapter<AttestationStatusModel>(context) {

    init {
        addItemViewType(object : ItemViewType<AttestationStatusModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_company_list
            override fun isItemClickable(): Boolean = true
            override fun isViewForType(item: AttestationStatusModel?, position: Int): Boolean = true
            override fun convert(holder: ViewHolder?, t: AttestationStatusModel?, position: Int) {
            }
        })
    }
}