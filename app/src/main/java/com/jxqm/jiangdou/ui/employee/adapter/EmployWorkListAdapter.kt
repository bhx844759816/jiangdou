package com.jxqm.jiangdou.ui.employee.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bhx.common.utils.LogUtils
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.*
import com.jxqm.jiangdou.ui.employee.view.EmployeeOfferDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * Created by Administrator on 2019/9/1.
 */
class EmployWorkListAdapter(context: Context) :
    MultiItemTypeAdapter<JobEmployeeBaseModel>(context) {
    var mAcceptOfferCallBack: ((Int) -> Unit)? = null
    var mRefuseOfferCallBack: ((Int) -> Unit)? = null
    var clearInvalidJobCallBack: (() -> Unit)? = null
    private val mGson: Gson by lazy {
        Gson()
    }

    init {
        //失效状态的title
        addItemViewType(object : ItemViewType<JobEmployeeBaseModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employee_work_top_item
            override fun isItemClickable(): Boolean = false
            override fun isViewForType(item: JobEmployeeBaseModel?, position: Int): Boolean {
                return item is JobEmployeeTitleModel
            }

            override fun convert(holder: ViewHolder?, t: JobEmployeeBaseModel?, position: Int) {
                holder?.let {
                    val title = it.getView<TextView>(R.id.tvTitle)
                    val tvOperation = it.getView<TextView>(R.id.tvOperation)
                    title.text = "失效"
                    tvOperation.clickWithTrigger {
                        clearInvalidJobCallBack?.invoke()
                    }
                }
            }


        })
        //正常状态的
        addItemViewType(object : ItemViewType<JobEmployeeBaseModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_work_list
            override fun isItemClickable(): Boolean = false
            override fun isViewForType(item: JobEmployeeBaseModel?, position: Int): Boolean {
                return item is JobEmployeeModel
            }

            override fun convert(holder: ViewHolder?, item: JobEmployeeBaseModel, position: Int) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvCity = it.getView<TextView>(R.id.tvCity)
                    val tvArea = it.getView<TextView>(R.id.tvArea)
                    val tvRecruitNum = it.getView<TextView>(R.id.tvRecruitNum)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val tvJobMoney = it.getView<TextView>(R.id.tvJobMoney)
                    val tvRefuse = it.getView<TextView>(R.id.tvRefuse)
                    val tvAccept = it.getView<TextView>(R.id.tvSingleSettle)
                    val model = item as JobEmployeeModel
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + model.typeImgUrl)
                        .into(ivEmployeeImg)
                    tvEmployeeTitle.text = model.title
                    tvCity.text = model.city
                    tvArea.text = model.area
                    tvRecruitNum.text = model.recruitNum.toString()
                    tvSingUpTime.text = model.signTime
                    tvJobMoney.text = "${model.salary} 币/小时"
                    if (model.offerCode == 2) {
                        //已接受
                        tvRefuse.visibility = View.GONE
                        tvJobMoney.visibility = View.GONE
                        tvAccept.text = "查看详情"
                    } else {
                        tvRefuse.visibility = View.VISIBLE
                        tvJobMoney.visibility = View.VISIBLE
                        tvAccept.text = "接受"
                    }
                    tvRefuse.clickWithTrigger {
                        mRefuseOfferCallBack?.invoke(model.jobResumeId)
                    }
                    tvAccept.clickWithTrigger {
                        if (model.offerCode == 2) {
                            mContext.startActivity<EmployeeOfferDetailsActivity>(
                                "JobEmployeeModel" to mGson.toJson(
                                    model
                                )
                            )
                        } else {
                            mAcceptOfferCallBack?.invoke(model.jobResumeId)
                        }
                    }
                }
            }

        })
        //失效状态
        addItemViewType(object : ItemViewType<JobEmployeeBaseModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_work_exception_list
            override fun isItemClickable(): Boolean = false
            override fun isViewForType(item: JobEmployeeBaseModel?, position: Int): Boolean {
                return item is JobEmployeeExceptionModel
            }

            override fun convert(holder: ViewHolder?, item: JobEmployeeBaseModel?, position: Int) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val ivWorkTip = it.getView<ImageView>(R.id.ivWorkTip)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvCity = it.getView<TextView>(R.id.tvCity)
                    val tvArea = it.getView<TextView>(R.id.tvArea)
                    val tvRecruitNum = it.getView<TextView>(R.id.tvRecruitNum)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val model = item as JobEmployeeExceptionModel
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + model.typeImgUrl)
                        .into(ivEmployeeImg)
                    tvEmployeeTitle.text = model.title
                    tvRecruitNum.text = model.recruitNum.toString()
                    tvSingUpTime.text = model.signTime
                    tvCity.text = model.city
                    tvArea.text = model.area
                    if (model.invalid) {//是否过期
                        ivWorkTip.setBackgroundResource(R.drawable.icon_timeout)
                    } else {
                        ivWorkTip.setBackgroundResource(R.drawable.icon_refuse)
                    }
                }
            }

        })
    }
}