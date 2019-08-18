package com.jxqm.jiangdou.ui.job.adapter

import android.content.Context
import com.bhx.common.adapter.rv.LoadMoreAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R

/**
 * Created by Administrator on 2019/8/18.
 */
class CompanyListAdapter(context: Context) :LoadMoreAdapter<String>(context){

    init {
        addItemViewType(object : ItemViewType<String> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_company_list
            override fun isItemClickable(): Boolean = true
            override fun isViewForType(item: String?, position: Int): Boolean = position != datas.size
            override fun convert(holder: ViewHolder?, t: String?, position: Int) {
            }
        })
    }
}