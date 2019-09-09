package com.jxqm.jiangdou.ui.login.view

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import com.bhx.common.utils.DeviceUtils
import com.bhx.common.utils.PhoneUtils
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ext.isEnable
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.ui.login.vm.RegisterViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_forget_psd.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.etCode
import kotlinx.android.synthetic.main.activity_register.etInputPhone
import kotlinx.android.synthetic.main.activity_register.etPassword
import kotlinx.android.synthetic.main.activity_register.flBack
import kotlinx.android.synthetic.main.activity_register.tvGetCode
import kotlinx.android.synthetic.main.activity_register.tvSubmit
import kotlinx.android.synthetic.main.activity_verify_code.*
import java.util.concurrent.TimeUnit

/**
 * 注册界面
 * Created By bhx On 2019/8/6 0006 09:32
 */
class RegisterActivity : BaseDataActivity<RegisterViewModel>() {
    private var mCount = 60
    private var isSendCodeSuccess = false
    override fun getLayoutId(): Int = R.layout.activity_register

    override fun getEventKey(): Any = Constants.EVENT_KEY_REGISTER

    override fun initView() {
        super.initView()
        flBack.clickWithTrigger {
            finish()
        }
        tvGetCode.clickWithTrigger {
            val phone = etInputPhone.text.toString().trim()
            val deviceId = DeviceUtils.getDeviceId(this)
            mViewModel.sendSmsCode(phone, deviceId)
        }
        tvSubmit.clickWithTrigger {
            val phone = etInputPhone.text.toString().trim()
            val code = etCode.text.toString().trim()
            val passWord = etPassword.text.toString().trim()
            val deviceId = DeviceUtils.getDeviceId(this)
            mViewModel.register(phone, deviceId, passWord, code)
        }
        tvGetCode.isEnable(etInputPhone) {
            val phone = etInputPhone.text.toString().trim()
            PhoneUtils.isMobile(phone)
        }
        tvSubmit.isEnable(etInputPhone) { onSubmitTextState() }
        tvSubmit.isEnable(etCode) { onSubmitTextState() }
        tvSubmit.isEnable(etPassword) { onSubmitTextState() }
    }

    /**
     * 验证注册按钮是否可以被点击
     */
    private fun onSubmitTextState(): Boolean {
        val phone = etInputPhone.text.toString().trim()
        val code = etCode.text.toString().trim()
        val passWord = etPassword.text.toString().trim()
        return !isSendCodeSuccess and PhoneUtils.isMobile(phone) and code.isNotEmpty() and passWord.isNotEmpty() and (passWord.length > 6)
    }

    override fun dataObserver() {
        registerObserver(Constants.TAG_REGISTER_GET_CODE_SUCCESS, Boolean::class.java).observe(this, Observer {
            //发送验证码成功
            smsCodeCountDown()
        })
        registerObserver(Constants.TAG_REGISTER_SUCCESS, Boolean::class.java).observe(this, Observer {
            //注册成功
            val phone = etInputPhone.text.toString().trim()
            val intent = Intent()
            intent.putExtra("phone", phone)
            setResult(Activity.RESULT_OK, intent)
            finish()
        })
    }

    /**
     * 发送验证码成功后的倒计时
     */
    private fun smsCodeCountDown() {
        addDisposable(
            Observable.interval(0, 1, TimeUnit.SECONDS)
                .take((mCount + 1).toLong()) //
                .doOnSubscribe {
                    tvCodeReSend.isEnabled = false
                }.subscribeOn(AndroidSchedulers.mainThread())
                .compose(applySchedulers())
                .subscribe({
                    mCount--
                    tvGetCode.text = String.format("%ss", mCount)
                }, {
                    tvCodeReSend.isEnabled = true
                    tvGetCode.text = "重新发送"
                    isSendCodeSuccess = false
                }, {
                    tvCodeReSend.isEnabled = true
                    tvGetCode.text = "重新发送"
                    isSendCodeSuccess = false
                })
        )
    }
}