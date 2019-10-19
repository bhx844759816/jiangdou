package com.jxqm.jiangdou.ui.user.view

import androidx.lifecycle.Observer
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.DeviceUtils
import com.bhx.common.utils.PhoneUtils
import com.bhx.common.utils.RegularUtils
import com.bhx.common.utils.ToastUtils
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ext.isEnable
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.ui.user.vm.CashOutViewModel
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_cash_out.*
import java.util.concurrent.TimeUnit

/**
 * 提现界面
 * Created By bhx On 2019/9/4 0004 16:38
 */
class CashOutActivity : BaseDataActivity<CashOutViewModel>() {
    private var mCount = 60
    private var isSendCodeSuccess = false
    override fun getEventKey(): Any = Constants.EVENT_CASH_OUT
    override fun getLayoutId(): Int = R.layout.activity_cash_out
    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        cashOutBack.clickWithTrigger {
            finish()
        }
        tvInputPhone.text = MyApplication.instance().userModel?.username
        //提交
        tvSubmit.clickWithTrigger {
            submit()
        }
        tvGetCode.clickWithTrigger {
            mViewModel.senSmsCode()
        }
        etInputAmount.addTextChangedListener {
            afterTextChanged {
                changeSubmitStatus()
                tvMoney.text = "$ ${etInputAmount.text.toString().trim()}"
            }
        }
        etInputAlipayNum.addTextChangedListener {
            afterTextChanged { changeSubmitStatus() }
        }
        etInputName.addTextChangedListener {
            afterTextChanged {
                changeSubmitStatus()
            }
        }
        etInputSmsCode.addTextChangedListener {
            afterTextChanged {
                changeSubmitStatus()
            }
        }

    }

    private fun submit() {
        val amount = etInputAmount.text.toString().trim()
        val alipayNum = etInputAlipayNum.text.toString().trim()
        val name = etInputName.text.toString().trim()
        val smsCode = etInputSmsCode.text.toString().trim()
        if (amount.isEmpty() || amount.toInt() >= 1000) {
            ToastUtils.toastShort("一次最多可提取1000豆币")
            return
        }
        if (alipayNum.isEmpty()) {
            ToastUtils.toastShort("请输入支付宝账号")
            return
        }
        if (!RegularUtils.isLegalName(name)) {
            ToastUtils.toastShort("请输入姓名")
            return
        }
        if (smsCode.isEmpty()) {
            ToastUtils.toastShort("请输入验证码")
            return
        }
        val params = mutableMapOf<String, String>()
        params["amount"] = amount
        params["alipay"] = alipayNum
        params["code"] = smsCode
        params["name"] = name
        mViewModel.sendCashOutRequest(params)
    }

    private fun changeSubmitStatus() {
        val amount = etInputAmount.text.toString().trim()
        val alipayNum = etInputAlipayNum.text.toString().trim()
        val name = etInputName.text.toString().trim()
        val smsCode = etInputSmsCode.text.toString().trim()
        val isEnable =
            amount.isNotEmpty() && amount.toInt() <= 1000 && alipayNum.isNotEmpty() && RegularUtils.isLegalName(name) && smsCode.isNotEmpty()
        if (isEnable) {
            tvSubmit.setBackgroundResource(R.drawable.shape_button_select)
        } else {
            tvSubmit.setBackgroundResource(R.drawable.shape_button_default)
        }
    }

    override fun dataObserver() {
        registerObserver(Constants.TAG_CASH_OUT_SEND_SMS_CODE_SUCCESS, Boolean::class.java).observe(
            this,
            Observer {
                smsCodeCountDown()
            })
        registerObserver(Constants.TAG_CASH_OUT_SUCCESS, Boolean::class.java).observe(
            this,
            Observer {
                startActivity<CashOutSuccessActivity>()
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