package com.jxqm.jiangdou.view.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.jxqm.jiangdou.R
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

/**
 * 刷新头
 * Created By bhx On 2019/9/4 0004 17:31
 */
class BaseRefreshHeader @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attributeSet, defStyle), RefreshHeader {
    private  var gifImageView: GifImageView
    private var gifFromAssets: GifDrawable? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_refresh_header,this)
        gifImageView  =  view.findViewById(R.id.gifImageView)
        gifFromAssets = GifDrawable(context.assets, "loading/gif_refresh.gif")
        gifImageView.setImageDrawable(gifFromAssets)
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        return 500 //延迟500ms在回弹
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {

    }

    override fun setPrimaryColors(vararg colors: Int) {

    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
    }

    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
    }

    override fun isSupportHorizontalDrag(): Boolean = false

    override fun getSpinnerStyle(): SpinnerStyle = SpinnerStyle.Translate

    override fun getView(): View = this
}