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
import com.jxqm.jiangdou.adapter.ArrayWheelAdapter
import kotlinx.android.synthetic.main.dialog_single_select.*

/**
 * 单列表选择器
 * Created By bhx On 2019/8/19 0019 11:09
 */
class SingleSelectDialog : BaseDialogFragment() {

    private val mItems = mutableListOf<String>()
    private var mLabel: String = ""

    override fun getLayoutId(): Int = R.layout.dialog_single_select

    override fun initView(view: View?) {
        super.initView(view)
        wheelView.adapter = ArrayWheelAdapter(mItems)
        wheelView.setIsOptions(true)
        if (mLabel.isNotEmpty()) {
            wheelView.isCenterLabel(false)
            wheelView.setLabel(mLabel)
        }
        wheelView.setCyclic(false)
    }

    fun setData(list: List<String>) {
        mItems.clear()
        mItems.addAll(list)
    }

    override fun initWindow() {
        if (dialog != null && context != null) {
            val window = dialog.window
            if (window != null) {
                val params = window.attributes
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = DensityUtil.dip2px(context, 200f)
                params.gravity = Gravity.BOTTOM
                window.attributes = params
            }
        }
    }

    companion object {
        private val TAG = SingleSelectDialog::class.simpleName

        fun show(activity: FragmentActivity, list: List<String>, lable: String = "") {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                fragment = SingleSelectDialog()
            }
            if (!fragment.isAdded) {
                (fragment as SingleSelectDialog).setData(list)
                fragment.mLabel = lable
                val manager = activity.supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.add(fragment, TAG)
                transaction.commitAllowingStateLoss()
            }
        }

        fun dismiss(activity: FragmentActivity?) {
            val fragment = activity?.supportFragmentManager?.findFragmentByTag(TAG) as SingleSelectDialog
            if (fragment.isAdded) {
                fragment.dismissAllowingStateLoss()
            }
        }
    }

}