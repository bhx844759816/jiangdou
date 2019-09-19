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
import kotlinx.android.synthetic.main.dialog_attestation_success.*

/**
 * 上传审核成功弹出的提示界面
 * Created By bhx On 2019/9/5 0005 09:08
 */
class AttestationSuccessDialog : BaseDialogFragment() {
    private var mCallBack: (() -> Unit)? = null
    override fun getLayoutId(): Int = R.layout.dialog_attestation_success
    override fun initView(view: View?) {
        tvSubmitConfirm.clickWithTrigger {
            mCallBack?.invoke()
            dismissAllowingStateLoss()
        }
    }

    override fun initWindow() {
        if (dialog != null && context != null) {
            val window = dialog.window
            if (window != null) {
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val params = window.attributes
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.gravity = Gravity.BOTTOM
                window.attributes = params
            }
        }
    }

    companion object {
        private val TAG = AttestationSuccessDialog::class.simpleName

        fun show(activity: FragmentActivity, callBack: () -> Unit) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                val dialog = AttestationSuccessDialog()
                dialog.mCallBack = callBack
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
            val fragment = activity?.supportFragmentManager?.findFragmentByTag(TAG) as AttestationSuccessDialog
            if (fragment.isAdded) {
                fragment.dismissAllowingStateLoss()
            }
        }
    }
}