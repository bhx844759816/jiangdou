package com.jxqm.jiangdou.ui.job.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.bhx.common.adapter.rv.CommonAdapter
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.CompanyDetailsModel

/**
 * 企业详情适配器
 * Created by Administrator on 2019/8/18.
 */
class CompanyDetailsAdapter constructor(context: Context) : MultiItemTypeAdapter<CompanyDetailsModel>(context) {

    init {
        addCompanyDetailsViewType()
        addCompanyPublishJobsViewType()
    }

    private fun addCompanyDetailsViewType() {
        addItemViewType(object : ItemViewType<CompanyDetailsModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_company_details_item

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: CompanyDetailsModel, position: Int): Boolean {
                return position == 0
            }

            override fun convert(holder: ViewHolder, t: CompanyDetailsModel, position: Int) {

            }
        })
    }

    private fun addCompanyPublishJobsViewType() {
        addItemViewType(object : ItemViewType<CompanyDetailsModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_job_list

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: CompanyDetailsModel, position: Int): Boolean {
                return position != 0
            }

            override fun convert(holder: ViewHolder, t: CompanyDetailsModel, position: Int) {

            }
        })
    }
}