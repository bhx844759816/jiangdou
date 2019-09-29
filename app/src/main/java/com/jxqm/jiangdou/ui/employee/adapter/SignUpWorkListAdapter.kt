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
import com.jxqm.jiangdou.model.JobSignCloseModel
import com.jxqm.jiangdou.model.JobSignModel
import com.jxqm.jiangdou.model.JobSignModelBase

/**
 * 雇员-工作列表-已报名—适配器
 * Created by Administrator on 2019/8/31.
 */
class SignUpWorkListAdapter(context: Context) : MultiItemTypeAdapter<JobSignModelBase>(context) {

    init {
        addItemViewType(object : ItemViewType<JobSignModelBase> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employee_work_top_item
            override fun isItemClickable(): Boolean = false
            override fun isViewForType(item: JobSignModelBase?, position: Int): Boolean {
                return item?.jobStatus == 0
            }

            override fun convert(holder: ViewHolder?, t: JobSignModelBase?, position: Int) {
            }
        })

        addItemViewType(object : ItemViewType<JobSignModelBase> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employee_work_list

            override fun isItemClickable(): Boolean = true

            override fun isViewForType(item: JobSignModelBase?, position: Int): Boolean {
                return item?.jobStatus == 0
            }

            override fun convert(holder: ViewHolder?, item: JobSignModelBase, position: Int) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvCity = it.getView<TextView>(R.id.tvCity)
                    val tvArea = it.getView<TextView>(R.id.tvArea)
                    val tyEmployPeopleNum = it.getView<TextView>(R.id.tyEmployPeopleNum)
                    val tvJobMoney = it.getView<TextView>(R.id.tvJobMoney)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val tvSignUpPeopleCounts = it.getView<TextView>(R.id.tvSignUpPeopleCounts)
                    val jobSignModel = item as JobSignModel
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + jobSignModel.typeImgUrl).into(ivEmployeeImg)
                    tvEmployeeTitle.text = jobSignModel.title
                    tyEmployPeopleNum.text = jobSignModel.recruitNum
                    tvJobMoney.text = "${jobSignModel.salary} 币/时"

                }
            }
        })

        //截止报名的工作
        addItemViewType(object : ItemViewType<JobSignModelBase> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employee_work_end_sign_list

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: JobSignModelBase?, position: Int): Boolean = item?.jobStatus == 2

            override fun convert(holder: ViewHolder?, item: JobSignModelBase, position: Int) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvCity = it.getView<TextView>(R.id.tvCity)
                    val tvArea = it.getView<TextView>(R.id.tvArea)
                    val tyEmployPeopleNum = it.getView<TextView>(R.id.tyEmployPeopleNum)
                    val tvJobMoney = it.getView<TextView>(R.id.tvJobMoney)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val tvSignUpPeopleCounts = it.getView<TextView>(R.id.tvSignUpPeopleCounts)
                    val jobSignModel = item as JobSignCloseModel
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + jobSignModel.typeImgUrl).into(ivEmployeeImg)
                    tvEmployeeTitle.text = jobSignModel.title
                    tyEmployPeopleNum.text = jobSignModel.recruitNum
                    tvJobMoney.text = "${jobSignModel.salary} 币/时"
                }
            }

        })
    }
}