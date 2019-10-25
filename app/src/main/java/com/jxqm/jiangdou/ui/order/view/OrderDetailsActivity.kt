package com.jxqm.jiangdou.ui.order.view

import android.graphics.Paint
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bhx.common.utils.DensityUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.haibin.calendarview.Calendar
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.OrderDetailsModel
import com.jxqm.jiangdou.model.TimeRangeModel
import com.jxqm.jiangdou.ui.order.vm.OrderDetailsViewModel
import com.jxqm.jiangdou.ui.web.AgreementWebActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import com.jxqm.jiangdou.view.dialog.QrCodeDialog
import kotlinx.android.synthetic.main.activity_order_details.*

/**
 * 订单详情界面
 * Created By bhx On 2019/9/5 0005 09:59
 */
class OrderDetailsActivity : BaseDataActivity<OrderDetailsViewModel>() {
    private var jobId: String? = null
    private var orderDetailsModel: OrderDetailsModel? = null
    private val gson = Gson()
    private var mBase64ImageString:String? = null
    override fun getEventKey(): Any = Constants.EVENT_KEY_ORDER_DETAILS

    override fun getLayoutId(): Int = R.layout.activity_order_details

    override fun initView() {
        super.initView()
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        jobId = intent.getStringExtra("JobId")
        jobId?.let {
            mViewModel.getOrderDetails(it)
        }
        rlQrCodeParent.clickWithTrigger {
            if (mBase64ImageString.isNullOrEmpty()) {
                mViewModel.getSignUpQrCode(jobId!!)
            } else {
                QrCodeDialog.show(this, mBase64ImageString!!)
            }
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
        tvDepositRule.clickWithTrigger {
            startActivity<AgreementWebActivity>("Status" to AgreementWebActivity.TAG_DEPOSIT_AGREEMENT)
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
                tvOrderCreateTime.text = it.createTime
                tvOrderPayTime.text = it.paymentTime
                tvOrderPayFinishTime.text = it.endTime
                //设置下滑线
                tvCommission.paintFlags = tvCommission.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                val listDates = gson.fromJson<List<String>>(it.datesJson, object : TypeToken<List<String>>() {}.type)
                val listTimes =
                    gson.fromJson<List<TimeRangeModel>>(it.timesJson, object : TypeToken<List<TimeRangeModel>>() {
                    }.type)
                addDataRange(listDates)
                addTimeRange(listTimes)
            })

        //获取签到二维码成功
        registerObserver(Constants.TAG_GET_SIGN_UP_QR_CODE_SUCCESS, String::class.java).observe(this, Observer {
            mBase64ImageString =  it
            QrCodeDialog.show(this,mBase64ImageString!!)
        })
    }

    /**
     * 添加日期区间
     */
    private fun addDataRange(dateList: List<String>) {
        val calendarList = mutableListOf<Calendar>()
        val rangeCalendarList = mutableListOf<MutableList<Calendar>>()
        dateList.forEach { date ->
            val calendar = Calendar()
            val endDates = date.split("-")
            calendar.year = endDates[0].toInt()
            calendar.month = endDates[1].toInt()
            calendar.day = endDates[2].toInt()
            calendarList.add(calendar)
        }
        calendarList.forEachIndexed { index, calendar ->
            if (index == 0) {
                val list = mutableListOf(calendar)
                rangeCalendarList.add(list)
                return@forEachIndexed
            }
            val lastCalendar = calendarList[index - 1]
            val lastTimeMillis = lastCalendar.timeInMillis + 24 * 60 * 60 * 1000
            val curTimeMillis = calendar.timeInMillis
            Log.i("TAG2", "$curTimeMillis")
            Log.i("TAG2", "$lastTimeMillis")
            Log.i("TAG2", "${curTimeMillis == lastTimeMillis}")
            if ((curTimeMillis - lastTimeMillis) <= 1000L) {
                val list = rangeCalendarList.last()
                list.add(calendar)
            } else {
                val list = mutableListOf(calendar)
                rangeCalendarList.add(list)
            }
        }
        rangeCalendarList.forEach {
            val strBuffer = StringBuffer()
            strBuffer.append(it.first().year)
                .append(" - ")
                .append(it.first().month)
                .append(" - ")
                .append(it.first().day)
            strBuffer.append("  至  ")
                .append(it.last().year)
                .append(" - ")
                .append(it.last().month)
                .append(" - ")
                .append(it.last().day)
            var textView = TextView(this)
            val layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT
            )
            layoutParams.rightMargin = DensityUtil.dip2px(this, 10f)
            layoutParams.bottomMargin = DensityUtil.dip2px(this, 5f)
            textView.setTextColor(resources.getColor(R.color.text_hint))
            textView.textSize = DensityUtil.dip2px(this, 5f).toFloat()
            textView.text = strBuffer.toString()
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