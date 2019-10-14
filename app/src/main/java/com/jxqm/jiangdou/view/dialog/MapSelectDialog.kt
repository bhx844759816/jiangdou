package com.jxqm.jiangdou.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.bhx.common.base.BaseDialogFragment
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.MapUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.dialog_map_select_layout.*

class MapSelectDialog : BaseDialogFragment() {
    var lat: String = ""
    var lon: String = ""
    var address:String = ""
    override fun getLayoutId(): Int = R.layout.dialog_map_select_layout

    override fun initView(view: View?) {
        super.initView(view)
        tvBaiduMap.clickWithTrigger {
            val result = MapUtils.goToBaidu(mContext, lat, lon,address)
            if (!result) {
                ToastUtils.toastShort("未检测到百度客户端")
            }
        }
        tvGaodeMap.clickWithTrigger {
            val result = MapUtils.goToGaode(mContext, lat, lon,address)
            if (!result) {
                ToastUtils.toastShort("未检测到高德度客户端")
            }
        }
    }

    override fun initWindow() {
        if (dialog != null && context != null) {
            val window = dialog.window
            if (window != null) {
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val params = window.attributes
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = DensityUtil.dip2px(mContext, 160f)
                params.gravity = Gravity.BOTTOM
                window.attributes = params
            }
        }
    }


    companion object {
        private val TAG = MapSelectDialog::class.simpleName

        fun show(activity: FragmentActivity, lat: String, lon: String,address:String) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                val dialog = MapSelectDialog()
                dialog.lat = lat
                dialog.lon = lon
                dialog.address = address
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
                activity?.supportFragmentManager?.findFragmentByTag(TAG) as MapSelectDialog
            if (fragment.isAdded) {
                fragment.dismissAllowingStateLoss()
            }
        }
    }
}