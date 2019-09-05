package com.jxqm.jiangdou.ui.attestation.view

import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.attestation.vm.PeopleAttestationViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.dialog.AttestationSuccessDialog
import kotlinx.android.synthetic.main.activity_people_attestation.*

/**
 * 身份认证
 * Created By bhx On 2019/8/9 0009 11:58
 */
class PeopleAttestationActivity : BaseDataActivity<PeopleAttestationViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_people_attestation
    override fun getEventKey(): Any = Constants.EVENT_KEY_PEOPLE_ATTESTATION

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        tvSubmit.clickWithTrigger {
            AttestationSuccessDialog.show(this)
        }

        peopleAttestationBack.clickWithTrigger {
            finish()
        }
    }
}