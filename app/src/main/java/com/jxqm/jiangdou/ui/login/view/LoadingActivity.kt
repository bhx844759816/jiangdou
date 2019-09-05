package com.jxqm.jiangdou.ui.login.view

import android.os.Build
import android.os.Handler
import android.view.WindowManager
import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.startActivity

/**
 * 启动页
 * Created By bhx On 2019/9/5 0005 15:29
 */
class LoadingActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_loading
    override fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
        }
        Handler().postDelayed({
            startActivity<GuideActivity>()
            finish()
        }, 2000)
    }

    override fun onBackPressed() {

    }
}