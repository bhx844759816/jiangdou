package com.jxqm.jiangdou.ui.job.view

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.LogUtils
import com.bhx.common.view.FlowLayout
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.base.CommonConfig
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.publish.model.TimeRangeModel
import kotlinx.android.synthetic.main.activity_job_details.*
import kotlinx.android.synthetic.main.activity_job_details.ivMapView
import kotlinx.android.synthetic.main.activity_job_details.llDateParent
import kotlinx.android.synthetic.main.activity_job_details.nestedScrollView
import kotlinx.android.synthetic.main.activity_job_details.toolbar
import kotlinx.android.synthetic.main.activity_job_details.tvJobArea
import kotlinx.android.synthetic.main.activity_job_details.tvJobContent
import kotlinx.android.synthetic.main.activity_job_details.tvJobMoney
import kotlinx.android.synthetic.main.activity_job_details.tvJobTips
import kotlinx.android.synthetic.main.activity_job_details.tvJobTitle
import kotlinx.android.synthetic.main.activity_job_details.tvJobType
import kotlinx.android.synthetic.main.activity_job_details.tvRecruitPeoples
import kotlinx.android.synthetic.main.activity_order_payment.*
import kotlinx.android.synthetic.main.activity_publish_job_preview.*

import java.io.File
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG




/**
 * 工作详情界面
 * Created By bhx On 2019/8/12 0012 10:56
 */
class JobDetailsActivity : BaseActivity() {
    private var mJobDetailsModel: JobDetailsModel? = null
    override fun getLayoutId(): Int = R.layout.activity_job_details

    override fun initView() {
        super.initView()
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        val layoutParams = nestedScrollView.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.bottomMargin = getStatusBarHeight() + DensityUtil.dip2px(this, 60f)
        nestedScrollView.fullScroll(View.FOCUS_UP)
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
            showDateRange(it.dates, it.times)

            LogUtils.i(Api.HTTP_BASE_URL + it.mapImg)
            Glide.with(this).load(Api.HTTP_BASE_URL + "/" + it.mapImg).into(ivMapView)
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    private fun showDateRange(dates: List<String>, times: List<TimeRangeModel>) {
        for (i in 0 until dates.size step 2) {
            val startData = dates[i]
            val endData = dates[i + 1]
            val view = LayoutInflater.from(this)
                .inflate(R.layout.view_job_details_data_range, llDateParent)
            val tvStartDate = view.findViewById<TextView>(R.id.tvStartDate)
            val tvEndDate = view.findViewById<TextView>(R.id.tvEndDate)
            val flTimeRangeParent = view.findViewById<FlowLayout>(R.id.flTimeRangeParent)
            tvStartDate.text = startData
            tvEndDate.text = endData
            times.forEach {
                addTimeRange(flTimeRangeParent, it)
            }
        }

    }

    private fun addTimeRange(flowLayout: FlowLayout, timeRange: TimeRangeModel) {
        var textView = TextView(this)
        textView.setTextColor(resources.getColor(R.color.text_default))
        textView.textSize = DensityUtil.dip2px(this, 8f).toFloat()
        textView.setPadding(0, 0, DensityUtil.dip2px(this, 15f), DensityUtil.dip2px(this, 5f))
        textView.text = "${timeRange.start}-${timeRange.end}"
        flowLayout.addView(textView)
    }

}