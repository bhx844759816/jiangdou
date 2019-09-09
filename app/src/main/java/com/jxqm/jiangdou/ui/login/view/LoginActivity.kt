package com.jxqm.jiangdou.ui.login.view

import android.os.Bundle
import androidx.lifecycle.Observer
import com.bhx.common.utils.DeviceUtils
import com.bhx.common.utils.PhoneUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ext.isEnable
import com.jxqm.jiangdou.ext.showSoftInput
import com.jxqm.jiangdou.ui.attestation.view.PeopleAttestationActivity
import com.jxqm.jiangdou.ui.login.vm.LoginViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_login.*
import com.jxqm.jiangdou.ui.publish.view.JobPublishActivity
import com.jxqm.jiangdou.view.dialog.LoadingDialog


/**
 * 登录界面
 * Created By bhx On 2019/8/6 0006 09:32
 */
class LoginActivity : BaseDataActivity<LoginViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_login

    override fun getEventKey(): Any = Constants.EVENT_KEY_LOGIN

    override fun initView() {
        super.initView()
        tvLogin.isClickable = false
        myLoginBack.clickWithTrigger {
            finish()
        }
        tvAccountLogin.clickWithTrigger {
            startActivity<PhoneLoginActivity>()
        }
        tvLogin.clickWithTrigger {
            val phone = etInputPhone.text.toString().trim()
            val deviceId = DeviceUtils.getDeviceId(this)
            mViewModel.sendSmsCode(deviceId, phone)
        }
        tvLogin.isEnable(etInputPhone) {
            val phone = etInputPhone.text.toString().trim()
            PhoneUtils.isMobile(phone)
        }
        etInputPhone.showSoftInput()
    }

    override fun dataObserver() {
        //注册发送验证码结果的接受者
        registerObserver(Constants.TAG_LOGIN_CODE_SUCCESS, Boolean::class.java).observe(this, Observer {
            if (it) {
                val bundle = Bundle()
                val phone = etInputPhone.text.toString().trim()
                bundle.putString("phone", phone)
                bundle.putString("deviceId", "101")
                startActivity<VerifyCodeActivity>(bundle)
            }
        })
    }


}