package com.jxqm.jiangdou.ui.attestation.view

import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_company_attestation.*

/**
 * 企业认证
 * Created By bhx On 2019/9/4 0004 18:15
 */
class CompanyAttestationActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_company_attestation
    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        tvNextStep.clickWithTrigger {
            startActivity<PeopleAttestationActivity>()
        }

        companyAttestationBack.clickWithTrigger {
            finish()
        }
    }
}