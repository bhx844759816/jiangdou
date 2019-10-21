package com.jxqm.jiangdou.ui.login.view

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.EditText
import androidx.lifecycle.Observer
import com.bhx.common.event.LiveBus
import com.bhx.common.utils.AppManager
import com.bhx.common.utils.DeviceUtils
import com.bhx.common.utils.LogUtils
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.model.UserModel
import com.jxqm.jiangdou.ui.home.view.MainActivity
import com.jxqm.jiangdou.ui.login.vm.VerifyCodeViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_verify_code.*
import java.util.concurrent.TimeUnit

/**
 * 验证码界面
 * Created By bhx On 2019/8/6 0006 09:35
 */
class VerifyCodeActivity : BaseDataActivity<VerifyCodeViewModel>() {
    private var mCount = 60
    private var mPhone: String? = null
    private var mDeviceId: String? = null
    val codeArras = arrayListOf<String>()
    override fun getLayoutId(): Int = R.layout.activity_verify_code

    override fun getEventKey(): Any = Constants.EVENT_KEY_VERIFY_CODE

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            val content = editable.toString()
            if (content.isNotEmpty()) {
                codeArras.add(content)
                LogUtils.i("codeArras.size ${codeArras.size}")
                when (codeArras.size) {
                    1 -> {
                        clearFocus(etInputNumOne)
                        requestFocus(etInputNumTwo)
                    }
                    2 -> {
                        clearFocus(etInputNumTwo)
                        requestFocus(etInputNumThree)
                    }
                    3 -> {
                        clearFocus(etInputNumThree)
                        requestFocus(etInputNumFour)
                    }
                    4 -> {
                        tvLogin.isEnabled = true
                        tvLogin.setBackgroundResource(R.drawable.shape_button_select)
                    }
                }
            } else {
                if (codeArras.size > 0) {
                    codeArras.removeAt(codeArras.size - 1)
                }
            }
            if (tvLogin.isEnabled && codeArras.size < 4) {
                tvLogin.isEnabled = false
                tvLogin.setBackgroundResource(R.drawable.shape_button_default)
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

    }

    fun clearFocus(editText: EditText) {
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false
        editText.clearFocus()
    }

    fun requestFocus(editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
    }

    override fun dataObserver() {
        super.dataObserver()
        registerObserver(Constants.TAG_SEND_SMS_CODE_RESULT, Boolean::class.java).observe(this, Observer {
            if (it) {
                mCount = 60
                ToastUtils.toastShort("发送验证码成功")
                smsCodeCountDown()
            }
        })
        registerObserver(Constants.TAG_GET_USER_INFO_SUCCESS, Boolean::class.java).observe(this, Observer {
            LiveBus.getDefault().postEvent(Constants.EVENT_KEY_MAIN_MY, Constants.TAG_MAIN_MY_LOGIN_SUCCESS, true)
            LiveBus.getDefault().postEvent(Constants.EVENT_KEY_EMPLOYEE_SIGN_UP, Constants.TAG_MAIN_MY_LOGIN_SUCCESS, true)
            AppManager.getAppManager().finishActivity(LoginActivity::class.java)
            finish()
            //发送消息到MainFragment
        })
    }

    override fun initView() {
        super.initView()
        mPhone = intent.getStringExtra("phone")
        mDeviceId = intent.getStringExtra("deviceId")
        smsCodeCountDown()
        flBack.clickWithTrigger {
            finish()
        }
        etInputNumOne.addTextChangedListener(textWatcher)
        etInputNumTwo.addTextChangedListener(textWatcher)
        etInputNumThree.addTextChangedListener(textWatcher)
        etInputNumFour.addTextChangedListener(textWatcher)
        etInputNumTwo.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.action != KeyEvent.ACTION_UP) {
                if (etInputNumTwo.text.toString().isEmpty()) {
                    clearFocus(etInputNumTwo)
                    requestFocus(etInputNumOne)
                    return@setOnKeyListener true
                }

            }
            return@setOnKeyListener false
        }
        etInputNumThree.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.action != KeyEvent.ACTION_UP) {
                if (etInputNumThree.text.toString().isEmpty()) {
                    clearFocus(etInputNumThree)
                    requestFocus(etInputNumTwo)
                    return@setOnKeyListener true
                }

            }
            return@setOnKeyListener false
        }
        etInputNumFour.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.action != KeyEvent.ACTION_UP) {
                if (etInputNumFour.text.toString().isEmpty()) {
                    clearFocus(etInputNumFour)
                    requestFocus(etInputNumThree)
                    return@setOnKeyListener true
                }

            }
            return@setOnKeyListener false
        }
        tvLogin.clickWithTrigger {
            if (mPhone.isNullOrEmpty() || mDeviceId.isNullOrEmpty()) {
                return@clickWithTrigger
            }
            val code = codeArras.joinToString("")
            LogUtils.i("验证码$code")
            mViewModel.getToken(mDeviceId!!, mPhone!!, code)
        }
        tvCodeReSend.clickWithTrigger {
            if (mPhone.isNullOrEmpty() || mDeviceId.isNullOrEmpty()) {
                return@clickWithTrigger
            }
            mViewModel.sendSmsCode(mDeviceId!!, mPhone!!)
        }
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
                    tvCodeReSend.text = String.format("%ss后可重新发送", mCount)
                }, {
                    tvCodeReSend.isEnabled = true
                    tvCodeReSend.text = "点击重新发送"
                }, {
                    tvCodeReSend.isEnabled = true
                    tvCodeReSend.text = "点击重新发送"
                })
        )
    }


}