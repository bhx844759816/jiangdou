package com.jxqm.jiangdou.view.dialog

import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.bhx.common.base.BaseDialogFragment
import com.bhx.common.utils.DensityUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.adapter.ArrayWheelAdapter
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.dialog_single_select.*

/**
 * 单列表选择器
 * Created By bhx On 2019/8/19 0019 11:09
 */
class SingleSelectDialog : BaseDialogFragment() {

    private var mLabel: String = ""
    private val mItems = mutableListOf<String>()
    private var mConfirmCallback: ((Int) -> Unit)? = null
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
        tvCancel.clickWithTrigger {
            dismissAllowingStateLoss()
        }
        tvConfirm.clickWithTrigger {
            val index = wheelView.currentItem
            mConfirmCallback?.invoke(index)
            dismissAllowingStateLoss()
        }
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

        fun  show(activity: FragmentActivity, list: List<String>, lable: String = "",callBack: ((Int) -> Unit)) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                val fragment_ = SingleSelectDialog()
                fragment_.mConfirmCallback = callBack
                fragment = fragment_
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
    }


}