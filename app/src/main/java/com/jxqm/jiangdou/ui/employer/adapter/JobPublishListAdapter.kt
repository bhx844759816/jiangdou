package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.ui.employer.view.EmployRecordActivity
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * Created by Administrator on 2019/9/22.
 */
class JobPublishListAdapter(context: Context, type: Int) : MultiItemTypeAdapter<JobDetailsModel>(context) {
    var paymentCallBack: ((String) -> Unit)? = null
    var cancelPublish: ((String) -> Unit)? = null
    var contentClickCallBack: ((JobDetailsModel) -> Unit)? = null
    var orderDetailsCallBack: ((JobDetailsModel) -> Unit)? = null

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

            override fun convert(holder: ViewHolder?, jobDetailsModel: JobDetailsModel, position: Int) {
                holder?.let {
                    val tvAccept = it.getView<TextView>(R.id.tvAccept)
                    val parent = it.getView<ConstraintLayout>(R.id.parent)
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvJobMoney = it.getView<TextView>(R.id.tvJobMoney)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val tvRefuse = it.getView<TextView>(R.id.tvRefuse)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + jobDetailsModel.typeImg)
                        .into(ivEmployeeImg)
                    tvEmployeeTitle.text = jobDetailsModel.title
                    tvJobMoney.text = "${jobDetailsModel.salary}币/小时"
                    tvSingUpTime.text = jobDetailsModel.createTime
                    //支付押金
                    tvAccept?.clickWithTrigger {
                        paymentCallBack?.invoke(jobDetailsModel.id)

                    }
                    //取消发布
                    tvRefuse.clickWithTrigger {
                        cancelPublish?.invoke(jobDetailsModel.id)
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

            override fun convert(holder: ViewHolder?, jobDetailsModel: JobDetailsModel, position: Int) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvJobMoney = it.getView<TextView>(R.id.tvJobMoney)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val tvRefuse = it.getView<TextView>(R.id.tvRefuse)
                    val tvAccept = it.getView<TextView>(R.id.tvAccept)
                    //
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + jobDetailsModel.typeImg)
                        .into(ivEmployeeImg)
                    tvEmployeeTitle.text = jobDetailsModel.title
                    tvJobMoney.text = "${jobDetailsModel.salary}币/小时"
                    tvSingUpTime.text = jobDetailsModel.createTime
                    tvRefuse.clickWithTrigger {

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

            override fun convert(holder: ViewHolder?, jobDetailsModel: JobDetailsModel, position: Int) {
                holder?.let {
                    val ivEmployeeImg = it.getView<ImageView>(R.id.ivEmployeeImg)
                    val tvEmployeeTitle = it.getView<TextView>(R.id.tvEmployeeTitle)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val tvOrderDetails = it.getView<TextView>(R.id.tvOrderDetails)
                    val tvEmployeeCity = it.getView<TextView>(R.id.tvEmployeeCity)
                    val tvEmployeeArea = it.getView<TextView>(R.id.tvEmployeeArea)
                    val tvEmployeePeopleCounts = it.getView<TextView>(R.id.tvEmployeePeopleCounts)
                    val tvEmployeeRecord = it.getView<TextView>(R.id.tvEmployeeRecord)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + jobDetailsModel.typeImg)
                        .into(ivEmployeeImg)
                    tvEmployeeTitle.text = jobDetailsModel.title
                    tvEmployeeRecord.clickWithTrigger {
                        mContext.startActivity<EmployRecordActivity>("jobId" to jobDetailsModel.id)
                    }
                }

            }

        })
    }

    /**
     * 截至报名
     */
    private fun addEndSignUpPublishJobItem() {
        addItemViewType(object : ItemViewType<JobDetailsModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_end_sign_up_item
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: JobDetailsModel?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, jobDetailsModel: JobDetailsModel, position: Int) {
                holder?.let {
                    val ivRefund = it.getView<ImageView>(R.id.ivRefund)
                    val tvPass = it.getView<TextView>(R.id.tvPass)
                    val tvSignUpRecord = it.getView<TextView>(R.id.tvSignUpRecord)
                }

//                if (endSignUpItem.isPass) {
//                    tvPass?.setBackgroundResource(R.drawable.shape_wait_pay_bg)
//                    tvPass?.text = "已结束"
//                    tvSignUpRecord?.text = "招聘记录"
//                    ivRefund?.visibility = View.GONE
//                } else {
//                    tvPass?.setBackgroundResource(R.drawable.shape_no_pass_bg)
//                    tvPass?.text = "未通过"
//                    tvSignUpRecord?.text = "删除"
//                    ivRefund?.visibility = View.VISIBLE
//                }

            }
        })
    }
}