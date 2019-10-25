package com.jxqm.jiangdou.ui.login.view

import androidx.fragment.app.FragmentTransaction
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.login.view.fragment.LoginPhonePsdFragment
import com.jxqm.jiangdou.ui.login.view.fragment.LoginQuickFragment
import com.jxqm.jiangdou.ui.login.vm.LoginViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_login.*


/**
 * 登录界面
 * Created By bhx On 2019/8/6 0006 09:32
 */
class LoginActivity : BaseDataActivity<LoginViewModel>() {
    private var mQuickLoginFragment: LoginQuickFragment? = null
    private var mPhonePsdLoginFragment: LoginPhonePsdFragment? = null
    private var isQuickLoginFlag = true
    override fun getLayoutId(): Int = R.layout.activity_login

    override fun getEventKey(): Any = Constants.EVENT_KEY_LOGIN

    override fun initView() {
        super.initView()
        showQuickLoginFragment()
        tvAccountLogin.clickWithTrigger {
            isQuickLoginFlag = !isQuickLoginFlag
            tvAccountLogin.text = if (isQuickLoginFlag) "手机快捷登录" else "账号密码登录"
            if (isQuickLoginFlag) {
                showQuickLoginFragment()
            } else {
                showPhonePsdLoginFragment()
            }
        }
        toolBar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        this.finish()
    }

    /**
     * 展示快捷登录的界面
     */
    private fun showQuickLoginFragment() {
        val transaction = supportFragmentManager.beginTransaction().disallowAddToBackStack()
        hideAllFragment(transaction)
        if (mQuickLoginFragment == null) {
            mQuickLoginFragment = LoginQuickFragment()
        }
        if (!mQuickLoginFragment!!.isAdded) {
            transaction.add(R.id.flFragment, mQuickLoginFragment!!)
        } else {
            transaction.show(mQuickLoginFragment!!)
        }
        transaction.commit()
    }

    /**
     * 展示手机密码登录的界面
     */
    private fun showPhonePsdLoginFragment() {
        val transaction = supportFragmentManager.beginTransaction().disallowAddToBackStack()
        hideAllFragment(transaction)
        if (mPhonePsdLoginFragment == null) {
            mPhonePsdLoginFragment = LoginPhonePsdFragment()
        }
        if (!mPhonePsdLoginFragment!!.isAdded) {
            transaction.add(R.id.flFragment, mPhonePsdLoginFragment!!)
        } else {
            transaction.show(mPhonePsdLoginFragment!!)
        }
        transaction.commit()
    }

    private fun hideAllFragment(transaction: FragmentTransaction) {
        if (mQuickLoginFragment != null && mQuickLoginFragment!!.isAdded) {
            transaction.hide(mQuickLoginFragment!!)
        }
        if (mPhonePsdLoginFragment != null && mPhonePsdLoginFragment!!.isAdded) {
            transaction.hide(mPhonePsdLoginFragment!!)
        }
    }


}