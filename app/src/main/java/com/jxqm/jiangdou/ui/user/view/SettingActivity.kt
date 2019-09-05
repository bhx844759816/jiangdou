package com.jxqm.jiangdou.ui.user.view

import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * 设置界面
 * Created By bhx On 2019/9/5 0005 09:25
 */
class SettingActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initView() {
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        rlBack.clickWithTrigger { finish() }
    }
}