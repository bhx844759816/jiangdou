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
import kotlinx.android.synthetic.main.dialog_settle_layout.*

/**
 * Created by Administrator on 2019/10/10.
 */
class SettleDialog : BaseDialogFragment() {

    private var mConfirmCallback: ((String) -> Unit)? = null
    override fun getLayoutId(): Int = R.layout.dialog_settle_layout


    override fun initView(view: View?) {
        cancel.clickWithTrigger {
            dismissAllowingStateLoss()
        }
        confirm.clickWithTrigger {
            mConfirmCallback?.invoke(etInputAmount.text.toString().trim())
            dismissAllowingStateLoss()
        }
        //获取最多结算金额
        val amount = arguments?.getString("amount")
        amount?.let {
            etInputAmount.hint = "最多$it 豆币"
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
                params.height = DensityUtil.dip2px(mContext, 200f)
                params.gravity = Gravity.CENTER
                window.attributes = params
            }
        }
    }

    companion object {
        private val TAG = PromptDialog::class.simpleName

        fun show(activity: FragmentActivity, amount: String, callBack: ((String) -> Unit)) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                val dialog = SettleDialog()
                val bundle = Bundle()
                bundle.putString("amount", amount)
                dialog.arguments = bundle
                dialog.mConfirmCallback = callBack
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
            val fragment = activity?.supportFragmentManager?.findFragmentByTag(TAG) as SettleDialog
            if (fragment.isAdded) {
                fragment.dismissAllowingStateLoss()
            }
        }
    }
}