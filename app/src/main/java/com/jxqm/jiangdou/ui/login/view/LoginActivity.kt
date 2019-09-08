package com.jxqm.jiangdou.ui.login.view

import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
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
            mViewModel.sendSmsCode("100", phone)
//            startActivity<VerifyCodeActivity>()
        }

        etInputPhone.addTextChangedListener {
            afterTextChanged {
                val isEmpty = it?.toString()?.isEmpty()
                if (isEmpty == null || isEmpty) {
                    tvLogin.setBackgroundResource(R.drawable.shape_button_default)
                    tvLogin.isEnabled = false
                } else {
                    tvLogin.setBackgroundResource(R.drawable.shape_button_select)
                    tvLogin.isEnabled = true
                }
            }
        }
    }


}