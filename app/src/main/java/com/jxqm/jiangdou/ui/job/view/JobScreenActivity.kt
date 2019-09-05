package com.jxqm.jiangdou.ui.job.view

import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_job_screen.*


/**
 * 筛选工作类型界面
 * Created by Administrator on 2019/8/18.
 */
class JobScreenActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_job_screen

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        calendarView.scrollToCalendar(calendarView.curYear, calendarView.curMonth, calendarView.curDay)
        ivCancel.clickWithTrigger {
            finish()
        }

    }

}