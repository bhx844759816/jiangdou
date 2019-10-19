package com.jxqm.jiangdou.ui.user.view

import com.bhx.common.utils.AppManager
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.home.view.MainActivity
import com.jxqm.jiangdou.ui.user.vm.CashOutSuccessViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_cash_out_success.*

class CashOutSuccessActivity : BaseDataActivity<CashOutSuccessViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_cash_out_success

    override fun getEventKey(): Any = Constants.EVENT_CASH_OUT_SUCCESS

    override fun initView() {
        super.initView()
        toolbar.setNavigationOnClickListener {
            finish()
        }
        tvGoBackHome.clickWithTrigger {
            AppManager.getAppManager().finishOthersActivity(MainActivity::class.java)
        }
        tvGoBackWallet.clickWithTrigger {
            AppManager.getAppManager().finishActivity(CashOutActivity::class.java)
            AppManager.getAppManager().finishActivity(this)
        }
    }
}