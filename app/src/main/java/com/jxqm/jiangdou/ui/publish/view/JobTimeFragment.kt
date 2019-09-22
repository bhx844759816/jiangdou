package com.jxqm.jiangdou.ui.publish.view

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.google.gson.Gson
import com.haibin.calendarview.Calendar
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.isEnable
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
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnJobPublishCallBack) {
            mCallback = context
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_job_work_time

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)
        tvNextStep.clickWithTrigger {
            //发送选择的日期区间和时间区间以及工资 [2019-9-13,]
            val params = mutableMapOf<String, String>()
            val dataArray = arrayListOf<String>()
            val timeArray = arrayListOf<TimeRangeModel>()
            mRangeDateList.forEach {
                val startTime = "${it.first().year}-${it.first().month}-${it.first().day}"
                val endTime = "${it.last().year}-${it.last().month}-${it.last().day}"
                dataArray.add(startTime)
                dataArray.add(endTime)
            }
            for (i in 0 until mRangeTimeList.size step 2) {
                val rangeTimeMode = TimeRangeModel(mRangeTimeList[i], mRangeTimeList[i + 1])
                timeArray.add(rangeTimeMode)
            }
            val gson = Gson()
            params["dates"] = gson.toJson(dataArray)
            params["times"] = gson.toJson(timeArray)
            params["salary"] = etPayMoney.text.toString().trim()
            LiveBus.getDefault().postEvent(Constants.EVENT_KEY_JOB_PUBLISH, Constants.TAG_PUBLISH_JOB_TIME, params)
            mCallback?.jobTimeNextStep()
        }
        tvSelectDate.clickWithTrigger {
            CalendarRangeSelectDialog.show(activity!!) {
                it.forEach { calendar ->
                    if (!mRangeCalendarList.contains(calendar)) {
                        mRangeCalendarList.add(calendar)
                    }
                }
                mRangeCalendarList.sort()
                mRangeDateList.clear()
                //生成数组对象
                mRangeCalendarList.forEachIndexed { index, calendar ->
                    Log.i("TAG2", "$calendar")
                    if (index == 0) {
                        val list = mutableListOf(calendar)
                        mRangeDateList.add(list)
                        return@forEachIndexed
                    }
                    if (index == mRangeCalendarList.size - 1) {
                        val list = mRangeDateList.last()
                        list.add(calendar)
                        return@forEachIndexed
                    }
                    val lastCalendar = mRangeCalendarList[index - 1]
                    val lastTimeMillis = lastCalendar.timeInMillis + 24 * 60 * 60 * 1000
                    val curTimeMillis = calendar.timeInMillis
                    Log.i("TAG2", "$curTimeMillis")
                    Log.i("TAG2", "$lastTimeMillis")
                    Log.i("TAG2", "${curTimeMillis == lastTimeMillis}")
                    if ((curTimeMillis - lastTimeMillis) <= 2) {
                        val list = mRangeDateList.last()
                        list.add(calendar)
                    } else {
                        val list = mutableListOf(calendar)
                        mRangeDateList.add(list)
                    }
                }
                addDateRange()
                isNextStepState()
            }
        }
        tvNextStep.isEnable(etPayMoney) { isNextStepState() }
        tvSelectTimeRange.clickWithTrigger {
            SelectTimeRangeDialog.show(activity!!) { start, end ->
                mRangeTimeList.add(start)
                mRangeTimeList.add(end)
                addTimeRange(start, end)
                isNextStepState()
            }
        }
    }

    /**
     * 添加日期区间View
     */
    private fun addDateRange() {
        llDataParent.removeAllViews()
        mRangeDateList.forEach {
            val calendarList = it
            val content =
                StringBuffer().append(it.first().year)
                    .append(" - ")
                    .append(it.first().month)
                    .append(" - ")
                    .append(it.first().day)
                    .append("  至  ")
                    .append(it.last().year)
                    .append(" - ")
                    .append(it.last().month)
                    .append(" - ")
                    .append(it.last().day).toString()
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
    private fun isNextStepState(): Boolean {
        val isEnable = mRangeCalendarList.isNotEmpty() && mRangeTimeList.isNotEmpty()
                && etPayMoney.text.toString().isNotEmpty()
        tvNextStep.isEnabled = isEnable
        return isEnable
    }

    private fun addTimeRange(startTime: String, endTime: String) {
        val view = LayoutInflater.from(mContext).inflate(R.layout.view_data_range_item, null)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            DensityUtil.dip2px(mContext, 35f)
        )
        params.bottomMargin = DensityUtil.dip2px(mContext, 8f)
        val rangeTextView = view.findViewById<TextView>(R.id.tvDateRange)
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