package com.jxqm.jiangdou.ui.publish.view

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.event.LiveBus
import com.bhx.common.utils.DateUtils
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.LogUtils
import com.bhx.common.utils.ToastUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.haibin.calendarview.Calendar
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ext.isEnable
import com.jxqm.jiangdou.ext.isRightInput
import com.jxqm.jiangdou.listener.OnJobPublishCallBack
import com.jxqm.jiangdou.ui.publish.model.TimeRangeModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.calendar.CalendarRangeSelectDialog
import com.jxqm.jiangdou.view.dialog.SelectTimeRangeDialog
import kotlinx.android.synthetic.main.fragment_job_work_time.*
import java.text.SimpleDateFormat

/**
 * 工作时间以及工作福利的界面
 * Created By bhx On 2019/8/8 0008 14:20
 */
class JobTimeFragment : BaseLazyFragment() {

    private var mCallback: OnJobPublishCallBack? = null
    private var mRangeDateList = mutableListOf<MutableList<Calendar>>()

    private var mRangeCalendarList = mutableListOf<Calendar>()
    private var mRangeTimeList = mutableListOf<String>()
    private val mGson = Gson()
    private val mSimpleDateFormat: SimpleDateFormat by lazy {
        SimpleDateFormat("HH:mm")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnJobPublishCallBack) {
            mCallback = context
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_job_work_time

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)
        initStatus()
        etPayMoney.isRightInput()
        tvNextStep.clickWithTrigger {
            val salary = etPayMoney.text.toString().trim()
            if (mRangeCalendarList.isEmpty()) {
                ToastUtils.toastShort("请选择工作日期")
                return@clickWithTrigger
            }
            if (mRangeTimeList.isEmpty()) {
                ToastUtils.toastShort("请选择工作时段")
                return@clickWithTrigger
            }
            if (TextUtils.isEmpty(salary) || salary.toInt() <= 0) {
                ToastUtils.toastShort("请至少输入一个豆币的工资")
                return@clickWithTrigger
            }
            //发送选择的日期区间和时间区间以及工资 [2019-9-13,]
            val params = mutableMapOf<String, String>()
            val dataArray = arrayListOf<String>()
            val timeArray = arrayListOf<TimeRangeModel>()
            mRangeCalendarList.forEach { calendar ->
                val time =
                    "${calendar.year}-${operateDate(calendar.month.toString())}-${operateDate(
                        calendar.day.toString()
                    )}"
                dataArray.add(time)
            }
            for (i in 0 until mRangeTimeList.size step 2) {
                val rangeTimeMode = TimeRangeModel(mRangeTimeList[i], mRangeTimeList[i + 1])
                timeArray.add(rangeTimeMode)
            }
            params["datesJson"] = mGson.toJson(dataArray)
            params["timesJson"] = mGson.toJson(timeArray)
            params["salary"] = salary
            LiveBus.getDefault()
                .postEvent(Constants.EVENT_KEY_JOB_PUBLISH, Constants.TAG_PUBLISH_JOB_TIME, params)
            mCallback?.jobTimeNextStep()
        }
        tvSelectDate.clickWithTrigger {
            CalendarRangeSelectDialog.show(activity!!, mRangeCalendarList) {
                mRangeCalendarList.clear()
                mRangeCalendarList.addAll(it)
                LogUtils.i("CalendarRangeSelectDialog=$mRangeCalendarList")
                operateRangeDateList()
                addDateRange()
                isNextStepState()
            }
        }
        //选择时间段
        tvSelectTimeRange.clickWithTrigger {
            if (mRangeTimeList.size == 6) {
                ToastUtils.toastShort("最多选择三个时间段")
                return@clickWithTrigger
            }
            SelectTimeRangeDialog.show(activity!!) { start, end ->
                if (mRangeTimeList.isEmpty()) {
                    mRangeTimeList.add(start)
                    mRangeTimeList.add(end)
                } else {
                    var startIndex = mRangeTimeList.size
                    var endIndex = 0
                    run breaking@{
                        mRangeTimeList.forEachIndexed { index, value ->
                            println("starttime = ${mSimpleDateFormat.parse(start).time}")
                            println("valuetime = ${mSimpleDateFormat.parse(value).time}")
                            if (
                                mSimpleDateFormat.parse(start).time <= mSimpleDateFormat.parse(value).time
                            ) {
                                startIndex = index
                                return@breaking
                            }
                        }
                    }
                    mRangeTimeList.forEachIndexed { index, value ->
                        println("endtime = ${mSimpleDateFormat.parse(end).time}")
                        println("valuetime = ${mSimpleDateFormat.parse(value).time}")
                        if (
                            mSimpleDateFormat.parse(end).time >= mSimpleDateFormat.parse(value).time
                        ) {
                            endIndex = if (endIndex == mRangeTimeList.size - 1) {
                                index
                            } else {
                                index + 1
                            }
                        }
                    }
                    println("startIndex=$startIndex")
                    println("endIndex=$endIndex")
                    if (startIndex == mRangeTimeList.size || endIndex == 0) {
                        //直接添加数据
                        mRangeTimeList.add(start)
                        mRangeTimeList.add(end)
                    } else {
                        println("添加前数据")
                        mRangeTimeList.forEach(::println)
                        mRangeTimeList.add(startIndex, start)
                        mRangeTimeList.add(endIndex, end)
                        mRangeTimeList.sortBy {
                            mSimpleDateFormat.parse(it).time
                        }
                        val removeIndexArray = mutableListOf<Int>()
                        if (startIndex == endIndex) {
                            for (i in startIndex until (endIndex + 2)) {
                                removeIndexArray.add(i)
                            }
                        } else {
                            for (i in (startIndex + 1) until (endIndex + 2)) {
                                removeIndexArray.add(i)
                            }
                        }
                        println("添加完数据")
                        mRangeTimeList.forEach(::println)
                        println("需要移除的下表")
                        removeIndexArray.forEach(::println)
                        val list = mRangeTimeList.filterIndexed { index, s ->
                            !removeIndexArray.contains(index)
                        }
                        mRangeTimeList.clear()
                        mRangeTimeList.addAll(list)
                    }
                }
                mRangeTimeList.sortBy {
                    mSimpleDateFormat.parse(it).time
                }
                addTimeRange()
                isNextStepState()
            }
        }
        //付的钱
        etPayMoney.addTextChangedListener {
            afterTextChanged {
                isNextStepState()
            }
        }
    }


    private fun operateRangeDateList() {
        mRangeDateList.clear()
        mRangeCalendarList.forEachIndexed { index, calendar ->
            if (index == 0) {
                val list = mutableListOf(calendar)
                mRangeDateList.add(list)
                return@forEachIndexed
            }
            val lastCalendar = mRangeCalendarList[index - 1]
            val lastTimeMillis = lastCalendar.timeInMillis + 24 * 60 * 60 * 1000
            val curTimeMillis = calendar.timeInMillis
            Log.i("TAG2", "$curTimeMillis")
            Log.i("TAG2", "$lastTimeMillis")
            Log.i("TAG2", "${curTimeMillis == lastTimeMillis}")
            if ((curTimeMillis - lastTimeMillis) <= 1000L) {
                val list = mRangeDateList.last()
                list.add(calendar)
            } else {
                val list = mutableListOf(calendar)
                mRangeDateList.add(list)
            }
        }
    }

    /**
     * 初始化
     */
    private fun initStatus() {
        val model = (activity as JobPublishActivity).mJobDetailsModel
        model?.let {
            etPayMoney.setText(it.salary.toString())
            val listDates =
                mGson.fromJson<List<String>>(it.datesJson, object : TypeToken<List<String>>() {
                }.type)
            val listTimes =
                mGson.run {
                    fromJson<List<TimeRangeModel>>(
                        it.timesJson,
                        object : TypeToken<List<TimeRangeModel>>() {
                        }.type
                    )
                }
            //时间段
            mRangeTimeList.clear()
            listTimes.forEach { timeRangeModel ->
                mRangeTimeList.add(timeRangeModel.start)
                mRangeTimeList.add(timeRangeModel.end)
            }
            mRangeDateList.clear()
            mRangeCalendarList.clear()
            listDates.forEach { date ->
                val calendar = Calendar()
                val endDates = date.split("-")
                calendar.year = endDates[0].toInt()
                calendar.month = endDates[1].toInt()
                calendar.day = endDates[2].toInt()
                mRangeCalendarList.add(calendar)
            }
            operateRangeDateList()
            //添加日期
            addDateRange()
            addTimeRange()
            isNextStepState()
        }
    }

    /**
     * 拼接日期
     */
    private fun operateDate(date: String): String {
        if (date.length == 1) {
            return "0$date"
        }
        return date
    }

    /**
     * 添加日期区间View
     */
    private fun addDateRange() {
        llDataParent.removeAllViews()
        mRangeDateList.forEach {
            val calendarList = it
            val strBuffer = StringBuffer()
            if (it.size == 1) {
                strBuffer.append(it.first().year)
                    .append(" - ")
                    .append(it.first().month)
                    .append(" - ")
                    .append(it.first().day)
            }
            if (it.size > 1) {
                strBuffer.append(it.first().year)
                    .append(" - ")
                    .append(it.first().month)
                    .append(" - ")
                    .append(it.first().day)
                strBuffer.append("  至  ")
                    .append(it.last().year)
                    .append(" - ")
                    .append(it.last().month)
                    .append(" - ")
                    .append(it.last().day)
            }
            val content = strBuffer.toString()
            val view = LayoutInflater.from(mContext).inflate(R.layout.view_data_range_item, null)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                DensityUtil.dip2px(mContext, 35f)
            )
            params.bottomMargin = DensityUtil.dip2px(mContext, 8f)
            val rangeTextView = view.findViewById<TextView>(R.id.tvDateRange)
            rangeTextView.text = content
            val deleteView = view.findViewById<ImageView>(R.id.ivDelete)
            deleteView.clickWithTrigger {
                llDataParent.removeView(view)
                mRangeDateList.remove(calendarList)
                mRangeCalendarList.removeAll(calendarList)
                LogUtils.i("mRangeDateList2$mRangeDateList")
                LogUtils.i("mRangeCalendarList$mRangeCalendarList")
            }
            llDataParent.addView(view, params)
        }
    }

    /**
     * 下一步是否可以被点击
     */
    private fun isNextStepState() {
        val salary = etPayMoney.text.toString()
        val isEnable = mRangeDateList.isNotEmpty() && mRangeTimeList.isNotEmpty()
                && salary.isNotEmpty()
        if (isEnable) {
            tvNextStep.setBackgroundResource(R.drawable.shape_button_select)
        } else {
            tvNextStep.setBackgroundResource(R.drawable.shape_button_default)
        }
    }

    private fun addTimeRange() {
        llTimeParent.removeAllViews()
        for (i in 0 until mRangeTimeList.size step 2) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.view_data_range_item, null)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                DensityUtil.dip2px(mContext, 35f)
            )
            params.bottomMargin = DensityUtil.dip2px(mContext, 8f)
            val rangeTextView = view.findViewById<TextView>(R.id.tvDateRange)
            val startTime = mRangeTimeList[i]
            val endTime = mRangeTimeList[i + 1]
            rangeTextView.text = "$startTime —— $endTime"
            val deleteView = view.findViewById<ImageView>(R.id.ivDelete)
            deleteView.clickWithTrigger {
                llTimeParent.removeView(view)
                mRangeTimeList.remove(startTime)
                mRangeTimeList.remove(endTime)
            }
            llTimeParent.addView(view, params)
        }

    }

}