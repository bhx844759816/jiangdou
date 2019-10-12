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
import kotlinx.android.synthetic.main.dialog_select_age_star_layout.*

/**
 * 选择星座和年龄得Dialog
 * Created by Administrator on 2019/10/10.
 */
class SelectAgeAndStarDialog : BaseDialogFragment() {
    private val mAgeList = mutableListOf<String>()
    private val mStarList = arrayListOf(
        "白羊座",
        "金牛座",
        "双子座",
        "巨蟹座",
        "狮子座",
        "处女座",
        "天秤座",
        "天蝎座",
        "射手座",
        "摩羯座",
        "水瓶座",
        "双鱼座"
    )
    private var mConfirmCallback: ((String, String) -> Unit)? = null
    override fun getLayoutId(): Int = R.layout.dialog_select_age_star_layout

    override fun initView(view: View?) {
        super.initView(view)
        mAgeList.addAll((0 until 100).map {
            "$it 岁"
        })
        wvStar.adapter = ArrayWheelAdapter(mStarList)
        wvStar.setIsOptions(true)

        wvAge.adapter = ArrayWheelAdapter(mAgeList)
        wvAge.setIsOptions(true)

        tvCancel.clickWithTrigger {
            dismissAllowingStateLoss()
        }
        tvConfirm.clickWithTrigger {
            val ageIndex = wvAge.currentItem
            val starIndex = wvStar.currentItem
            mConfirmCallback?.invoke(mAgeList[ageIndex],mStarList[starIndex])
            dismissAllowingStateLoss()
        }
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
        private val TAG = SelectAgeAndStarDialog::class.simpleName

        fun show(activity: FragmentActivity, callBack: ((String,String) -> Unit)) {
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                val fragment_ = SelectAgeAndStarDialog()
                fragment_.mConfirmCallback = callBack
                fragment = fragment_
            }
            if (!fragment.isAdded) {
                val manager = activity.supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.add(fragment, TAG)
                transaction.commitAllowingStateLoss()
            }
        }
    }
}