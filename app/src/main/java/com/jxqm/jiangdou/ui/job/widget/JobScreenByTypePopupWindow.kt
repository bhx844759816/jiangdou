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
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.JobTypeModel
import com.jxqm.jiangdou.ui.job.adapter.JobScreenTypeAdapter
import com.jxqm.jiangdou.utils.clickWithTrigger

/**
 *
 * 全部兼职 - 筛选通过兼职类型
 * Created by Administrator on 2019/9/7.
 */
class JobScreenByTypePopupWindow(activity: Activity) : PopupWindow(activity) {
    private var mActivity = activity
    private var mAdapter: JobScreenTypeAdapter //
    private var recyclerView: RecyclerView
    private var rbJobTypeAll: CheckBox
    private var mJobTypeList = mutableListOf<JobTypeModel>()
    var mConfirmCallBack: ((MutableList<Int>) -> Unit)? = null

    init {
        this.width = FrameLayout.LayoutParams.MATCH_PARENT
        this.height = FrameLayout.LayoutParams.MATCH_PARENT
        val inflater: LayoutInflater = LayoutInflater.from(activity)
        val rootView = inflater.inflate(R.layout.view_job_screen_by_type, null)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        rbJobTypeAll = rootView.findViewById(R.id.rbJobTypeAll)
        recyclerView.layoutManager = LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false)
        mAdapter = JobScreenTypeAdapter(mActivity)
        recyclerView.adapter = mAdapter
        //点击确定
        rootView.findViewById<TextView>(R.id.tvConfirm).clickWithTrigger {
            mConfirmCallBack?.invoke(mAdapter.mJopTypeArrayList)
            dismiss()
        }
        mAdapter.mTypeSelectCallBack = {
            rbJobTypeAll.isChecked = false
        }
        rbJobTypeAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mAdapter.clearCheck()
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

    }

    fun showPopup(view: View, list: List<JobTypeModel>, jobTypId: String?) {
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
            mJobTypeList.clear()
            mJobTypeList.addAll(list)
            jobTypId?.let {
                mJobTypeList.forEach { jobTypeModel ->
                    jobTypeModel.jobTypes.forEach { jobTypeModel2 ->
                        if (jobTypeModel2.id.toString() == it) {
                            jobTypeModel2.isChecked = true
                        }
                    }
                }
                rbJobTypeAll.isChecked = false
            }
            LogUtils.i("jobTypId=$jobTypId mJobTypeList=$mJobTypeList")
            mAdapter.setDataList(mJobTypeList)
        } else {
            dismiss()
        }
    }
}