package com.jxqm.jiangdou.ui.publish.view

import android.view.View
import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import kotlinx.android.synthetic.main.activity_job_preview.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bhx.common.utils.DensityUtil


/**
 * 兼职预览界面
 * Created By bhx On 2019/8/12 0012 10:56
 */
class JobPreviewActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_job_preview


    override fun initView() {
        super.initView()
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        val layoutParams = nestedScrollView.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.bottomMargin = getStatusBarHeight() + DensityUtil.dip2px(this,50f)
        nestedScrollView.fullScroll(View.FOCUS_UP)
    }

    private fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }



}