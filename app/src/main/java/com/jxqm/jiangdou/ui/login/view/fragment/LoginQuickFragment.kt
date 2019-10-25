package com.jxqm.jiangdou.ui.login.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bhx.common.mvvm.BaseMVVMFragment
import com.bhx.common.utils.DeviceUtils
import com.bhx.common.utils.RegularUtils
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ui.login.view.VerifyCodeActivity
import com.jxqm.jiangdou.ui.login.vm.LoginQuickViewModel
import com.jxqm.jiangdou.ui.web.AgreementWebActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.fragment_login_quick.*

/**
 * 手机快捷登录
 */
class LoginQuickFragment : BaseMVVMFragment<LoginQuickViewModel>() {
    private var mDeviceId = ""
    override fun getLayoutId(): Int = R.layout.fragment_login_quick

    override fun getEventKey(): Any = Constants.EVENT_KEY_LOGIN_QUICK

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etInputPhone.addTextChangedListener {
            afterTextChanged {
                changeNextStepStatus()
            }
        }
        tvUserAgreement.clickWithTrigger {
            mContext.startActivity<AgreementWebActivity>("Status" to AgreementWebActivity.TAG_USER_AGREEMENT)
        }
        cbProtocol.setOnCheckedChangeListener { _, _ ->
            changeNextStepStatus()
        }
        tvLogin.clickWithTrigger {
            val phone = etInputPhone.text.toString().trim()
            if (phone.isEmpty()) {
                ToastUtils.toastShort("请输入手机号")
                return@clickWithTrigger
            }
            if (!RegularUtils.isTelPhoneNumber(phone)) {
                ToastUtils.toastShort("请输入正确的手机号")
                return@clickWithTrigger
            }
            if (!cbProtocol.isChecked) {
                ToastUtils.toastShort("请同意用户协议")
                return@clickWithTrigger
            }
            mDeviceId = DeviceUtils.getDeviceId(mContext)
            mViewModel.sendSmsCode(mDeviceId, phone)
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        registerObserver(Constants.TAG_LOGIN_QUICK_CODE_SUCCESS, Boolean::class.java).observe(this,
            Observer {
                if (it) {
                    val phone = etInputPhone.text.toString().trim()
                    mContext.startActivity<VerifyCodeActivity>(
                        "phone" to phone,
                        "deviceId" to mDeviceId
                    )
                }
            })


    }

    /**
     * 改变下一步的状态
     */
    private fun changeNextStepStatus() {
        val phone = etInputPhone.text.toString().trim()
        val isEnable = RegularUtils.isTelPhoneNumber(phone) && cbProtocol.isChecked
        if (isEnable) {
            tvLogin.setBackgroundResource(R.drawable.shape_button_select)
        } else {
            tvLogin.setBackgroundResource(R.drawable.shape_button_default)
        }
    }
}