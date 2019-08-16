package com.jxqm.jiangdou.ui.login.view

import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.login.vm.PhoneLoginViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_phone_login.*
import android.text.Selection
import android.text.Spannable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.utils.click
import kotlinx.android.synthetic.main.activity_forget_psd.*
import kotlinx.android.synthetic.main.activity_phone_login.etInputPhone
import kotlinx.android.synthetic.main.activity_phone_login.etPassword


/**
 * 手机号和密码登录
 * Created By bhx On 2019/8/6 0006 09:32
 */
class PhoneLoginActivity : BaseDataActivity<PhoneLoginViewModel>() {

    var isEyeOpen = false
    private var isPhoneInput = false
    private var isPasswordInput = false


    override fun getEventKey(): Any = Constants.EVENT_KEY_PHONE_LOGIN

    override fun getLayoutId(): Int = R.layout.activity_phone_login

    override fun initView() {
        super.initView()
        ivClose.click {
            finish()
        }
        tvPhoneLogin.clickWithTrigger {
            finish()
        }
        flEyeParent.clickWithTrigger {
            isEyeOpen = !isEyeOpen
            if (isEyeOpen) {
                ivEye.setImageResource(R.drawable.icon_eye_open)
            } else {
                ivEye.setImageResource(R.drawable.icon_eye_close)
            }
            checkPasswordShowState()
        }
        tvForgetPsd.clickWithTrigger {
            startActivity<ForgetPsdActivity>()
        }
        tvRegister.clickWithTrigger {
            startActivity<RegisterActivity>()
        }
        etInputPhone.addTextChangedListener {
            afterTextChanged {
                val isEmpty = it?.toString()?.isEmpty()
                isPasswordInput = isEmpty == null || isEmpty
                changeBtnState()
            }
        }
        etPassword.addTextChangedListener {
            afterTextChanged {
                val isEmpty = it?.toString()?.isEmpty()
                isPasswordInput = isEmpty == null || isEmpty
                changeBtnState()
            }
        }
        tvLogin.clickWithTrigger {
            ToastUtils.toastShort("登录成功")
        }
    }

    private fun changeBtnState() {
        if (isPhoneInput && isPasswordInput) {
            tvLogin.isEnabled = true
            tvLogin.setBackgroundResource(R.drawable.icon_shadow_btn)
        } else {
            tvLogin.isEnabled = false
            tvLogin.setBackgroundResource(R.drawable.shape_button_default)
        }
    }

    private fun checkPasswordShowState() {
        val method = etPassword.transformationMethod
        if (method === HideReturnsTransformationMethod.getInstance()) {
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
        // 保证切换后光标位于文本末尾
        val spanText = etPassword.text
        if (spanText != null) {
            Selection.setSelection(spanText, spanText.length)
        }
    }
}