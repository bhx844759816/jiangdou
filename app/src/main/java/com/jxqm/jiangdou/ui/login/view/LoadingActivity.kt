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
import androidx.lifecycle.Observer
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.home.view.MainActivity
import com.jxqm.jiangdou.ui.login.vm.LoadingViewModel


/**
 * 启动页
 * Created By bhx On 2019/9/5 0005 15:29
 */
class LoadingActivity : BaseDataActivity<LoadingViewModel>() {
    override fun getEventKey(): Any = Constants.EVENT_KET_LOADING
    override fun getLayoutId(): Int = R.layout.activity_loading
    override fun initView() {
        super.initView()
//        hideBottomUIMenu()
        MyApplication.instance().isRecyclerFlag = 0
        requestPermission()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
        }

    }


    override fun dataObserver() {
        //获取信息完成
        registerObserver(Constants.TAG_LOADING_FINISH, Boolean::class.java).observe(this, Observer {
            startActivity<MainActivity>()
            finish()
        })
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
            RxPermissions(this).request(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION
            ).subscribe {
                mViewModel.getUserInfo()
            }
        addDisposable(disposable)

    }
}