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
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.job.adapter.JobScreenAreaAdapter
import com.jxqm.jiangdou.utils.clickWithTrigger

/**
 * Created by Administrator on 2019/9/7.
 */

class JobScreenByAreaPopupWindow(activity: Activity) : PopupWindow(activity) {

    private var mActivity = activity
    private var mAdapter: JobScreenAreaAdapter
    private var mAreaList = arrayListOf<String>()
    var mConfirmCallBack: ((String) -> Unit)? = null

    init {
        this.width = FrameLayout.LayoutParams.MATCH_PARENT
        this.height = FrameLayout.LayoutParams.MATCH_PARENT
        val inflater: LayoutInflater = LayoutInflater.from(activity)
        val rootView = inflater.inflate(R.layout.view_job_screen_by_area, null)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView)
        val llParent = rootView.findViewById<LinearLayout>(R.id.llParent)
        recyclerView.layoutManager = GridLayoutManager(mActivity, 4)
        mAdapter = JobScreenAreaAdapter(mActivity)
        recyclerView.adapter = mAdapter
        //点击确定
        rootView.findViewById<TextView>(R.id.tvConfirm).clickWithTrigger {
            mConfirmCallBack?.invoke(mAreaList[mAdapter.mSelectPosition])
            dismiss()
        }
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
                llParent.getGlobalVisibleRect(rect)
                if (!rect.contains(x.toInt(), y.toInt())) {
                    dismiss()
                }
            }
            true
        }

    }

    fun showPopup(view: View, list: MutableList<String>) {
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
            mAreaList.clear()
            mAreaList.addAll(list)
            mAdapter.setDataList(mAreaList)
        } else {
            dismiss()
        }
    }
}