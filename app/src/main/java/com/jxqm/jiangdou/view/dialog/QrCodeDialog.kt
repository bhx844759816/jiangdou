package com.jxqm.jiangdou.view.dialog

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.bhx.common.base.BaseDialogFragment
import com.bhx.common.utils.LogUtils
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.ImageUtil
import kotlinx.android.synthetic.main.dialog_qr_code_layout.*
import java.io.ByteArrayInputStream

/**
 * 二维码
 * Created by Administrator on 2019/10/9.
 */
class QrCodeDialog : BaseDialogFragment() {
    override fun getLayoutId(): Int = R.layout.dialog_qr_code_layout
    override fun initView(view: View?) {
        super.initView(view)
        val base64ImageString = arguments?.getString("Base64ImageString")
        base64ImageString?.let {
            val base64String = it.substring(it.indexOf(",")+1)
            val bitmap =  ImageUtil.base64ToBitmap(base64String)
            bitmap?.let {bitmap_->
                ivQrCode.setImageBitmap(bitmap_)
            }
        }
    }

    companion object {
        private val TAG = QrCodeDialog::class.simpleName

        fun show(activity: FragmentActivity,base64ImageString:String) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                val dialog = QrCodeDialog()
                val bundle = Bundle()
                bundle.putString("Base64ImageString", base64ImageString)
                dialog.arguments = bundle
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
            val dialog = activity?.supportFragmentManager?.findFragmentByTag(TAG)
            dialog?.let {
                val fragment = dialog as QrCodeDialog
                if (fragment.isAdded) {
                    fragment.dismissAllowingStateLoss()
                }
            }
        }
    }
}