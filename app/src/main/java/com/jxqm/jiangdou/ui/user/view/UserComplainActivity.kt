package com.jxqm.jiangdou.ui.user.view

import android.os.Bundle
import android.view.View
import com.bhx.common.base.BaseActivity
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_user_complain.*

/**
 * 用户投诉
 * Created By bhx On 2019/8/20 0020 11:11
 */
class UserComplainActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_user_complain

    override fun initView() {
        super.initView()
        llComplainChargeType.clickWithTrigger {
            onViewChick(it)
        }
        llComplainOtherType.clickWithTrigger {
            onViewChick(it)
        }
        llComplainMoneyType.clickWithTrigger {
            onViewChick(it)
        }
        llComplainFalseMessageType.clickWithTrigger { onViewChick(it) }
        llComplainTimeOutType.clickWithTrigger { onViewChick(it) }
    }

    private fun onViewChick(view: View) {
        var type = when (view.id) {
            R.id.llComplainChargeType -> 0 //收取费用
            R.id.llComplainMoneyType -> 1//工资拖欠
            R.id.llComplainTimeOutType -> 2//放鸽子
            R.id.llComplainFalseMessageType -> 3//信息虚假
            R.id.llComplainOtherType -> 4//其他投诉
            else -> -1
        }
        val bundle = Bundle()
        bundle.putInt("ComplainType", type)
        startActivity<ComplainDetailsActivity>(bundle)
    }
}