package com.jxqm.jiangdou.view.dialog

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.bhx.common.base.BaseDialogFragment
import com.bhx.common.http.RxHelper
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.dialog_my_service.*

/**
 * 客服Dialog
 * Created by Administrator on 2019/9/4.
 */
class MyServiceDialog : BaseDialogFragment() {
    override fun getLayoutId(): Int = R.layout.dialog_my_service

    override fun initView(view: View?) {
        ivCallPhone.clickWithTrigger {
            callPhone()
        }
        icCopyWx.clickWithTrigger {
            copyClip()
            ToastUtils.toastShort("复制成功")
        }
    }

    private fun callPhone() {
        activity?.let {
            RxPermissions(it).request(Manifest.permission.CALL_PHONE)
                .compose(RxHelper.io_main())
                .subscribe { result ->
                    if (result) {
                        val intent = Intent()
                        intent.action = Intent.ACTION_DIAL
                        intent.data = Uri.parse("tel:400-669-6067")
                        mContext.startActivity(intent)
                    }
                }
        }
    }

    private fun copyClip() {
        val clipManager = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Label", "J16696667881")
        clipManager.setPrimaryClip(clipData)
    }

    override fun initWindow() {
        if (dialog != null && context != null) {
            val window = dialog.window
            if (window != null) {
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window.decorView.setPadding(DensityUtil.dip2px(context, 30f), 0, DensityUtil.dip2px(context, 30f), 0)
                val params = window.attributes
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = DensityUtil.dip2px(mContext, 230f)
                params.gravity = Gravity.CENTER
                window.attributes = params
            }
        }
    }

    companion object {
        private val TAG = MyServiceDialog::class.simpleName

        fun show(activity: FragmentActivity) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                fragment = MyServiceDialog()
            }
            if (!fragment.isAdded) {
                val manager = activity.supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.add(fragment, TAG)
                transaction.commitAllowingStateLoss()
            }
        }

        fun dismiss(activity: FragmentActivity?) {
            val fragment = activity?.supportFragmentManager?.findFragmentByTag(TAG) as MyServiceDialog
            if (fragment.isAdded) {
                fragment.dismissAllowingStateLoss()
            }
        }
    }
}