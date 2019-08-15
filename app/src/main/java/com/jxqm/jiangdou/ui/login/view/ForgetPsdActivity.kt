package com.jxqm.jiangdou.ui.login.view

import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ui.login.vm.ForgetPsdViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_forget_psd.*

/**
 * 忘记密码界面
 * Created By bhx On 2019/8/6 0006 09:32
 */
class ForgetPsdActivity : BaseDataActivity<ForgetPsdViewModel>() {
    private var isPhoneInput = false
    private var isCodeInput = false
    private var isPasswordInput = false
    override fun getLayoutId(): Int = R.layout.activity_forget_psd

    override fun getEventKey(): Any = Constants.EVENT_KEY_FORGET_PSD

    override fun initView() {
        super.initView()
        flBack.clickWithTrigger { finish() }
        etInputPhone.addTextChangedListener {
            afterTextChanged {
                val isEmpty = it?.toString()?.isEmpty()
                isPhoneInput = !(isEmpty == null || isEmpty)
                if (isPhoneInput) {
                    tvGetCode.isEnabled = true
                    tvGetCode.setBackgroundResource(R.drawable.shape_get_verify_code_light_bg)
                } else {
                    tvGetCode.isEnabled = false
                    tvGetCode.setBackgroundResource(R.drawable.shape_get_verify_code_bg)
                }
                changeBtnState()
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
            ToastUtils.toastShort("提交成功")
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