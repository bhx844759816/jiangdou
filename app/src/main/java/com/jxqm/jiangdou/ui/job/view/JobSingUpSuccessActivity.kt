package com.jxqm.jiangdou.ui.job.view

import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.AppManager
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.home.view.MainActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_sing_up_success.*

/**
 * 报名成功
 * Created by Administrator on 2019/10/9.
 */
class JobSingUpSuccessActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_sing_up_success
    override fun initView() {
        super.initView()
        toolBar.setNavigationOnClickListener {
            finish()
        }

        tvBackHome.clickWithTrigger {
            AppManager.getAppManager().finishOthersActivity(MainActivity::class.java)
        }
        tvSeeDetails.clickWithTrigger {
            AppManager.getAppManager().finishOthersActivity(MainActivity::class.java)
        }
    }
}