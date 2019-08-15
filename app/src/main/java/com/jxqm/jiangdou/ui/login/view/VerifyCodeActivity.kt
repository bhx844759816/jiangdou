package com.jxqm.jiangdou.ui.login.view

import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.bhx.common.utils.LogUtils
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.login.vm.VerifyCodeViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import com.jxqm.jiangdou.view.dialog.UserAgreementDialog
import kotlinx.android.synthetic.main.activity_verify_code.*

/**
 * 验证码界面
 * Created By bhx On 2019/8/6 0006 09:35
 */
class VerifyCodeActivity : BaseDataActivity<VerifyCodeViewModel>() {
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
                        ToastUtils.toastShort("输入完成")
                        tvLogin.isEnabled = true
                        tvLogin.setBackgroundResource(R.drawable.icon_shadow_btn)
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
        registerObserver(Constants.TAG_AGREE_CONTINUE, Boolean::class.java).observe(this, Observer {
            if (it) {
                startActivity<RegisterActivity>()
            }
        })
    }

    override fun initView() {
        super.initView()
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
            UserAgreementDialog.show(this)
        }
    }


}