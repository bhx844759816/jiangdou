package com.jxqm.jiangdou.ui.user.view

import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_about_us.*

/**
 * 关于我们界面
 * Created By bhx On 2019/9/5 0005 15:30
 */
class AboutUsActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_about_us

    override fun initView() {
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)

        aboutUsBack.clickWithTrigger {
            finish()
        }
    }
}