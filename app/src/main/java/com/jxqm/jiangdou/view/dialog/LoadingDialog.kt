package com.jxqm.jiangdou.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.bhx.common.base.BaseDialogFragment
import com.bhx.common.utils.DensityUtil
import com.jxqm.jiangdou.R
import kotlinx.android.synthetic.main.dialog_loading.*
import pl.droidsonroids.gif.GifDrawable
import java.io.IOException

/**
 * 加载对话框
 * Created By bhx On 2019/9/5 0005 11:14
 */
class LoadingDialog : BaseDialogFragment() {
    private var gifDrawable: GifDrawable? = null
    override fun getLayoutId(): Int = R.layout.dialog_loading

    override fun initView(view: View?) {
        setIsCanceledOnTouchOutside(false)
        try {
            gifDrawable = GifDrawable(mContext.assets, "loading/gif_loading.gif")
            gifImageView.setImageDrawable(gifDrawable)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gifDrawable?.recycle()
    }

    companion object {
        private val TAG = LoadingDialog::class.simpleName

        fun show(activity: FragmentActivity) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                fragment = LoadingDialog()
            }
            if (!fragment.isAdded) {
                val manager = activity.supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.add(fragment, TAG)
                transaction.commitAllowingStateLoss()
            }
        }

        fun dismiss(activity: FragmentActivity?) {
            val dialog = activity?.supportFragmentManager?.findFragmentByTag(TAG)
            dialog?.let {
                val fragment = dialog as LoadingDialog
                if (fragment.isAdded) {
                    fragment.dismissAllowingStateLoss()
                }
            }
        }
    }

}