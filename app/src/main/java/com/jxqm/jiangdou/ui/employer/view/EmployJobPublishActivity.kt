package com.jxqm.jiangdou.ui.employer.view

import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.order.view.OrderPaymentActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_employ_job_publish.*
import kotlinx.android.synthetic.main.activity_job_preview.*
import kotlinx.android.synthetic.main.activity_job_preview.toolbar

/**
 * 兼职预览发布界面
 * Created By bhx On 2019/9/4 0004 16:20
 */
class EmployJobPublishActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_employ_job_publish

    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        tvPayMoney.clickWithTrigger {
            startActivity<OrderPaymentActivity>()
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}