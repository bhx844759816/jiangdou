package com.jxqm.jiangdou.ui.login.view

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import com.bhx.common.base.BaseLazyFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.employee.view.EmployeeWorkListFragment
import kotlinx.android.synthetic.main.fragment_guide.*

/**
 * 引导页的Fragment
 * Created By bhx On 2019/9/5 0005 15:59
 */
class GuideFragment : BaseLazyFragment() {
    private val mBgRes =
        arrayListOf(R.drawable.icon_guide_bg_01, R.drawable.icon_guide_bg_02, R.drawable.icon_guide_bg_03)
    private val mTitles = arrayListOf("日结工作，现劳现结", "优质企业，层层审核", "薪资担保，自动结算")
    private val mDescriptions = arrayListOf("海量优质日结工作任你选", "实名企业认证线上线下严格审核", "企业预付薪资押金，免除拖欠顾虑")
    private var mType = 0
    override fun getLayoutId(): Int = R.layout.fragment_guide

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mType = arguments?.getInt("type")!!
        tvGuideTitle.text = mTitles[mType]
        val spannableString = SpannableString(mDescriptions[mType])
        val colorAccent = ForegroundColorSpan(resources.getColor(R.color.colorAccent))
        spannableString.setSpan(colorAccent, 2, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvGuideDescription.text = spannableString


    }

    companion object {
        fun newInstance(type: Int): GuideFragment {
            val fragment = GuideFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }
}