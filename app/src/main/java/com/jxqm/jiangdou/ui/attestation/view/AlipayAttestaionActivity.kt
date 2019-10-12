package com.jxqm.jiangdou.ui.attestation.view

import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.attestation.vm.AlipayAttestationViewModel

/**
 * 绑定支付宝 - 认证
 */
class AlipayAttestaionActivity : BaseDataActivity<AlipayAttestationViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_alipay_attestation_layout

    override fun getEventKey(): Any = Constants.EVENT_KEY_ALIPAY_ATTESTATION

    override fun initView() {
        super.initView()
    }

    override fun dataObserver() {

    }

}