package com.jxqm.jiangdou.ui.publish.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginBottom
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.LogUtils
import com.bhx.common.view.FlowLayout
import com.bumptech.glide.Glide
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.CommonConfig
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.publish.model.TimeRangeModel
import kotlinx.android.synthetic.main.activity_publish_job_preview.*
import java.io.File

/**
 * 预览简历
 * Created By bhx On 2019/9/4 0004 14:31
 */
class PublishJobPreviewActivity : BaseActivity() {
    private var mJobDetailsModel: JobDetailsModel? = null
    override fun getLayoutId(): Int = R.layout.activity_publish_job_preview

    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        val jsonString = intent.getStringExtra("JobDetailsModel")
        mJobDetailsModel = CommonConfig.fromJson(jsonString!!, JobDetailsModel::class.java)
        LogUtils.i("工作详情$mJobDetailsModel")
        mJobDetailsModel?.let {
            tvJobType.text = it.jobTypeValue
            tvJobMoney.text = "${it.salary} 币/小时"
            tvRecruitPeoples.text = "招${it.recruitNum}人"
            tvJobTitle.text = it.title
            tvJobContent.text = it.content
            tvJobTips.text = it.areaCode.split(",")[1]
            tvJobArea.text = it.area
            showDateRange(it.dates, it.times)
            val mapFile = File(Constants.APP_SAVE_DIR + Constants.MAPVIEW_FILENAME)
            Glide.with(this).load(mapFile).into(ivMapView)
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
                .inflate(R.layout.view_job_details_data_range, llDateParent)
            val tvStartDate = view.findViewById<TextView>(R.id.tvStartDate)
            val tvEndDate = view.findViewById<TextView>(R.id.tvEndDate)
            val flTimeRangeParent = view.findViewById<FlowLayout>(R.id.flTimeRangeParent)
            tvStartDate.text = startData
            tvEndDate.text = endData
            times.forEach {
                addTimeRange(flTimeRangeParent,it)
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