package com.jxqm.jiangdou.ui.login.view

import android.Manifest
import android.os.Build
import android.os.Handler
import android.view.View
import android.view.WindowManager
import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.startActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION



/**
 * 启动页
 * Created By bhx On 2019/9/5 0005 15:29
 */
class LoadingActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_loading
    override fun initView() {
//        hideBottomUIMenu()
        requestPermission()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
        }

    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    private fun hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 17) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = (SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or SYSTEM_UI_FLAG_IMMERSIVE_STICKY or SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }

    override fun onBackPressed() {

    }


    /**
     * 请求权限 SD卡存储和拍照权限
     */
    private fun requestPermission() {
        val disposable =
            RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe {
                    Handler().postDelayed({
                        startActivity<GuideActivity>()
                        finish()
                    }, 2000)
                }
        addDisposable(disposable)

    }
}