package com.jxqm.jiangdou.ui.job.adapter

import android.content.Context
import com.bhx.common.adapter.rv.LoadMoreAdapter
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.JobDetailsModel

/**
 * 搜索 - 兼职列表
 * Created by Administrator on 2019/8/17.
 */
class JobListAdapter(context: Context) : MultiItemTypeAdapter<JobDetailsModel>(context) {

    init {
        addItemViewType(object : ItemViewType<JobDetailsModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_job_list
            override fun isItemClickable(): Boolean = true
            override fun isViewForType(item: JobDetailsModel?, position: Int): Boolean = true
            override fun convert(holder: ViewHolder?, t: JobDetailsModel?, position: Int) {

            }
        })
    }
}