package com.jxqm.jiangdou.ui.user.view

import androidx.fragment.app.Fragment
import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_complain_details.*

/**
 * 投诉详情界面
 * Created By bhx On 2019/8/20 0020 14:51
 */
class ComplainDetailsActivity : BaseActivity() {
    private lateinit var mFragment: Fragment
    override fun getLayoutId(): Int = R.layout.activity_complain_details

    override fun initView() {
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        when (intent.getIntExtra("ComplainType", -1)) {
            0 -> {
                mFragment = ComplainChargeTypeFragment()
                tvTitle.text = "收取费用投诉"
            }
            1 -> {
                mFragment = ComplainMoneyTypeFragment()
                tvTitle.text = "工资拖欠投诉"
            }
            2 -> {
                mFragment = ComplainTimeoutTypeFragment()
                tvTitle.text = "放鸽子投诉"
            }
            3 -> {
                mFragment = ComplainFalseMessageTypeFragment()
                tvTitle.text = "信息虚假投诉"
            }
            4 -> {
                mFragment = ComplainOthersTypeFragment()
                tvTitle.text = "其他投诉"
            }
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.flFragmentParent, mFragment)
        transaction.commit()
        complainDetailsBack.clickWithTrigger {
            finish()
        }

    }
}