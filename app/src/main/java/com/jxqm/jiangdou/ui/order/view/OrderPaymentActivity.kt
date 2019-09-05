package com.jxqm.jiangdou.ui.order.view

import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import com.jxqm.jiangdou.view.dialog.PromptDialog
import kotlinx.android.synthetic.main.activity_job_preview.*
import kotlinx.android.synthetic.main.activity_order_payment.*

/**
 * 订单支付界面
 * Created by Administrator on 2019/9/3.
 */
class OrderPaymentActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_order_payment

    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        tvPay.clickWithTrigger {
            PromptDialog.show(this) {
                startActivity<OrderPaymentSuccessActivity>()
            }
        }
        orderPaymentBack.clickWithTrigger {
            finish()
        }
    }
}