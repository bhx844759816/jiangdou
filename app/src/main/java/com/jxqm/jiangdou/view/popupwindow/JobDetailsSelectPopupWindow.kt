package com.jxqm.jiangdou.view.popupwindow

import android.app.Activity
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RadioGroup
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.clickWithTrigger

class JobDetailsSelectPopupWindow(activity: Activity) : PopupWindow(activity) {
    private val mActivity = activity
    private lateinit var rgSortParent: LinearLayout
    var mCallback: ((Int) -> Unit)? = null

    init {
        init(activity)
    }

    private fun init(activity: Activity) {
        this.width = FrameLayout.LayoutParams.MATCH_PARENT
        this.height = FrameLayout.LayoutParams.MATCH_PARENT
        val inflater: LayoutInflater = LayoutInflater.from(activity)
        val rootView = inflater.inflate(R.layout.view_job_details_select_popup, null)
        rgSortParent = rootView.findViewById(R.id.rgSortParent)
        this.contentView = rootView
        this.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        this.isOutsideTouchable = true
        this.isFocusable = true
//        this.animationStyle = R.style.popup_animation2
        rootView.findViewById<FrameLayout>(R.id.flShare).clickWithTrigger {
            dismiss()
            mCallback?.invoke(0)
        }
        rootView.findViewById<FrameLayout>(R.id.flRefuse).clickWithTrigger {
            dismiss()
            mCallback?.invoke(1)
        }
        rootView.findViewById<FrameLayout>(R.id.flGoBackHome).clickWithTrigger {
            dismiss()
            mCallback?.invoke(2)
        }
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
    }

    fun showPopup(view: View) {
        if (!this.isShowing) {
            if (Build.VERSION.SDK_INT < 24) {
                showAsDropDown(view)
//                showAtLocation(view,Gravity.RIGHT,0,0)
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