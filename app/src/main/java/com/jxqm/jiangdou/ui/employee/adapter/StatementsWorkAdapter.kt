package com.jxqm.jiangdou.ui.employee.adapter

import android.content.Context
import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.JobEmployeeModel
import com.jxqm.jiangdou.utils.clickWithTrigger

/**
 *
 * Created By bhx On 2019/9/2 0002 17:53
 */
class StatementsWorkAdapter(context: Context) : MultiItemTypeAdapter<JobEmployeeModel>(context) {
    var mRefuseCallBack: ((String) -> Unit)? = null //拒绝结算
    var mAcceptCallBack: ((String) -> Unit)? = null //接受结算

    init {
        //待结算
        addItemViewType(object : ItemViewType<JobEmployeeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_statements_work_item
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: JobEmployeeModel?, position: Int): Boolean =
                item?.settleCode == 0 ||
                        item?.settleCode == 2

            override fun convert(holder: ViewHolder?, item: JobEmployeeModel, position: Int) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvCity = it.getView<TextView>(R.id.tvCity)
                    val tvArea = it.getView<TextView>(R.id.tvArea)
                    val tvAccept = it.getView<TextView>(R.id.tvAccept)
                    val tvRefuse = it.getView<TextView>(R.id.tvRefuse)
                    val tvAmountSettle = it.getView<TextView>(R.id.tvAmountSettle)
                    val tvAmount = it.getView<TextView>(R.id.tvAmount)
                    val tvRecruitNum = it.getView<TextView>(R.id.tvRecruitNum)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + item.typeImg).into(ivEmployeeImg)
                    tvEmployeeTitle.text = item.title
                    tvCity.text = item.city
                    tvAmount.text = "${item.amount}币"
                    tvAmountSettle.text = "${item.settleAmount}币"
                    tvArea.text = item.area
                    tvRecruitNum.text = "${item.recruitNum}人"
                    tvAmount.paintFlags = tvAmount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvAccept.clickWithTrigger {
                        mAcceptCallBack?.invoke(item.jobWorkId.toString())
                    }
                    tvRefuse.clickWithTrigger {
                        mRefuseCallBack?.invoke(item.jobWorkId.toString())
                    }
                }
            }

        })
        //拒绝结算
        addItemViewType(object : ItemViewType<JobEmployeeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_statements_work_refuse_item
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: JobEmployeeModel?, position: Int): Boolean = item?.settleCode == 3
            override fun convert(holder: ViewHolder?, item: JobEmployeeModel, position: Int) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvCity = it.getView<TextView>(R.id.tvCity)
                    val tvArea = it.getView<TextView>(R.id.tvArea)
                    val tvRecruitNum = it.getView<TextView>(R.id.tvRecruitNum)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + item.typeImg).into(ivEmployeeImg)
                    tvEmployeeTitle.text = item.title
                    tvRecruitNum.text = "${item.recruitNum}人"
                    tvCity.text = item.city
                    tvArea.text = item.area
                }
            }

        })
        //已结算
        addItemViewType(object : ItemViewType<JobEmployeeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_statements_work_finish_item
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: JobEmployeeModel?, position: Int): Boolean = item?.settleCode == 1
            override fun convert(holder: ViewHolder?, item: JobEmployeeModel, position: Int) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvCity = it.getView<TextView>(R.id.tvCity)
                    val tvArea = it.getView<TextView>(R.id.tvArea)
                    val tvRecruitNum = it.getView<TextView>(R.id.tvRecruitNum)
                    val tvAmount = it.getView<TextView>(R.id.tvAmount)
                    val tvAmountSettle = it.getView<TextView>(R.id.tvAmountSettle)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + item.typeImg).into(ivEmployeeImg)
                    tvEmployeeTitle.text = item.title
                    tvRecruitNum.text = "${item.recruitNum}人"
                    tvCity.text = item.city
                    tvArea.text = item.area
                    tvAmount.text = "${item.amount}币"
                    tvAmountSettle.text = "${item.settleAmount}币"
                    tvAmount.paintFlags = tvAmount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            }

        })
    }
}