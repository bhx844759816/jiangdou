package com.jxqm.jiangdou.ui.order.view

import android.graphics.Paint
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.DensityUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.order.model.OrderDetailsModel
import com.jxqm.jiangdou.ui.order.vm.OrderDetailsViewModel
import com.jxqm.jiangdou.ui.publish.model.TimeRangeModel
import kotlinx.android.synthetic.main.activity_order_details.*

/**
 * 订单详情界面
 * Created By bhx On 2019/9/5 0005 09:59
 */
class OrderDetailsActivity : BaseDataActivity<OrderDetailsViewModel>() {
    private var jobId: String? = null
    private var orderDetailsModel: OrderDetailsModel? = null
    private val gson = Gson()
    override fun getEventKey(): Any = Constants.EVENT_KEY_ORDER_DETAILS

    override fun getLayoutId(): Int = R.layout.activity_order_details

    override fun initView() {
        super.initView()
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        jobId = intent.getStringExtra("JobId")
        jobId?.let {
            mViewModel.getOrderDetails(it)
        }
    }

    /**
     * 注册LiveData事件监听
     */
    override fun dataObserver() {
        //获取订单详情成功
        registerObserver(Constants.TAG_GET_ORDER_DETAILS_SUCCESS, OrderDetailsModel::class.java).observe(
            this,
            Observer {
                orderDetailsModel = it
                tvOrderNumber.text = it.orderNo
                tvJobSalary.text = "${it.salary} 币/小时"
                tvRecruitPeoples.text = "${it.recruitNum.toInt()} 人"
                tvJobWorkTime.text = "${it.count} 小时"
                tvTotalPaymentMoney.text = "${it.amount} 币"
                tvCommission.text = "${it.commission} 币"
                //设置下滑线
                tvCommission.paintFlags = tvCommission.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                val listDates = gson.fromJson<List<String>>(it.datesJson, object : TypeToken<List<String>>() {}.type)
                val listTimes =
                    gson.fromJson<List<TimeRangeModel>>(it.timesJson, object : TypeToken<List<TimeRangeModel>>() {
                    }.type)
                addDataRange(listDates)
                addTimeRange(listTimes)
            })
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