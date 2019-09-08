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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        process()
    }

    fun process() {
        // 华为,OPPO机型在StatusBarUtil.setLightStatusBar后布局被顶到状态栏上去了
        if (SDK_INT >= M) {
            val content = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
            content?.fitsSystemWindows = true
        }
    }

}