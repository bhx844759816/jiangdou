package com.jxqm.jiangdou.ui.login.view

import android.text.Editable
import android.text.TextWatcher
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ui.login.vm.RegisterViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_forget_psd.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.etCode
import kotlinx.android.synthetic.main.activity_register.etInputPhone
import kotlinx.android.synthetic.main.activity_register.etPassword
import kotlinx.android.synthetic.main.activity_register.flBack
import kotlinx.android.synthetic.main.activity_register.tvGetCode
import kotlinx.android.synthetic.main.activity_register.tvSubmit

/**
 * 注册界面
 * Created By bhx On 2019/8/6 0006 09:32
 */
class RegisterActivity : BaseDataActivity<RegisterViewModel>() {

    private var isPhoneInput = false
    private var isCodeInput = false
    private var isPasswordInput = false


    override fun getLayoutId(): Int = R.layout.activity_register

    override fun getEventKey(): Any = Constants.EVENT_KEY_REGISTER

    override fun initView() {
        super.initView()

        flBack.clickWithTrigger {
            finish()
        }

        etInputPhone.addTextChangedListener {
            afterTextChanged {
                val isEmpty = it?.toString()?.isEmpty()
                isPhoneInput = !(isEmpty == null || isEmpty)
                if (isEmpty == null || isEmpty) {
                    tvGetCode.setBackgroundResource(R.drawable.shape_get_verify_code_bg)
                    tvGetCode.isEnabled = false
                } else {
                    tvGetCode.setBackgroundResource(R.drawable.shape_get_verify_code_light_bg)
                    tvGetCode.isEnabled = true
                }
            }
        }

        etCode.addTextChangedListener {
            afterTextChanged {
                val isEmpty = it?.toString()?.isEmpty()
                isCodeInput = !(isEmpty == null || isEmpty)
                changeBtnState()
            }
        }

        etPassword.addTextChangedListener {
            afterTextChanged {
                val isEmpty = it?.toString()?.isEmpty()
                isPasswordInput = !(isEmpty == null || isEmpty)
                changeBtnState()
            }
        }

        tvSubmit.clickWithTrigger {
            ToastUtils.toastShort("注册成功")
        }
    }
    private fun changeBtnState() {
        if (isPhoneInput && isPasswordInput && isCodeInput) {
            tvSubmit.isEnabled = true
            tvSubmit.setBackgroundResource(R.drawable.icon_shadow_btn)
        } else {
            tvSubmit.isEnabled = false
            tvSubmit.setBackgroundResource(R.drawable.shape_button_default)
        }
    }
}