package com.jxqm.jiangdou.ui.job.adapter

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bhx.common.adapter.rv.LoadMoreAdapter
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.home.model.HomeJobDetailsModel
import com.jxqm.jiangdou.ui.job.view.JobDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger

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
            override fun convert(holder: ViewHolder?, model: JobDetailsModel, position: Int) {
                holder?.let {
                    it.getView<LinearLayout>(R.id.llParent).clickWithTrigger {
                        val intent = Intent(mContext, JobDetailsActivity::class.java)
                        intent.putExtra("JobId", model.id.toString())
                        intent.putExtra("Status", JobDetailsActivity.STATUS_SINGUP)
                        mContext.startActivity(intent)
                    }
                    val ivJobListImg = it.getView<ImageView>(R.id.ivJobListImg)
                    val tvJobTitle = it.getView<TextView>(R.id.tvJobTitle)
                    val tvJobCity = it.getView<TextView>(R.id.tvJobCity)
                    val tvJobArea = it.getView<TextView>(R.id.tvJobArea)
                    val tvJobNumbers = it.getView<TextView>(R.id.tvJobNumbers)
                    val tvJobSalary = it.getView<TextView>(R.id.tvJobSalary)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + model.typeImgUrl).into(ivJobListImg)
                    tvJobTitle.text = model.title
                    tvJobCity.text = model.city
                    tvJobArea.text = model.area
                    tvJobNumbers.text = "招${model.recruitNum}人"
                    tvJobSalary.text = "${model.salary}币/时"
                }
            }
        })
    }
}