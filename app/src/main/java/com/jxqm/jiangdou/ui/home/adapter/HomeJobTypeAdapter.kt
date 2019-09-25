package com.jxqm.jiangdou.ui.home.adapter

import android.content.Context
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bhx.common.utils.LogUtils
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.JobTypeModel

/**
 * Created By bhx On 2019/9/25 0025 08:36
 */
class HomeJobTypeAdapter(context: Context) : MultiItemTypeAdapter<JobTypeModel>(context) {

    init {
        addItemViewType(object : ItemViewType<JobTypeModel> {
            override fun isItemClickable(): Boolean = true

            override fun isViewForType(item: JobTypeModel?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, jobTypeModel: JobTypeModel, position: Int) {
                holder?.let {
                    LogUtils.i(Api.HTTP_BASE_URL + jobTypeModel.imgUrl)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + jobTypeModel.imgUrl)
                        .into(it.getView(R.id.ivJobTypeImage))
                    it.getView<TextView>(R.id.tvJobTypeTitle).text = jobTypeModel.jobTypeName
                }

            }

            override fun getItemViewLayoutId(): Int = R.layout.adapter_home_job_type_item
        })

    }
}