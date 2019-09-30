package com.jxqm.jiangdou.ui.employee.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.JobEmployeeModel
import com.jxqm.jiangdou.model.ReportDutyWorkItem

/**
 * 雇员 - 到岗 - 适配器
 * Created By bhx On 2019/9/2 0002 10:09
 */
class ReportDutyWorkListAdapter(context: Context) : MultiItemTypeAdapter<JobEmployeeModel>(context) {

    init {
        addItemViewType(object : ItemViewType<JobEmployeeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_report_duty_work_item

            override fun isItemClickable(): Boolean = true

            override fun isViewForType(item: JobEmployeeModel?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, item: JobEmployeeModel?, position: Int) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvCity = it.getView<TextView>(R.id.tvCity)
                    val tvArea = it.getView<TextView>(R.id.tvArea)
                    val tvRecruitNum = it.getView<TextView>(R.id.tvRecruitNum)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val tvJobMoney = it.getView<TextView>(R.id.tvJobMoney)
                    val model = item as JobEmployeeModel
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + model.typeImg).into(ivEmployeeImg)
                    tvEmployeeTitle.text = model.title
                    tvRecruitNum.text = model.recruitNum.toString()
                    tvSingUpTime.text = model.signTime
                    tvJobMoney.text = "${model.salary} 币/小时"
                }
            }
        })
    }
}