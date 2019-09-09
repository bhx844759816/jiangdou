package com.jxqm.jiangdou.base

import androidx.appcompat.widget.DialogTitle
import androidx.appcompat.widget.Toolbar
import com.bhx.common.mvvm.BaseMVVMActivity
import com.bhx.common.mvvm.BaseViewModel
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import kotlinx.android.synthetic.main.view_toolbar.*
import androidx.core.view.ViewCompat.setFitsSystemWindows
import android.os.Build.VERSION_CODES.M
import android.os.Build.VERSION.SDK_INT
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bhx.common.http.ApiException
import com.bhx.common.utils.LogUtils
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.view.dialog.LoadingDialog


/**
 * 公共类
 * Created By bhx On 2019/8/6 0006 09:38
 */
abstract class BaseDataActivity<T : BaseViewModel<*>> : BaseMVVMActivity<T>() {
    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
    }


    override fun onResume() {
        super.onResume()
        LogUtils.i("${this.javaClass.simpleName},onResume")
        //注册加载对话框监听
        registerObserver(Constants.EVENT_KEY_LOADING_DIALOG, Constants.TAG_LOADING_DIALOG, Boolean::class.java).observe(
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