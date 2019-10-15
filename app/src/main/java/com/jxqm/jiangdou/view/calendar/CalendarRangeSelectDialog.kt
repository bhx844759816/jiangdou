package com.jxqm.jiangdou.view.calendar

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.bhx.common.base.BaseDialogFragment
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.LogUtils
import com.bhx.common.utils.ToastUtils
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.dialog_calendar_range_select.*

/**
 * Created By bhx On 2019/8/9 0009 15:24
 */
class CalendarRangeSelectDialog : BaseDialogFragment() {
    private var mCallback: ((MutableList<Calendar>) -> Unit)? = null

    private var mCalendarList = mutableListOf<Calendar>()
    override fun getLayoutId(): Int = R.layout.dialog_calendar_range_select


    override fun initView(view: View?) {
        super.initView(view)
        calendarView.setRange(
            calendarView.curYear, calendarView.curMonth, calendarView.curDay,
            calendarView.curYear + 4, 12, 30
        )
        //设置选中当前日期为起始点
        calendarView.setSelectStartCalendar(
            calendarView.curYear,
            calendarView.curMonth,
            calendarView.curDay
        )
        tvCurrentDate.text = "${calendarView.curMonth} 月 ${calendarView.curYear} 年"
        calendarView.setOnMonthChangeListener { year, month ->
            tvCurrentDate.text = "$month 月 $year 年"
        }
        //
        mCalendarList.forEach {
            calendarView.putMultiSelect(it)
        }
        tvConfirm.clickWithTrigger {
            if (calendarView.multiSelectCalendars.isEmpty()) {
                ToastUtils.toastShort("请至少选择一天")
                return@clickWithTrigger
            }
            mCallback?.invoke(calendarView.multiSelectCalendars)
            dismissAllowingStateLoss()
        }
        tvCancel.clickWithTrigger {
            dismissAllowingStateLoss()
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

    override fun initWindow() {
        if (dialog != null && context != null) {
            val window = dialog.window
            if (window != null) {
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window.decorView.setPadding(
                    DensityUtil.dip2px(context, 15f),
                    0,
                    DensityUtil.dip2px(context, 15f),
                    0
                )
                val params = window.attributes
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = DensityUtil.dip2px(context, 350f)
                params.gravity = Gravity.CENTER
                window.attributes = params
            }
        }
    }

    companion object {
        private val TAG = CalendarRangeSelectDialog::class.simpleName

        fun show(activity: FragmentActivity,calendarList:MutableList<Calendar>, callBack: ((MutableList<Calendar>) -> Unit)) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                val dialog = CalendarRangeSelectDialog()
                dialog.mCallback = callBack
                dialog.mCalendarList = calendarList
                fragment = dialog
            }
            if (!fragment.isAdded) {
                val manager = activity.supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.add(fragment, TAG)
                transaction.commitAllowingStateLoss()
            }
        }

        fun dismiss(activity: FragmentActivity?) {
            val fragment =
                activity?.supportFragmentManager?.findFragmentByTag(TAG) as CalendarRangeSelectDialog
            if (fragment.isAdded) {
                fragment.dismissAllowingStateLoss()
            }
        }
    }
}

