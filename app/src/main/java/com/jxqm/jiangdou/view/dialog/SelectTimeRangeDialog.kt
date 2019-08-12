package com.jxqm.jiangdou.view.dialog

import android.app.ActivityManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.bhx.common.base.BaseDialogFragment
import com.bhx.common.utils.DensityUtil
import com.contrarywind.adapter.WheelAdapter
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.adapter.ArrayWheelAdapter
import kotlinx.android.synthetic.main.dialog_select_time_range.*

/**
 * 选着时间段dialog
 * Created By bhx On 2019/8/9 0009 17:37
 */
class SelectTimeRangeDialog : BaseDialogFragment() {

    open val mItems = arrayListOf(
        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
        "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"
    )

    override fun getLayoutId(): Int = R.layout.dialog_select_time_range

    override fun initView(view: View?) {

        wvStartTime.adapter = ArrayWheelAdapter(mItems)
        wvStartSecond.adapter = ArrayWheelAdapter(mItems)
        wvEndTime.adapter = ArrayWheelAdapter(mItems)
        wvEndSecond.adapter = ArrayWheelAdapter(mItems)
    }

    override fun initWindow() {
        if (dialog != null && context != null) {
            val window = dialog.window
            if (window != null) {
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window.decorView.setPadding(DensityUtil.dip2px(context, 30f), 0, DensityUtil.dip2px(context, 15f), 0)
                val params = window.attributes
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = DensityUtil.dip2px(context, 200f)
                params.gravity = Gravity.CENTER
                window.attributes = params
            }
        }
    }


}