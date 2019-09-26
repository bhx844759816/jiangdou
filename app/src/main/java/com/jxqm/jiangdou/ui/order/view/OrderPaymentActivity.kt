package com.jxqm.jiangdou.ui.order.view

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bhx.common.mvvm.BaseMVVMActivity
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.LogUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.order.model.OrderDetailsModel
import com.jxqm.jiangdou.ui.order.vm.OrderPaymentViewModel
import com.jxqm.jiangdou.ui.publish.model.TimeRangeModel
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

    private var mAccountBalance: Float = 0.0f
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
            mViewModel.getAccountBalance()
            mViewModel.getOrderDetails(it)
        }
        //支付押金
        tvPay.clickWithTrigger {
            PromptDialog.show(this) {
                orderDetailsModel?.let {
                    mViewModel.payOrder(it.jobId)
                }
            }
        }
        //返回键监听
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }


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
                tvTotalPayment.text = "支付   ${it.amount} 币"
                //设置下滑线
                tvCommission.paintFlags = tvCommission.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                val listDates = gson.fromJson<List<String>>(it.datesJson, object : TypeToken<List<String>>() {
                }.type)
                val listTimes =
                    gson.fromJson<List<TimeRangeModel>>(it.timesJson, object : TypeToken<List<TimeRangeModel>>() {
                    }.type)
                addDataRange(listDates)
                addTimeRange(listTimes)
            })
        //获取账户余额
        registerObserver(Constants.TAG_GET_USER_ACCOUNT_BALANCE_SUCCESS, String::class.java).observe(this, Observer {
            mAccountBalance = it.toFloat()
        })
        //支付订单成功
        registerObserver(Constants.TAG_PAY_ORDER_SUCCESS, Boolean::class.java).observe(this, Observer {
            startActivity<OrderPaymentSuccessActivity>("orderDetailsModel" to orderDetailsModel!!.toJson())
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