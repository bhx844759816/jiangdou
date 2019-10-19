package com.jxqm.jiangdou.view.dialog

import android.app.ActivityManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.bhx.common.base.BaseDialogFragment
import com.bhx.common.utils.DensityUtil
import com.contrarywind.adapter.WheelAdapter
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.adapter.ArrayWheelAdapter
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.dialog_select_time_range.*

/**
 * 选着时间段dialog
 * Created By bhx On 2019/8/9 0009 17:37
 */
class SelectTimeRangeDialog : BaseDialogFragment() {

    private val mItems = arrayListOf(
        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19", "20", "21", "22", "23"
    )
    private var mEndTimeItems = mutableListOf<String>()
    private val mSecondItems = arrayListOf("00", "10", "20", "30", "40", "50")
    private var mCallBack: ((String, String) -> Unit)? = null
    override fun getLayoutId(): Int = R.layout.dialog_select_time_range

    override fun initView(view: View?) {
        wvStartTime.adapter = ArrayWheelAdapter(mItems)
        wvStartSecond.adapter = ArrayWheelAdapter(mSecondItems)
        wvStartTime.currentItem = 8
        mEndTimeItems = mItems.subList(wvStartTime.currentItem, mItems.size)
        wvEndTime.adapter = ArrayWheelAdapter(mEndTimeItems)
        wvEndSecond.adapter = ArrayWheelAdapter(mSecondItems)
        wvEndTime?.currentItem = wvEndTime.adapter.itemsCount / 2
        wvStartTime.setOnItemSelectedListener {
            mEndTimeItems = mItems.subList(it, mItems.size)
            wvEndTime.adapter = ArrayWheelAdapter(mEndTimeItems)
            wvEndSecond.adapter = ArrayWheelAdapter(mSecondItems)
            wvEndTime?.currentItem = wvEndTime.adapter.itemsCount / 2
        }
        wvEndTime.setOnItemSelectedListener {
            val selectTime = mEndTimeItems[it]
            if (selectTime == mItems[wvStartTime.currentItem]) {
                wvEndSecond.adapter =
                    ArrayWheelAdapter(mSecondItems.subList(wvStartSecond.currentItem,mSecondItems.size))
            }

        }
        wvStartSecond.setOnItemSelectedListener {


        }


        tvCancel.clickWithTrigger {
            dismissAllowingStateLoss()
        }
        tvConfirm.clickWithTrigger {
            if (wvStartTime.currentItem > wvEndTime.currentItem) {
                return@clickWithTrigger
            }
            val startTime =
                "${mItems[wvStartTime.currentItem]}:${mSecondItems[wvStartSecond.currentItem]}"
            val endTime =
                "${mItems[wvEndTime.currentItem]}:${mSecondItems[wvEndSecond.currentItem]}"
            mCallBack?.invoke(startTime, endTime)
            dismissAllowingStateLoss()
        }
    }

    override fun initWindow() {
        if (dialog != null && context != null) {
            val window = dialog.window
            if (window != null) {
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window.decorView.setPadding(
                    DensityUtil.dip2px(context, 30f),
                    0,
                    DensityUtil.dip2px(context, 30f),
                    0
                )
                val params = window.attributes
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = DensityUtil.dip2px(context, 265f)
                params.gravity = Gravity.CENTER
                window.attributes = params
            }
        }
    }

    companion object {
        private val TAG = SelectTimeRangeDialog::class.simpleName

        fun show(activity: FragmentActivity, callBack: (String, String) -> Unit) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                val dialog = SelectTimeRangeDialog()
                dialog.mCallBack = callBack
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
                activity?.supportFragmentManager?.findFragmentByTag(TAG) as SelectTimeRangeDialog
            if (fragment.isAdded) {
                fragment.dismissAllowingStateLoss()
            }
        }
    }

}