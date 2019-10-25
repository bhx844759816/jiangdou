package com.jxqm.jiangdou.adapter

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.job.view.JobDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger

/**
 *
 * Created by Administrator on 2019/10/10.
 */
class JobItemAdapter(context: Context) : MultiItemTypeAdapter<JobDetailsModel>(context) {

    init {
        //添加Item的布局
        addItemViewType(object : ItemViewType<JobDetailsModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_home_item
            override fun isItemClickable(): Boolean = true
            override fun isViewForType(item: JobDetailsModel, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, homeModel: JobDetailsModel, position: Int) {
                holder?.let {
                    val ivJobListImg = holder.getView<ImageView>(R.id.ivJobListImg)
                    val llParent = holder.getView<LinearLayout>(R.id.llParent)
                    val tvJobTitle = holder.getView<TextView>(R.id.tvJobTitle)
                    val tvJobCity = holder.getView<TextView>(R.id.tvJobCity)
                    val tvJobArea = holder.getView<TextView>(R.id.tvJobArea)
                    val tvJobNumbers = holder.getView<TextView>(R.id.tvJobNumbers)
                    val tvJobSalary = holder.getView<TextView>(R.id.tvJobSalary)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + homeModel.typeImgUrl)
                        .into(ivJobListImg)
                    tvJobTitle.text = homeModel.title
                    tvJobCity.text = homeModel.city
                    tvJobArea.text = homeModel.area
                    tvJobNumbers.text = "招${homeModel.recruitNum}人"
                    tvJobSalary.text = "${homeModel.salary}币/时"
                    llParent.clickWithTrigger {
                        val intent = Intent(mContext, JobDetailsActivity::class.java)
                        intent.putExtra("JobId", homeModel.id.toString())
                        intent.putExtra("Status", JobDetailsActivity.STATUS_SINGUP)
                        mContext.startActivity(intent)
                    }
                }
            }
        })

    }
}