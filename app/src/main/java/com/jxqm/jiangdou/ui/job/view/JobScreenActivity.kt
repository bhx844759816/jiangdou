package com.jxqm.jiangdou.ui.job.view

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.view.isVisible
import com.bhx.common.base.BaseActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
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
    private val mParamsMap = mutableMapOf<String, String>()
    private val mLimitArray = mutableListOf<String>()
    private val mGson: Gson by lazy {
        Gson()
    }

    override fun getLayoutId(): Int = R.layout.activity_job_screen

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        val jsonString = intent.getStringExtra("ScreenResult")
        jsonString?.let {
            val params = mGson.fromJson<Map<String, String>>(it, object : TypeToken<Map<String, String>>() {
            }.type)
            mParamsMap.putAll(params)
        }
        initListener()
        initUiStatus()

    }

    private fun initListener() {
        //取消
        ivCancel.clickWithTrigger {
            finish()
        }
        //日期月份改变切换
        calendarView.setOnMonthChangeListener { year, month ->
            tvCurrentTime.text = "$year 年 $month 月"
        }
        //选择得日期
        calendarView.setOnCalendarSelectListener(object : CalendarView.OnCalendarSelectListener {
            override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                if (isClick) {
                    calendar?.let {
                        mParamsMap["year"] = it.year.toString()
                        mParamsMap["month"] = it.month.toString()
                        mParamsMap["day"] = it.day.toString()
                        tvStartWorkTime.text = "${it.year} 年 ${it.month} 月 ${it.day}"
                    }
                }
            }

            override fun onCalendarOutOfRange(calendar: Calendar?) {
            }

        })
        //开始工作时间
        rbLimitJobTime.setOnCheckedChangeListener { _, checked ->
            tvStartWorkTime.visibility = if (checked) {
                View.VISIBLE
            } else {
                View.GONE
            }
            llTimeParent.visibility = if (checked) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        //性别要求
        rgSexSelect.setOnCheckedChangeListener { _, id ->
            val sexParams = when (id) {
                R.id.rbNoSex -> {//全部  //woman, man, all
                    "all"
                }
                R.id.rbBoy -> {//男
                    "man"
                }
                R.id.rbGirl -> {//女
                    "woman"
                }
                else -> {
                    "all"
                }
            }
            mParamsMap["gender"] = sexParams
        }
        //上班时段 - 不限
        rbNoLimitTime.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                rbLimitAfternoon.isChecked = false
                rbLimitMorning.isChecked = false
                rbLimitNight.isChecked = false
            }
        }
        //上班时段 - 上午
        rbLimitMorning.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                checkTimeLimitStatus()
            }
        }
        //上班时段 - 下午
        rbLimitAfternoon.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                checkTimeLimitStatus()
            }

        }
        //上班时段 - 晚上
        rbLimitNight.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                checkTimeLimitStatus()
            }
        }
        //重置状态
        tvReset.clickWithTrigger {
            rbNoLimitTime.isChecked = true
            rbNoSex.isChecked = true
            rbNoLimitJobTime.isChecked = true
        }
        //确定
        tvConfirm.clickWithTrigger {
             //
            if(rbNoLimitTime.isChecked){
                mParamsMap.remove("times")
            }else{
                if(rbLimitMorning.isChecked){
                    mLimitArray.add("am")
                }
                if(rbLimitAfternoon.isChecked){
                    mLimitArray.add("pm")
                }
                if(rbLimitNight.isChecked){
                    mLimitArray.add("nm")
                }
            }
            if (mLimitArray.isNotEmpty()) {
                mParamsMap["times"] = mLimitArray.joinToString(",")
            }
            //
            if (tvStartWorkTime.visibility == View.VISIBLE) {
                mParamsMap["date"] = tvStartWorkTime.text.toString()
            }else{
                mParamsMap.remove("date")
                mParamsMap.remove("year")
                mParamsMap.remove("month")
                mParamsMap.remove("day")
            }
            val intent = Intent()
            intent.putExtra("ScreenResult", mGson.toJson(mParamsMap))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun initUiStatus() {
        val gender = mParamsMap["gender"]
        gender?.let {
            when (it) {
                "all" -> {
                    rbNoSex.isChecked = true
                }
                "man" -> {
                    rbBoy.isChecked = true
                }
                "woman" -> {
                    rbGirl.isChecked = true
                }
            }
        }
        val times = mParamsMap["times"]
        times?.let {
            val arrays = times.split(",")
            arrays.forEach { time ->
                when (time) {
                    "am" -> {
                        rbLimitMorning.isChecked = true
                    }
                    "pm" -> {
                        rbLimitAfternoon.isChecked = true
                    }
                    "nm" -> {
                        rbLimitNight.isChecked = true
                    }
                }
            }
        }
        val date = mParamsMap["date"]
        if (date.isNullOrEmpty()) {
            calendarView.scrollToCalendar(calendarView.curYear, calendarView.curMonth, calendarView.curDay)
            tvCurrentTime.text = "${calendarView.curYear} 年 ${calendarView.curMonth} 月"
            tvStartWorkTime.text = "${calendarView.curYear} 年 ${calendarView.curMonth} 月 ${calendarView.curDay}"
        } else {
            rbLimitJobTime.isChecked = true
            tvStartWorkTime.text = "${ mParamsMap["year"]} 年 ${mParamsMap["month"]} 月 ${mParamsMap["day"]}"
            calendarView.scrollToCalendar(
                mParamsMap["year"]!!.toInt(),
                mParamsMap["month"]!!.toInt(),
                mParamsMap["day"]!!.toInt()
            )
        }

    }

    private fun checkTimeLimitStatus() {
        rbNoLimitTime.isChecked = rbLimitAfternoon.isChecked && rbLimitMorning.isChecked && rbLimitNight.isChecked
    }
}