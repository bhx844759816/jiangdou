package com.jxqm.jiangdou.ui.job.widget

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.bhx.common.utils.DensityUtil
import com.jxqm.jiangdou.R
import android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND
import android.widget.FrameLayout
import com.jxqm.jiangdou.view.MultipleRadioGroup
import android.R.attr.bottom
import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.util.DisplayMetrics
import android.view.*
import android.widget.RadioGroup
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.utils.AnimationUtil


/**
 * Created by Administrator on 2019/8/17.
 */
class JobListSortPopupWindow(activity: Activity) : PopupWindow(activity) {
    private lateinit var rgSortParent: RadioGroup
    private var callback: ((Int, String) -> Unit)? = null
    private var mActivity = activity

    init {
        init(activity)
    }

    private fun init(activity: Activity) {
        this.width = FrameLayout.LayoutParams.MATCH_PARENT
        this.height = FrameLayout.LayoutParams.MATCH_PARENT
        val inflater: LayoutInflater = LayoutInflater.from(activity)
        val rootView = inflater.inflate(R.layout.view_job_list_sort_popup, null)
        rgSortParent = rootView.findViewById(R.id.rgSortParent)
        rgSortParent.setOnCheckedChangeListener { _, p1 ->
            when (p1) {
                R.id.rbSortWhole -> {
                    dismissPopup()
                    callback?.invoke(0,"综合排序")
                }
                R.id.rbSortMoney -> {
                    dismissPopup()
                    callback?.invoke(1,"时薪最高")
                }
                R.id.rbSortJobPeoples -> {
                    dismissPopup()
                    callback?.invoke(2,"招人最多")
                }
            }

        }
        this.contentView = rootView
        this.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        this.isOutsideTouchable = true
        this.isFocusable = true
        this.animationStyle = R.style.popup_animation
        setOnDismissListener {
            dismiss()
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
            if (SDK_INT < 24) {
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
            //执行进入动画，这里主要是执行列表下滑，背景变半透明在setAnimationStyle(R.style.SelectPopupWindow);中实现
            AnimationUtil.createAnimation(true, contentView, rgSortParent, null)
        } else {
            dismiss()
        }
    }

    fun setHeight() {

    }

    fun setCallBack(callback: ((Int, String) -> Unit)?) {
        this.callback = callback
    }

    //消失
    fun dismissPopup() {
        super.dismiss()// 调用super.dismiss(),如果直接dismiss()会一直会调用下面的dismiss()
    }

    override fun dismiss() {
        //执行推出动画，列表上滑退出，同时背景变透明
        AnimationUtil.createAnimation(false, contentView, rgSortParent) {
            dismissPopup()
        }
    }
}