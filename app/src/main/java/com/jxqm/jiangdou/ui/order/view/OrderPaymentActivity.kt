package com.jxqm.jiangdou.ui.order.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bhx.common.event.LiveBus
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
import com.jxqm.jiangdou.ui.order.vm.OrderPaymentViewModel
import com.jxqm.jiangdou.ui.web.AgreementWebActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import com.jxqm.jiangdou.view.dialog.PromptDialog
import kotlinx.android.synthetic.main.activity_order_payment.*


/**
 * 订单支付界面
 * Created by Administrator on 2019/9/3.
 */
@SuppressLint("SetTextI18n")
class OrderPaymentActivity : BaseDataActivity<OrderPaymentViewModel>() {

    private var mAccountBalance: Float = 0.0f //账户余额
    private val gson = Gson()
    private var jobId: String? = null
    private var orderDetailsModel: OrderDetailsModel? = null

    override fun getEventKey(): Any = Constants.EVENT_KEY_PAYMENT_ORDER

    override fun getLayoutId(): Int = R.layout.activity_order_payment

    override fun initView() {
        super.initView()
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        jobId = intent.getStringExtra("JobId")
        //获取订单详情
        jobId?.let {
            mViewModel.getOrderDetail(it)
        }
        val spannableString = SpannableString("请支付如下押金")
        spannableString.setSpan(
            ForegroundColorSpan(Color.rgb(136, 165, 253)),
            1,
            3,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvTitle.text = spannableString
        //支付押金
        tvPay.clickWithTrigger {
            if (isAccountBalanceAdequate()) {
                PromptDialog.show(this, "确认支付押金并发布兼职吗？") {
                    orderDetailsModel?.let {
                        mViewModel.payOrder(it.jobId)
                    }
                }
            } else {

            }
        }
        tvDepositRule.clickWithTrigger {
            startActivity<AgreementWebActivity>("Status" to AgreementWebActivity.TAG_DEPOSIT_AGREEMENT)
        }
        //返回键监听
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    /**
     * 注册接收
     */
    override fun dataObserver() {
        //获取订单详情成功
        registerObserver(
            Constants.TAG_GET_ORDER_DETAILS_SUCCESS,
            OrderDetailsModel::class.java
        ).observe(
            this,
            Observer {
                orderDetailsModel = it
                tvOrderNumber.text = it.orderNo
                tvJobSalary.text = "${it.salary} 币/小时"
                tvRecruitPeoples.text = "${it.recruitNum.toInt()} 人"
                tvJobWorkTime.text = "${it.count} 小时"
                tvTotalPaymentMoney.text = "${it.amount} 币"
                tvCommission.text = "${it.commission} 币"
                tvTotalPayment.text = "支付   ${it.amount} 币"
                //设置删除
                tvCommission.paintFlags = tvCommission.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                val listDates =
                    gson.fromJson<List<String>>(it.datesJson, object : TypeToken<List<String>>() {
                    }.type)
                val listTimes =
                    gson.fromJson<List<TimeRangeModel>>(
                        it.timesJson,
                        object : TypeToken<List<TimeRangeModel>>() {
                        }.type
                    )
                addDataRange(listDates)
                addTimeRange(listTimes)
                if (!isAccountBalanceAdequate()) {
                    tvPay.setBackgroundColor(Color.parseColor("#FF7A45"))
                    tvPay.text = "余额不足，去充值"
                }
            })
        //获取账户余额
        registerObserver(
            Constants.TAG_GET_USER_ACCOUNT_BALANCE_SUCCESS,
            String::class.java
        ).observe(this, Observer {
            mAccountBalance = it.toFloat()
        })
        //支付订单成功
        registerObserver(Constants.TAG_PAY_ORDER_SUCCESS, Boolean::class.java).observe(
            this,
            Observer {
                //支付押金成功刷新等待发布列表
                LiveBus.getDefault().postEvent(
                    Constants.EVENT_KEY_WAIT_PUBLISH_JOB,
                    Constants.TAG_WAIT_PUBLISH_REFRESH_JOB_LIST, true
                )
                //支付押金成功刷新等待审核列表
                LiveBus.getDefault().postEvent(
                    Constants.EVENT_KEY_WAIT_EXAMINE_JOB,
                    Constants.TAG_WAIT_EXAMINE_REFRESH_JOB_LIST, true
                )
                //刷新截至报名
                LiveBus.getDefault().postEvent(
                    Constants.EVENT_KEY_END_SIGN_UP,
                    Constants.TAG_END_SIGN_UP_JOB_LIST, true
                )
                //跳转到支付成功界面
                startActivity<OrderPaymentSuccessActivity>("orderDetailsModel" to orderDetailsModel!!.toJson())
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

    /**
     * 判断余额是否充足
     */
    private fun isAccountBalanceAdequate(): Boolean {
        orderDetailsModel?.let {
            return it.amount.toFloat() < mAccountBalance
        }
        return true
    }
}