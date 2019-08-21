package com.jxqm.jiangdou.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Created by Administrator on 2019/8/20.
 */

class NoScrollViewPage(context: Context, attributeSet: AttributeSet? = null) : ViewPager(context, attributeSet) {
    private var noScroll = true

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if(noScroll)
            return false
        return super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if(noScroll)
            return false
        return super.onInterceptTouchEvent(ev)
    }

}