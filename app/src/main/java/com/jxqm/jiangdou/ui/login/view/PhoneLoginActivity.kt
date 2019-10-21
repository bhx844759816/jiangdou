package com.jxqm.jiangdou.ui.login.view

import android.app.Activity
import android.content.Intent
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
import androidx.lifecycle.Observer
import com.bhx.common.event.LiveBus
import com.bhx.common.utils.AppManager
import com.bhx.common.utils.DeviceUtils
import com.bhx.common.utils.PhoneUtils
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ext.isEnable
import com.jxqm.jiangdou.model.UserModel
import com.jxqm.jiangdou.ui.home.view.MainActivity
import com.jxqm.jiangdou.utils.click
import kotlinx.android.synthetic.main.activity_forget_psd.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_phone_login.etInputPhone
import kotlinx.android.synthetic.main.activity_phone_login.etPassword
import kotlinx.android.synthetic.main.activity_phone_login.tvLogin


/**
 * 手机号和密码登录
 * Created By bhx On 2019/8/6 0006 09:32
 */
class PhoneLoginActivity : BaseDataActivity<PhoneLoginViewModel>() {
    var isEyeOpen = false

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
        tvLogin.isEnable(etInputPhone) { isLoginState() }
        tvLogin.isEnable(etPassword) { isLoginState() }
        tvForgetPsd.clickWithTrigger {
            startActivity<ForgetPsdActivity>()
        }
        tvRegister.clickWithTrigger {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
        tvLogin.clickWithTrigger {
            val phone = etInputPhone.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val deviceId = DeviceUtils.getDeviceId(this)
            mViewModel.doLogin(phone, password, deviceId)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val phone = data?.getStringExtra("phone")
            phone?.let {
                etInputPhone.setText(it)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun isLoginState(): Boolean {
        val phone = etInputPhone.text.toString().trim()
        val password = etPassword.text.toString().trim()
        return PhoneUtils.isMobile(phone) and password.isNotEmpty()
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

    override fun dataObserver() {
        registerObserver(Constants.TAG_PHONE_LOGIN_SUCCESS, UserModel::class.java).observe(this, Observer {
            //登录成功
            LiveBus.getDefault().postEvent(Constants.EVENT_KEY_MAIN_MY, Constants.TAG_MAIN_MY_LOGIN_SUCCESS, true)
            //发送登录成功得消息
            AppManager.getAppManager().finishOthersActivity(MainActivity::class.java)
        })
    }

    companion object {
        const val REQUEST_CODE = 0x01
    }
}