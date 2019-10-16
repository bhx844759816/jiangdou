package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.ui.employer.view.EmployRecordActivity
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.order.view.OrderDetailsActivity
import com.jxqm.jiangdou.ui.publish.view.JobPublishActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * Created by Administrator on 2019/9/22.
 */
class JobPublishListAdapter(context: Context, type: Int) :
    MultiItemTypeAdapter<JobDetailsModel>(context) {
    var paymentCallBack: ((String) -> Unit)? = null
    var cancelPublish: ((String) -> Unit)? = null
    var contentClickCallBack: ((JobDetailsModel) -> Unit)? = null
    var orderDetailsCallBack: ((JobDetailsModel) -> Unit)? = null
    private val mGson: Gson by lazy {
        Gson()
    }


    init {
        when (type) {
            0 -> {
                addWaitPublishItem()
            }
            1 -> {
                addWaitExamineJobItem()
            }
            2 -> {
                addUnderWayPublishJobItem()
            }
            3 -> {
                addEndSignUpPublishJobItem()
            }
        }
    }

    /**
     * 等待发布
     */
    private fun addWaitPublishItem() {
        addItemViewType(object : ItemViewType<JobDetailsModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_wait_publish_item
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: JobDetailsModel?, position: Int): Boolean = true

            override fun convert(
                holder: ViewHolder?,
                jobDetailsModel: JobDetailsModel,
                position: Int
            ) {
                holder?.let {
                    val tvAccept = it.getView<TextView>(R.id.tvSingleSettle)
                    val parent = it.getView<ConstraintLayout>(R.id.parent)
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvJobMoney = it.getView<TextView>(R.id.tvJobMoney)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val tvRefuse = it.getView<TextView>(R.id.tvRefuse)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + jobDetailsModel.typeImgUrl)
                        .into(ivEmployeeImg)
                    tvEmployeeTitle.text = jobDetailsModel.title
                    tvJobMoney.text = "${jobDetailsModel.salary}币/小时"
                    tvSingUpTime.text = jobDetailsModel.createTime
                    //支付押金
                    tvAccept?.clickWithTrigger {
                        paymentCallBack?.invoke(jobDetailsModel.id.toString())

                    }
                    //取消发布
                    tvRefuse.clickWithTrigger {
                        cancelPublish?.invoke(jobDetailsModel.id.toString())
                    }

                    parent.clickWithTrigger {
                        contentClickCallBack?.invoke(jobDetailsModel)
                    }

                }

            }
        })
    }

    /**
     * 等待审核
     */
    private fun addWaitExamineJobItem() {
        addItemViewType(object : ItemViewType<JobDetailsModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_wait_examine_item
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: JobDetailsModel?, position: Int): Boolean = true

            override fun convert(
                holder: ViewHolder?,
                jobDetailsModel: JobDetailsModel,
                position: Int
            ) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvJobMoney = it.getView<TextView>(R.id.tvJobMoney)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val tvRefuse = it.getView<TextView>(R.id.tvRefuse)
                    val tvAccept = it.getView<TextView>(R.id.tvSingleSettle)
                    //
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + jobDetailsModel.typeImgUrl)
                        .into(ivEmployeeImg)
                    tvEmployeeTitle.text = jobDetailsModel.title
                    tvJobMoney.text = "${jobDetailsModel.salary}币/小时"
                    tvSingUpTime.text = jobDetailsModel.createTime
                    tvRefuse.clickWithTrigger {
                        cancelPublish?.invoke(jobDetailsModel.id.toString())
                    }
                    tvAccept.clickWithTrigger {
                        orderDetailsCallBack?.invoke(jobDetailsModel)
                    }
                }
            }
        })
    }

    /**
     * 正在招聘
     */
    private fun addUnderWayPublishJobItem() {
        addItemViewType(object : ItemViewType<JobDetailsModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_advertise_job_item
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: JobDetailsModel?, position: Int): Boolean = true

            override fun convert(
                holder: ViewHolder?,
                jobDetailsModel: JobDetailsModel,
                position: Int
            ) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvSingUpPeopleNum = it.getView<TextView>(R.id.tvSingUpPeopleNum)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val tvOrderDetails = it.getView<TextView>(R.id.tvOrderDetails)
                    val tvEmployeeCity = it.getView<TextView>(R.id.tvEmployeeCity)
                    val tvEmployeeArea = it.getView<TextView>(R.id.tvEmployeeArea)
                    val tvEmployeePeopleCounts = it.getView<TextView>(R.id.tvEmployeePeopleCounts)
                    val tvEmployeeRecord = it.getView<TextView>(R.id.tvEmployeeRecord)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + jobDetailsModel.typeImgUrl)
                        .into(ivEmployeeImg)
                    tvEmployeeTitle.text = jobDetailsModel.title
                    //雇佣记录
                    tvEmployeeRecord.clickWithTrigger {
                        mContext.startActivity<EmployRecordActivity>("jobId" to jobDetailsModel.id.toString())
                    }
                    //订单详情
                    tvOrderDetails.clickWithTrigger {
                        //                        mContext.startActivity<JobPublishActivity>("JobDetails" to jobDetailsModel.toJson())
                        mContext.startActivity<OrderDetailsActivity>("JobId" to jobDetailsModel.id.toString())
                    }
                    val listDates =
                        mGson.fromJson<List<String>>(
                            jobDetailsModel.datesJson,
                            object : TypeToken<List<String>>() {
                            }.type
                        )
                    tvSingUpTime.text = "${operationData(listDates.first())}-${operationData(listDates.last())} "
                    tvEmployeeCity.text = jobDetailsModel.city
                    tvEmployeeArea.text = jobDetailsModel.area
                    tvEmployeePeopleCounts.text = "${jobDetailsModel.recruitNum}人"
                    tvSingUpPeopleNum.text = jobDetailsModel.signNum.toString() //报名人数
                }

            }

        })
    }

    fun operationData(date: String): String {
        val dates = date.split("-")
        return "${dates[1]}月${dates[2]}日"
    }

    /**
     * 截至报名
     */
    private fun addEndSignUpPublishJobItem() {
        //未通过
        addItemViewType(object : ItemViewType<JobDetailsModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_examine_no_pass_item
            override fun isItemClickable(): Boolean = false
            override fun isViewForType(item: JobDetailsModel?, position: Int): Boolean =
                item?.statusCode == -1

            override fun convert(
                holder: ViewHolder?,
                jobDetailsModel: JobDetailsModel,
                position: Int
            ) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime) //截至报名
                    val tvDelete = it.getView<TextView>(R.id.tvDelete) //删除
                    val tvAccept = it.getView<TextView>(R.id.tvSingleSettle)//在招一次
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + jobDetailsModel.typeImgUrl)
                        .into(ivEmployeeImg)
                    tvEmployeeTitle.text = jobDetailsModel.title
                    tvSingUpTime.text = jobDetailsModel.endTime
                    tvDelete.clickWithTrigger {
                        //删除
                        cancelPublish?.invoke(jobDetailsModel.id.toString())
                    }
                    //
                    tvAccept.clickWithTrigger {
                        //在招一次
                        mContext.startActivity<JobPublishActivity>("JobDetails" to jobDetailsModel.toJson())
                    }
                }
            }
        })
        //已过期
        addItemViewType(object : ItemViewType<JobDetailsModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_end_sign_up_item
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: JobDetailsModel?, position: Int): Boolean =
                item?.statusCode != -1

            override fun convert(
                holder: ViewHolder?,
                jobDetailsModel: JobDetailsModel,
                position: Int
            ) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvSignUpRecord = it.getView<TextView>(R.id.tvSignUpRecord)
                    val tvSingleSettle = it.getView<TextView>(R.id.tvSingleSettle)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + jobDetailsModel.typeImgUrl)
                        .into(ivEmployeeImg)
                    tvEmployeeTitle.text = jobDetailsModel.title
                    //招聘记录
                    tvSignUpRecord.clickWithTrigger {
                        mContext.startActivity<EmployRecordActivity>("jobId" to jobDetailsModel.id.toString())
                    }
                    //在招一次
                    tvSingleSettle.clickWithTrigger {
                        mContext.startActivity<JobPublishActivity>("JobDetails" to jobDetailsModel.toJson())
                    }
                }
            }
        })
    }
}