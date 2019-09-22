package com.jxqm.jiangdou.view.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.jxqm.jiangdou.R
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

/**
 * Created By bhx On 2019/9/5 0005 11:21
 */
class BaseRefreshFooter @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle), RefreshFooter {
    private var gifImageView: GifImageView
    private var llLoading: LinearLayout
    private var gifFromAssets: GifDrawable? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_refresh_footer, this)
        gifImageView = view.findViewById(R.id.ivNoMoreData)
        llLoading = view.findViewById(R.id.llLoading)
        gifFromAssets = GifDrawable(context.assets, "loading/gif_refresh.gif")
        gifImageView.setImageDrawable(gifFromAssets)
    }

    override fun getSpinnerStyle(): SpinnerStyle = SpinnerStyle.Translate

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        return 500
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
    }

    override fun setNoMoreData(noMoreData: Boolean): Boolean = true

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {

    }

    override fun getView(): View = this

    override fun setPrimaryColors(vararg colors: Int) {
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
    }

    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
    }

    override fun isSupportHorizontalDrag(): Boolean = false
}