package com.jxqm.jiangdou.ui.login.view

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.login.vm.LoginViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Context
import com.jxqm.jiangdou.ui.publish.view.JobPreviewActivity
import com.jxqm.jiangdou.ui.publish.view.PublishActivity


/**
 * 登录界面
 * Created By bhx On 2019/8/6 0006 09:32
 */
class LoginActivity : BaseDataActivity<LoginViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_login

    override fun getEventKey(): Any = Constants.EVENT_KEY_LOGIN

    override fun initView() {
        super.initView()
        tvLogin.isClickable = false
        tvAccountLogin.clickWithTrigger {
            startActivity<JobPreviewActivity>()
        }
        tvLogin.clickWithTrigger {
            startActivity<VerifyCodeActivity>()
        }
        etInputPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                val isEmpty = editable?.toString()?.isEmpty()
                if (isEmpty == null || isEmpty) {
                    tvLogin.setBackgroundResource(R.drawable.shape_button_default)
                    tvLogin.isEnabled = false
                } else {
                    tvLogin.setBackgroundResource(R.drawable.shape_button_select)
                    tvLogin.isEnabled = true
                }

            }
        })
    }


}