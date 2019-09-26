package com.jxqm.jiangdou.ui.publish.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.DensityUtil
import com.bhx.common.view.FlowLayout
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.base.CommonConfig
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.order.view.OrderPaymentActivity
import com.jxqm.jiangdou.ui.publish.model.TimeRangeModel
import com.jxqm.jiangdou.ui.publish.vm.JobPublishDetailsViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_employ_job_publish.*

/**
 * 发布兼职的详情界面
 * 可以取消发布 以及支付押金
 * Created By bhx On 2019/9/4 0004 16:20
 */
class JobPublishDetailsActivity : BaseDataActivity<JobPublishDetailsViewModel>() {

    private val gson = Gson()
    override fun getEventKey(): Any = Constants.EVENT_KEY_JOB_PUBLISH_DETAILS

    private var mJobDetailsModel: JobDetailsModel? = null
    override fun getLayoutId(): Int = R.layout.activity_employ_job_publish

    override fun initView() {
        super.initView()
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        val jsonString = intent.getStringExtra("JobDetailsModel")
        mJobDetailsModel = CommonConfig.fromJson(jsonString!!, JobDetailsModel::class.java)
        mJobDetailsModel?.let {
            tvJobType.text = it.jobTypeValue
            tvJobMoney.text = "${it.salary} 币/小时"
            tvJobTips.text = it.areaCode //
            tvRecruitPeoples.text = "招${it.recruitNum}人"
            tvJobSex.text = it.gender
            tvJobTitle.text = it.title
            tvJobContent.text = it.content
            tvJobArea.text = it.area
            val listDates = gson.fromJson<List<String>>(it.datesJson, object : TypeToken<List<String>>() {
            }.type)
            val listTimes =
                gson.fromJson<List<TimeRangeModel>>(it.timesJson, object : TypeToken<List<TimeRangeModel>>() {
                }.type)
            if (listDates.isNotEmpty() && listTimes.isNotEmpty()) {
                showDateRange(listDates, listTimes)
            }
            Glide.with(this).load(Api.HTTP_BASE_URL + "/" + it.mapImg).into(ivMapView)
        }

        tvPayMoney.clickWithTrigger {
           startActivity<OrderPaymentActivity>("JobId" to mJobDetailsModel!!.id)
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun showDateRange(dates: List<String>, times: List<TimeRangeModel>) {
        for (i in 0 until dates.size step 2) {
            val startData = dates[i]
            val endData = dates[i + 1]
            val view = LayoutInflater.from(this)
                .inflate(R.layout.view_job_details_data_range, null)
            val tvStartDate = view.findViewById<TextView>(R.id.tvStartDate)
            val tvEndDate = view.findViewById<TextView>(R.id.tvEndDate)
            val flTimeRangeParent = view.findViewById<FlowLayout>(R.id.flTimeRangeParent)
            tvStartDate.text = startData
            tvEndDate.text = endData
            times.forEach {
                addTimeRange(flTimeRangeParent, it)
            }
            llDateParent.addView(view)
        }

    }

    private fun addTimeRange(flowLayout: FlowLayout, timeRange: TimeRangeModel) {
        val textView = TextView(this)
        val layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
        layoutParams.rightMargin = DensityUtil.dip2px(this, 10f)
        layoutParams.bottomMargin = DensityUtil.dip2px(this, 5f)
        textView.setTextColor(resources.getColor(R.color.text_hint))
        textView.textSize = DensityUtil.dip2px(this, 5f).toFloat()
        textView.text = "${timeRange.start}-${timeRange.end}"
        textView.layoutParams = layoutParams
        flowLayout.addView(textView)
    }

    private fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }
}