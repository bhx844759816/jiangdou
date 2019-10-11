package com.jxqm.jiangdou.ui.order.view

import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import android.widget.TextView
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.AppManager
import com.bhx.common.utils.DensityUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.CommonConfig
import com.jxqm.jiangdou.ui.home.view.MainActivity
import com.jxqm.jiangdou.ui.order.model.OrderDetailsModel
import com.jxqm.jiangdou.ui.publish.model.TimeRangeModel
import com.jxqm.jiangdou.ui.publish.view.JobPublishActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_order_payment_success.*

/**
 * Created By bhx On 2019/9/4 0004 08:55
 */
class OrderPaymentSuccessActivity : BaseActivity() {
    private var orderDetailsModel: OrderDetailsModel? = null
    private val gson = Gson()
    override fun getLayoutId(): Int = R.layout.activity_order_payment_success
    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        val jsonString = intent.getStringExtra("orderDetailsModel")
        jsonString?.let {
            orderDetailsModel = CommonConfig.fromJson(it, OrderDetailsModel::class.java)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
        val spannableString = SpannableString("小豆会在1小时内对发布内容进行审核")
        spannableString.setSpan(
            ForegroundColorSpan(Color.rgb(136, 165, 253)),
            4,
            7,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvTips.text = spannableString
        //继续发布
        tvContinuePublish.clickWithTrigger {
            startActivity<JobPublishActivity>()
            AppManager.getAppManager().finishActivity(OrderPaymentSuccessActivity::class.java)
            AppManager.getAppManager().finishActivity(OrderPaymentActivity::class.java)
            AppManager.getAppManager().finishActivity(JobPublishActivity::class.java)
        }
        //返回工作台
        tvBackWorkStage.clickWithTrigger {
            AppManager.getAppManager().finishOthersActivity(MainActivity::class.java)
        }

    }

    override fun initData() {
        orderDetailsModel?.let {
            tvOrderNumber.text = it.orderNo
            tvJobSalary.text = "${it.salary} 币/小时"
            tvRecruitPeoples.text = "${it.recruitNum.toInt()} 人"
            tvJobWorkTime.text = "${it.count} 小时"
            tvTotalPaymentMoney.text = "${it.amount} 币"
            //设置下滑线
            val listDates = gson.fromJson<List<String>>(it.datesJson, object : TypeToken<List<String>>() {
            }.type)
            val listTimes =
                gson.fromJson<List<TimeRangeModel>>(it.timesJson, object : TypeToken<List<TimeRangeModel>>() {
                }.type)
            addDataRange(listDates)
            addTimeRange(listTimes)
        }
    }

    /**
     * 添加日期区间
     */
    private fun addDataRange(dateList: List<String>) {

        for (i in 0 until dateList.size step 2) {
            val startData = dateList[i]
            val endData = dateList[i + 1]
            var textView = TextView(this)
            val layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT
            )
            layoutParams.rightMargin = DensityUtil.dip2px(this, 10f)
            layoutParams.bottomMargin = DensityUtil.dip2px(this, 5f)
            textView.setTextColor(resources.getColor(R.color.text_hint))
            textView.textSize = DensityUtil.dip2px(this, 5f).toFloat()
            textView.text = "$startData 至 $endData"
            textView.layoutParams = layoutParams
            flDateRangeParent.addView(textView)
        }
    }

    /**
     * 添加时间区间
     */
    private fun addTimeRange(timeRangeList: List<TimeRangeModel>) {
        timeRangeList.forEach {
            val textView = TextView(this)
            val layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT
            )
            layoutParams.rightMargin = DensityUtil.dip2px(this, 10f)
            layoutParams.bottomMargin = DensityUtil.dip2px(this, 5f)
            textView.setTextColor(resources.getColor(R.color.text_hint))
            textView.textSize = DensityUtil.dip2px(this, 5f).toFloat()
            textView.text = "${it.start} - ${it.end}"
            textView.layoutParams = layoutParams
            flTimeRangeParent.addView(textView)
        }
    }
}