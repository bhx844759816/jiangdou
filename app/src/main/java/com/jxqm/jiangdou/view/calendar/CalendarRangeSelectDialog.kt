package com.jxqm.jiangdou.view.calendar

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.bhx.common.base.BaseDialogFragment
import com.bhx.common.utils.DensityUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.view.dialog.UserAgreementDialog
import kotlinx.android.synthetic.main.dialog_calendar_range_select.*

/**
 * Created By bhx On 2019/8/9 0009 15:24
 */
class CalendarRangeSelectDialog : BaseDialogFragment() {

    override fun getLayoutId(): Int = R.layout.dialog_calendar_range_select

    override fun initView(view: View?) {
        super.initView(view)
        calendarView.setRange(
            calendarView.curYear, calendarView.curMonth, calendarView.curDay,
            2020, 12, 30
        )
    }

    override fun initWindow() {
        if (dialog != null && context != null) {
            val window = dialog.window
            if (window != null) {
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window.decorView.setPadding(DensityUtil.dip2px(context, 15f), 0, DensityUtil.dip2px(context, 15f), 0)
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

        fun show(activity: FragmentActivity) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                fragment = CalendarRangeSelectDialog()
            }
            if (!fragment.isAdded) {
                val manager = activity.supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.add(fragment, TAG)
                transaction.commitAllowingStateLoss()
            }
        }

        fun dismiss(activity: FragmentActivity?) {
            val fragment = activity?.supportFragmentManager?.findFragmentByTag(TAG) as CalendarRangeSelectDialog
            if (fragment.isAdded) {
                fragment.dismissAllowingStateLoss()
            }
        }
    }
}

