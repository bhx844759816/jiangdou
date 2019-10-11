package com.jxqm.jiangdou.ui.job.widget

import android.app.Activity
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.RadioGroup
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.JobTypeModel

/**
 * Created by Administrator on 2019/9/7.
 */
class JobScreenBySortPopupWindow(activity: Activity) : PopupWindow(activity) {
    private var mActivity = activity
    private var rgSortParent: RadioGroup
    var mCallBack: ((String) -> Unit)? = null

    init {
        this.width = FrameLayout.LayoutParams.MATCH_PARENT
        this.height = FrameLayout.LayoutParams.MATCH_PARENT
        val inflater: LayoutInflater = LayoutInflater.from(activity)
        val rootView = inflater.inflate(R.layout.view_job_screen_by_sort, null)
        rgSortParent = rootView.findViewById(R.id.rgSortParent)
        this.contentView = rootView
        this.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        this.isOutsideTouchable = true
        this.isFocusable = true
        contentView.setOnTouchListener { p0, p1 ->
            if (p1.action == MotionEvent.ACTION_DOWN) {
                val x = p1.x
                val y = p1.y
                val rect = Rect()
                rgSortParent.getGlobalVisibleRect(rect)
                if (!rect.contains(x.toInt(), y.toInt())) {
                    dismiss()
                }
            }
            true
        }
        //选择排序
        rgSortParent.setOnCheckedChangeListener { _, id ->
            //time, salary, recruitNum, instance
            val params = when (id) {
                R.id.rbSortAll -> {//综合排序
                    ""
                }
                R.id.rbSortTime -> {//最新发布
                    "time"
                }
                R.id.rbSortDistance -> {//距离最短
                    "instance"
                }
                R.id.rbSortSalary -> {//时薪最高
                    "salary"
                }
                R.id.rbSortRecruitNum -> {//找人最多
                    "recruitNum"
                }
                else -> {
                    ""
                }
            }
            mCallBack?.invoke(params)
            dismiss()
        }
    }

    fun showPopup(view: View) {
        if (!this.isShowing) {
            if (Build.VERSION.SDK_INT < 24) {
                showAsDropDown(view)
            } else {
                val outMetrics = DisplayMetrics()
                mActivity.windowManager.defaultDisplay.getRealMetrics(outMetrics)
                val heightPixel = outMetrics.heightPixels
                val rect = Rect()
                view.getGlobalVisibleRect(rect)
                val h = heightPixel - rect.bottom
                this.height = h
                showAsDropDown(view)
            }
        } else {
            dismiss()
        }
    }
}