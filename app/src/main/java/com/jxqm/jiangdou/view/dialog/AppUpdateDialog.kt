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
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.dialog_app_update.*

/**
 * App更新的Dialog
 *
 * */
class AppUpdateDialog : BaseDialogFragment() {

    var mAppVersion: String = ""
    var mApkSize: String = ""
    var mUpdateMessage: String = ""
    var mConfirmCallBack: (() -> Unit)? = null
    override fun getLayoutId(): Int = R.layout.dialog_app_update

    override fun initView(view: View?) {
        super.initView(view)
        setIsCanceledOnTouchOutside(false)
        tvTitle.text = "是否升级到${mAppVersion}版本?"
        tvApkSize.text = "新版本大小：${mApkSize}"
        tvContent.text = mUpdateMessage

        ivCancel.clickWithTrigger {
            dismissAllowingStateLoss()
        }

        tvUpdate.clickWithTrigger {
            mConfirmCallBack?.invoke()
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
                params.height = WindowManager.LayoutParams.MATCH_PARENT
                params.gravity = Gravity.CENTER
                window.attributes = params
            }
        }
    }
    companion object {
        private val TAG = AppUpdateDialog::class.simpleName

        fun show(
            activity: FragmentActivity, appVersion: String,
            apkSize: String,
            updateMessage: String,
            callBack: (() -> Unit)
        ) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                val dialog = AppUpdateDialog()
                dialog.mAppVersion = appVersion
                dialog.mApkSize = apkSize
                dialog.mConfirmCallBack = callBack
                dialog.mUpdateMessage = updateMessage
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
            val fragment =
                activity?.supportFragmentManager?.findFragmentByTag(TAG) as AppUpdateDialog
            if (fragment.isAdded) {
                fragment.dismissAllowingStateLoss()
            }
        }
    }
}