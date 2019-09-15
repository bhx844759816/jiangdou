package com.jxqm.jiangdou.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.bhx.common.base.BaseDialogFragment
import com.bhx.common.utils.DensityUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.calendar.CalendarRangeSelectDialog
import kotlinx.android.synthetic.main.dialog_select_sex.*

/**
 * Created By bhx On 2019/8/9 0009 17:23
 */

class SelectSexDialog : BaseDialogFragment() {
    private var mCallback: ((String) -> Unit)? = null

    override fun getLayoutId(): Int = R.layout.dialog_select_sex

    override fun initView(view: View?) {
        tvNoSex.clickWithTrigger {
            mCallback?.invoke("不限")
            dismissAllowingStateLoss()
        }
        tvBoy.clickWithTrigger {
            mCallback?.invoke("男")
            dismissAllowingStateLoss()

        }
        tvGirl.clickWithTrigger {
            mCallback?.invoke("女")
            dismissAllowingStateLoss()
        }

    }

    override fun initWindow() {
        if (dialog != null && context != null) {
            val window = dialog.window
            if (window != null) {
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window.decorView.setPadding(DensityUtil.dip2px(context, 30f), 0, DensityUtil.dip2px(context, 30f), 0)
                val params = window.attributes
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = DensityUtil.dip2px(mContext, 210f)
                params.gravity = Gravity.CENTER
                window.attributes = params
            }
        }
    }

    companion object {
        private val TAG = SelectSexDialog::class.simpleName

        fun show(activity: FragmentActivity, callBack: ((String) -> Unit)) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                val dialog = SelectSexDialog()
                dialog.mCallback = callBack
                fragment = dialog
            }
            if (!fragment.isAdded) {
                val manager = activity.supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.add(fragment, TAG)
                transaction.commitAllowingStateLoss()
            }
        }

        fun dismiss(activity: FragmentActivity?) {
            val fragment = activity?.supportFragmentManager?.findFragmentByTag(TAG) as SelectSexDialog
            if (fragment.isAdded) {
                fragment.dismissAllowingStateLoss()
            }
        }
    }

}