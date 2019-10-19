package com.jxqm.jiangdou.ui.login.view

import androidx.lifecycle.Observer
import com.bhx.common.utils.DeviceUtils
import com.bhx.common.utils.RegularUtils
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ext.isEnable
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.ui.login.vm.ForgetPsdViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_forget_psd.*
import java.util.concurrent.TimeUnit

/**
 * 忘记密码界面
 * Created By bhx On 2019/8/6 0006 09:32
 */
class ForgetPsdActivity : BaseDataActivity<ForgetPsdViewModel>() {
    private var mCount = 60
    private var isSendCodeSuccess = false
    override fun getLayoutId(): Int = R.layout.activity_forget_psd

    override fun getEventKey(): Any = Constants.EVENT_KEY_FORGET_PSD

    override fun initView() {
        super.initView()
        flBack.clickWithTrigger { finish() }
        tvGetCode.isEnable(etInputPhone) {
            val phone = etInputPhone.text.toString().trim()
            !isSendCodeSuccess and RegularUtils.isTelPhoneNumber(phone)
        }
        tvSubmit.clickWithTrigger {
            val phone = etInputPhone.text.toString().trim()
            val code = etCode.text.toString().trim()
            val passWord = etPassword.text.toString().trim()
            val deviceId = DeviceUtils.getDeviceId(this)
            mViewModel.modifyPsd(phone, deviceId, passWord, code)
        }
        //发送验证码
        tvGetCode.clickWithTrigger {
            val phone = etInputPhone.text.toString().trim()
            val deviceId = DeviceUtils.getDeviceId(this)
            mViewModel.sendSmsCode(phone, deviceId)
        }
        tvSubmit.isEnable(etInputPhone) { onSubmitTextState() }
        tvSubmit.isEnable(etCode) { onSubmitTextState() }
        tvSubmit.isEnable(etPassword) { onSubmitTextState() }
    }

    override fun dataObserver() {
        //发送验证码成功
        registerObserver(
            Constants.TAG_FORGET_PSD_GET_SMS_CODE_SUCCESS,
            Boolean::class.java
        ).observe(this, Observer {
            //发送验证码成功
            smsCodeCountDown()
        })
        // 修改密码成功
        registerObserver(Constants.TAG_FORGET_PSD_MODIFY_PSD_SUCCESS, Boolean::class.java).observe(
            this,
            Observer {
                finish()
            })
    }

    /**
     * 验证注册按钮是否可以被点击
     */
    private fun onSubmitTextState(): Boolean {
        val phone = etInputPhone.text.toString().trim()
        val code = etCode.text.toString().trim()
        val passWord = etPassword.text.toString().trim()
        return RegularUtils.isTelPhoneNumber(phone) and code.isNotEmpty() and passWord.isNotEmpty()
    }

    /**
     * 发送验证码成功后的倒计时
     */
    private fun smsCodeCountDown() {
        addDisposable(
            Observable.interval(0, 1, TimeUnit.SECONDS)
                .take((mCount + 1).toLong()) //
                .doOnSubscribe {
                    isSendCodeSuccess = true
                    tvGetCode.isEnabled = false
                }.subscribeOn(AndroidSchedulers.mainThread())
                .compose(applySchedulers())
                .subscribe({
                    mCount--
                    tvGetCode.text = String.format("%ss", mCount)
                }, {
                    tvGetCode.isEnabled = true
                    tvGetCode.text = "重新发送"
                    isSendCodeSuccess = false
                }, {
                    tvGetCode.isEnabled = true
                    tvGetCode.text = "重新发送"
                    isSendCodeSuccess = false
                })
        )
    }
}