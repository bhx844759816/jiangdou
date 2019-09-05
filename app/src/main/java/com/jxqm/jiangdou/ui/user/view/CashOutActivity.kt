package com.jxqm.jiangdou.ui.user.view

import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_cash_out.*

/**
 * 提现界面
 * Created By bhx On 2019/9/4 0004 16:38
 */
class CashOutActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_cash_out
    override fun initView() {
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        cashOutBack.clickWithTrigger {
            finish()
        }
    }
}