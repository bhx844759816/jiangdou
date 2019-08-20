package com.jxqm.jiangdou.ui.job.view

import android.view.View
import com.bhx.common.base.BaseActivity
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.dialog.SingleSelectDialog
import kotlinx.android.synthetic.main.activity_job_screen.*
import com.jxqm.jiangdou.MainActivity



/**
 * 筛选工作类型界面
 * Created by Administrator on 2019/8/18.
 */
class JobScreenActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_job_screen

    override fun initView() {
        super.initView()
        calendarView.scrollToCalendar(calendarView.curYear, calendarView.curMonth, calendarView.curDay)


    }

}