package com.jxqm.jiangdou.ui.employee.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.*

/**
 * 雇员-工作列表-已报名—适配器
 * Created by Administrator on 2019/8/31.
 */
class SignUpWorkListAdapter(context: Context) : MultiItemTypeAdapter<JobEmployeeBaseModel>(context) {

    init {
        addItemViewType(object : ItemViewType<JobEmployeeBaseModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employee_work_top_item
            override fun isItemClickable(): Boolean = false
            override fun isViewForType(item: JobEmployeeBaseModel?, position: Int): Boolean {
                return item is JobEmployeeTitleModel
            }

            override fun convert(holder: ViewHolder?, t: JobEmployeeBaseModel?, position: Int) {
            }
        })

        addItemViewType(object : ItemViewType<JobEmployeeBaseModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employee_work_list

            override fun isItemClickable(): Boolean = true

            override fun isViewForType(item: JobEmployeeBaseModel?, position: Int): Boolean {
                return item is JobEmployeeModel
            }

            override fun convert(holder: ViewHolder?, item: JobEmployeeBaseModel, position: Int) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvCity = it.getView<TextView>(R.id.tvCity)
                    val tvArea = it.getView<TextView>(R.id.tvArea)
                    val tyEmployPeopleNum = it.getView<TextView>(R.id.tyEmployPeopleNum)
                    val tvJobMoney = it.getView<TextView>(R.id.tvJobMoney)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val tvSignUpPeopleCounts = it.getView<TextView>(R.id.tvSignUpPeopleCounts)
                    val jobSignModel = item as JobEmployeeModel
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + jobSignModel.typeImgUrl).into(ivEmployeeImg)
                    tvEmployeeTitle.text = jobSignModel.title
                    tyEmployPeopleNum.text = jobSignModel.recruitNum.toString()
                    tvJobMoney.text = "${jobSignModel.salary} 币/时"
                    tvSingUpTime.text = jobSignModel.signTime
                    tvCity.text = jobSignModel.city
                    tvArea.text = jobSignModel.area
                    tvSignUpPeopleCounts.text = "已报名: ${jobSignModel.signNum}人"
                }
            }
        })

        //截止报名的工作
        addItemViewType(object : ItemViewType<JobEmployeeBaseModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employee_work_end_sign_list

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: JobEmployeeBaseModel?, position: Int): Boolean = item is JobEmployeeExceptionModel

            override fun convert(holder: ViewHolder?, item: JobEmployeeBaseModel, position: Int) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvCity = it.getView<TextView>(R.id.tvCity)
                    val tvArea = it.getView<TextView>(R.id.tvArea)
                    val tyEmployPeopleNum = it.getView<TextView>(R.id.tyEmployPeopleNum)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val jobSignModel = item as JobEmployeeExceptionModel
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + jobSignModel.typeImgUrl).into(ivEmployeeImg)
                    tvEmployeeTitle.text = jobSignModel.title
                    tvCity.text = jobSignModel.city
                    tvArea.text = jobSignModel.area
                    tyEmployPeopleNum.text = jobSignModel.recruitNum.toString()
                    tvSingUpTime.text = jobSignModel.signTime
                }
            }

        })
    }
}