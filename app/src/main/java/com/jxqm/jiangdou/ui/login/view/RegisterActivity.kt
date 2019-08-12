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
import kotlinx.android.synthetic.main.activity_register.*

/**
 * 注册界面
 * Created By bhx On 2019/8/6 0006 09:32
 */
class RegisterActivity : BaseDataActivity<RegisterViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_register

    override fun getEventKey(): Any = Constants.EVENT_KEY_REGISTER

    override fun initView() {
        super.initView()

        flBack.clickWithTrigger {
            finish()
        }

        etInputPsd.addTextChangedListener {
            afterTextChanged {
                val isEmpty = it?.toString()?.isEmpty()
                if (isEmpty == null || isEmpty) {
                    tvRegister.setBackgroundResource(R.drawable.shape_button_default)
                    tvRegister.isEnabled = false
                } else {
                    tvRegister.setBackgroundResource(R.drawable.shape_button_select)
                    tvRegister.isEnabled = true
                }
            }
        }

        tvRegister.clickWithTrigger {
            ToastUtils.toastShort("注册成功")
        }
    }

}