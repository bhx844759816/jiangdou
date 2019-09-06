package com.jxqm.jiangdou.ui.user.view

import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_job_preview.*
import kotlinx.android.synthetic.main.activity_user_wallet.*
import kotlinx.android.synthetic.main.activity_user_wallet.toolbar

/**
 * 我的钱包
 * Created By bhx On 2019/9/6 0006 10:37
 */
class UserWalletActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_user_wallet

    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        tvCashOutMoney.clickWithTrigger {
            startActivity<CashOutActivity>()
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

}