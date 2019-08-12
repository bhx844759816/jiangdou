package com.jxqm.jiangdou.view.dialog

import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.bhx.common.base.BaseDialogFragment
import com.bhx.common.event.LiveBus
import com.bhx.common.utils.DensityUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.dialog_user_agreement.*

/**
 * 展示登录的时候的用户协议Dialog
 * Created By bhx On 2019/8/6 0006 15:48
 */
class UserAgreementDialog : BaseDialogFragment() {


    override fun getLayoutId(): Int = R.layout.dialog_user_agreement

    override fun initView(view: View?) {
        super.initView(view)
        tvBack.clickWithTrigger {
            dismissAllowingStateLoss()
        }
        tvContinue.clickWithTrigger {
            LiveBus.getDefault().postEvent(Constants.EVENT_KEY_VERIFY_CODE, Constants.TAG_AGREE_CONTINUE, true)
            dismissAllowingStateLoss()
        }

    }

    override fun initWindow() {
        if (dialog != null && context != null) {
            val window = dialog.window
            if (window != null) {
                window.decorView.setPadding(0, 0, 0, 0)
                val params = window.attributes
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = DensityUtil.dip2px(context, 200f)
                params.gravity = Gravity.BOTTOM
                window.attributes = params
            }
        }
    }

    companion object {
        private val TAG = UserAgreementDialog::class.simpleName

        fun show(activity: FragmentActivity) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                fragment = UserAgreementDialog()
            }
            if (!fragment.isAdded) {
                val manager = activity.supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.add(fragment, TAG)
                transaction.commitAllowingStateLoss()
            }
        }

        fun dismiss(activity: FragmentActivity?) {
            val fragment = activity?.supportFragmentManager?.findFragmentByTag(TAG) as UserAgreementDialog
            if (fragment.isAdded) {
                fragment.dismissAllowingStateLoss()
            }
        }
    }
}