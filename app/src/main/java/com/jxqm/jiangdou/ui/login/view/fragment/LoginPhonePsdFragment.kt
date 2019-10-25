package com.jxqm.jiangdou.ui.login.view.fragment

import android.os.Bundle
import android.text.Selection
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.lifecycle.Observer
import com.bhx.common.event.LiveBus
import com.bhx.common.mvvm.BaseMVVMFragment
import com.bhx.common.utils.AppManager
import com.bhx.common.utils.DeviceUtils
import com.bhx.common.utils.RegularUtils
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.model.UserModel
import com.jxqm.jiangdou.ui.home.view.MainActivity
import com.jxqm.jiangdou.ui.login.view.ForgetPsdActivity
import com.jxqm.jiangdou.ui.login.vm.LoginPhonePsdViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.fragment_login_phone_psd.*

/**
 *  手机号密码登录界面
 */
class LoginPhonePsdFragment : BaseMVVMFragment<LoginPhonePsdViewModel>() {
    private var isEyeOpen = false
    override fun getLayoutId(): Int = R.layout.fragment_login_phone_psd

    override fun getEventKey(): Any = Constants.EVENT_KEY_LOGIN_PHONE_PSD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        etInputPhone.addTextChangedListener {
            afterTextChanged {
                changeLoginState()
            }
        }
        etPassword.addTextChangedListener {
            afterTextChanged {
                changeLoginState()
            }
        }
        tvLogin.clickWithTrigger {
            val phone = etInputPhone.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (phone.isEmpty()) {
                ToastUtils.toastShort("手机号不能为空")
                return@clickWithTrigger
            }
            if (!RegularUtils.isTelPhoneNumber(phone)) {
                ToastUtils.toastShort("请输入正确的手机号")
                return@clickWithTrigger
            }
            if (password.isEmpty()) {
                ToastUtils.toastShort("密码不能为空")
                return@clickWithTrigger
            }
            val deviceId = DeviceUtils.getDeviceId(mContext)
            mViewModel.doLogin(phone, password, deviceId)
        }
    }

    /**
     * 改变登录状态
     */
    private fun changeLoginState() {
        val phone = etInputPhone.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val isEnable = RegularUtils.isTelPhoneNumber(phone) && password.isNotEmpty()
        if (isEnable) {
            tvLogin.setBackgroundResource(R.drawable.shape_button_select)
        } else {
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

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        registerObserver(Constants.TAG_LOGIN_PHONE_PSD_LOGIN_SUCCESS, UserModel::class.java).observe(this, Observer {
            //登录成功
            LiveBus.getDefault().postEvent(Constants.EVENT_KEY_MAIN_MY, Constants.TAG_MAIN_MY_LOGIN_SUCCESS, true)
            //发送登录成功得消息
            activity?.finish()
        })
    }
}