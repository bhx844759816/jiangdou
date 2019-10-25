package com.jxqm.jiangdou.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Resources
import androidx.appcompat.widget.DialogTitle
import androidx.appcompat.widget.Toolbar
import com.bhx.common.mvvm.BaseMVVMActivity
import com.bhx.common.mvvm.BaseViewModel
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import android.os.Build.VERSION_CODES.M
import android.os.Build.VERSION.SDK_INT
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bhx.common.http.ApiException
import com.bhx.common.utils.LogUtils
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.home.view.MainActivity
import com.jxqm.jiangdou.ui.login.view.LoadingActivity
import com.jxqm.jiangdou.view.dialog.LoadingDialog
import com.umeng.analytics.MobclickAgent


/**
 * 公共类
 * Created By bhx On 2019/8/6 0006 09:38
 */
abstract class BaseDataActivity<T : BaseViewModel<*>> : BaseMVVMActivity<T>() {

    override fun initView() {
        super.initView()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        LogUtils.i("MyApplication isRecycler${MyApplication.instance().isRecyclerFlag} $this.javaClass=${this.javaClass}")
        if (MyApplication.instance().isRecyclerFlag == -1 && this.javaClass != LoadingActivity::class.java) {
            LogUtils.i("MyApplication isRecycler")
            protectApp()
        }
    }

    /**
     * 跳转到MainActivity然后跳转到启动页
     */
    open fun protectApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)//清空栈里MainActivity之上的所有activty
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
        LogUtils.i("${this.javaClass.simpleName},onResume")
        //注册加载对话框监听
        registerObserver(
            Constants.EVENT_KEY_LOADING_DIALOG,
            Constants.TAG_LOADING_DIALOG,
            Boolean::class.java
        ).observe(
            this, Observer {
                if (it != null && it) {
                    LoadingDialog.show(this)
                } else {
                    LoadingDialog.dismiss(this)
                }
            })
        // 注册Http请求得监听
        registerObserver(
            Constants.EVENT_KEY_HTTP_REQUEST_ERROR,
            Constants.TAG_HTTP_REQUEST_ERROR,
            Throwable::class.java
        ).observe(
            this, Observer {
                if (it is ApiException) {
                    val code = it.code
                    ToastUtils.toastShort(it.message)
                }
            }
        )
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
        LogUtils.i("${this.javaClass.simpleName},onPause")
        //页面不显示的时候取消注册对话框监听
        unRegisterObserver(Constants.EVENT_KEY_LOADING_DIALOG, Constants.TAG_LOADING_DIALOG)
        unRegisterObserver(Constants.EVENT_KEY_HTTP_REQUEST_ERROR, Constants.TAG_HTTP_REQUEST_ERROR)
    }

    fun process() {
        // 华为,OPPO机型在StatusBarUtil.setLightStatusBar后布局被顶到状态栏上去了
        if (SDK_INT >= M) {
            val content = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
            content?.fitsSystemWindows = true
        }
    }

}