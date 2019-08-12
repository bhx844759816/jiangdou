package com.jxqm.jiangdou.ui.publish.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import com.bhx.common.mvvm.BaseMVVMFragment
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.LogUtils
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.listener.OnJobPublishCallBack
import com.jxqm.jiangdou.ui.publish.vm.SelectJobTypeViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.MultipleRadioGroup
import kotlinx.android.synthetic.main.fragment_select_job_type.*

/**
 * 选择界面类型
 * Created By bhx On 2019/8/8 0008 09:09
 */
class JobTypeFragment : BaseMVVMFragment<SelectJobTypeViewModel>() {

    private val hotTypes = arrayListOf("类型2", "类型3", "类型4")
    private val hotTypeIdS = arrayListOf(R.id.radioGroup_item_01, R.id.radioGroup_item_02, R.id.radioGroup_item_03)
    private val hotType2s = arrayListOf("类型5", "类型6", "类型7")
    private val hotType2IdS = arrayListOf(R.id.radioGroup_item_04, R.id.radioGroup_item_05, R.id.radioGroup_item_06)
    private var llMoreTypeTwo: LinearLayout? = null
    private var llMoreTypeThree: LinearLayout? = null
    private var mCallback: OnJobPublishCallBack? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnJobPublishCallBack) {
            mCallback = context
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_select_job_type

    override fun getEventKey(): Any = Constants.EVENT_KEY_SELECT_JOB_TYPE

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)
        rgHotTypeGroup.setOnCheckedChangeListener { group, checkedId ->
            LogUtils.i("rgMoreTypeGroup group$group,checkedId$checkedId")
            rgMoreTypeGroup.clearCheck()
        }

        rgMoreTypeGroup.setOnCheckedChangeListener { group, checkedId ->
            LogUtils.i("rgMoreTypeGroup group$group,checkedId$checkedId")
            rgHotTypeGroup.clearCheck()
        }
        //点击下一步
        tvNextStep.clickWithTrigger {
            mCallback?.jobTypNextStep()
        }

        flExpend.clickWithTrigger {
            ToastUtils.toastShort("展开")
            fillViewGroup()
        }
    }

    /**
     * 填冲布局
     */
    private fun fillViewGroup() {
        llMoreTypeOne.removeView(flExpend)
        llMoreTypeOne.addView(getItemView("类型1",R.id.radioGroup_item_07))
        for (index in 0 until hotTypes.size) {
            if (llMoreTypeTwo == null) {
                llMoreTypeTwo = LinearLayout(context)
            }
            llMoreTypeTwo!!.addView(getItemView(hotTypes[index], hotTypeIdS[index]))
        }
        val params = MultipleRadioGroup.LayoutParams(
                ViewGroup.MarginLayoutParams.MATCH_PARENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
        params.topMargin = DensityUtil.dip2px(context, 11f)
        rgMoreTypeGroup.addView(llMoreTypeTwo, params)

        for (index in 0 until hotType2s.size) {
            if (llMoreTypeThree == null) {
                llMoreTypeThree = LinearLayout(context)
            }
            llMoreTypeThree!!.addView(getItemView(hotType2s[index], hotType2IdS[index]))
        }

        val params2 = MultipleRadioGroup.LayoutParams(
                ViewGroup.MarginLayoutParams.MATCH_PARENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
        params2.topMargin = DensityUtil.dip2px(context, 11f)
        rgMoreTypeGroup.addView(llMoreTypeThree, params2)

    }

    fun getItemView(text: String, id: Int): View {
        val child = LayoutInflater.from(context).inflate(
                R.layout.view_job_publish_radiogroup,
                null
        ) as RadioButton
        val params = LinearLayout.LayoutParams(0, DensityUtil.dip2px(context, 36f))
        params.weight = 1f
        params.rightMargin = DensityUtil.dip2px(context, 11f)
        child.text = text
        child.id = id
        child.layoutParams = params
        return child
    }


}