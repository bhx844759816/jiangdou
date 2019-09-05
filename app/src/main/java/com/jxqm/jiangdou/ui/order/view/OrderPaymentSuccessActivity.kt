package com.jxqm.jiangdou.ui.order.view

import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_job_preview.*
import kotlinx.android.synthetic.main.activity_order_payment_success.*

/**
 * Created By bhx On 2019/9/4 0004 08:55
 */
class OrderPaymentSuccessActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_order_payment_success
    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        orderPaymentSuccessBack.clickWithTrigger {
            finish()
        }
    }
}