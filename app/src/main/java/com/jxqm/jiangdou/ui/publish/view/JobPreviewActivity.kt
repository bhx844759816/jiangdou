package com.jxqm.jiangdou.ui.publish.view

import android.os.Bundle
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.StatusBarUtil
import com.jxqm.jiangdou.R

/**
 * 兼职预览界面
 * Created By bhx On 2019/8/12 0012 10:56
 */
class JobPreviewActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_job_preview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initView() {
        super.initView()
        StatusBarUtil.transparencyBar(this) //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this) //设置白底黑字
        StatusBarUtil.setTransparent(this)
    }
}