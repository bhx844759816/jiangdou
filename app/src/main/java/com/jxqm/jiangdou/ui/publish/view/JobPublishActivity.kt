package com.jxqm.jiangdou.ui.publish.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bhx.common.base.BaseActivity
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.listener.OnJobPublishCallBack
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_publish.*

/**
 * 发布兼职的界面
 * Created By bhx On 2019/8/8 0008 09:00
 */
class JobPublishActivity : BaseActivity(), OnJobPublishCallBack {


    private var mJobTypeFragment: JobTypeFragment? = null
    private var mJobMessageFragment: JobMessageFragment? = null
    private var mJobTimeFragment: JobTimeFragment? = null
    private var mJobContactsFragment: JobContactsFragment? = null
    private var mCurrentFragment: Fragment? = null


    override fun getLayoutId(): Int = R.layout.activity_publish


    override fun initView() {
        super.initView()
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        showSelectJobTypeFragment()
    }

    /**
     * 展示选择工作类别
     */
    private fun showSelectJobTypeFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        if (mJobTypeFragment == null) {
            mJobTypeFragment = JobTypeFragment()
        }
        hideAllFragment(transaction)
        if (!mJobTypeFragment!!.isAdded) {
            transaction.add(R.id.flFragmentParent, mJobTypeFragment!!)
        } else {
            transaction.show(mJobTypeFragment!!)
        }
        mCurrentFragment = mJobTypeFragment
        transaction.commit()
    }

    /**
     * 展示选择工作详情的界面
     */
    private fun showSelectJobMessageFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        if (mJobMessageFragment == null) {
            mJobMessageFragment = JobMessageFragment()
        }
        hideAllFragment(transaction)
        if (!mJobMessageFragment!!.isAdded) {
            transaction.add(R.id.flFragmentParent, mJobMessageFragment!!)
        } else {
            transaction.show(mJobMessageFragment!!)
        }
        mCurrentFragment = mJobMessageFragment
        transaction.commit()
    }

    /**
     * 展示时间选着界面
     */
    private fun showSelectJobTimeFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        if (mJobTimeFragment == null) {
            mJobTimeFragment = JobTimeFragment()
        }
        hideAllFragment(transaction)
        if (!mJobTimeFragment!!.isAdded) {
            transaction.add(R.id.flFragmentParent, mJobTimeFragment!!)
        } else {
            transaction.show(mJobTimeFragment!!)
        }
        mCurrentFragment = mJobTimeFragment
        transaction.commit()
    }

    /**
     * 展示联系人界面
     */
    private fun showSelectJobContactsFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        if (mJobContactsFragment == null) {
            mJobContactsFragment = JobContactsFragment()
        }
        hideAllFragment(transaction)
        if (!mJobContactsFragment!!.isAdded) {
            transaction.add(R.id.flFragmentParent, mJobContactsFragment!!)
        } else {
            transaction.show(mJobContactsFragment!!)
        }
        mCurrentFragment = mJobContactsFragment
        transaction.commit()
    }

    private fun hideAllFragment(transaction: FragmentTransaction) {
        mJobTypeFragment?.let {
            if (it.isResumed) {
                transaction.hide(it)
            }
        }
        mJobMessageFragment?.let {
            if (it.isResumed) {
                transaction.hide(it)
            }
        }
        mJobTimeFragment?.let {
            if (it.isResumed) {
                transaction.hide(it)
            }
        }
        mJobContactsFragment?.let {
            if (it.isResumed) {
                transaction.hide(it)
            }
        }
    }

    override fun jobTypNextStep() {
        showSelectJobMessageFragment()
    }

    override fun jobMessageNextStep() {
        showSelectJobTimeFragment()
    }

    override fun jobTimeNextStep() {
        showSelectJobContactsFragment()
    }

    override fun jobContactsNextStep() {
        startActivity<JobPreviewActivity>()
    }

    override fun onBackPressed() {
        if (mCurrentFragment is JobTypeFragment) {
            super.onBackPressed()
        } else {
            when (mCurrentFragment) {
                is JobMessageFragment -> {
                    showSelectJobTypeFragment()
                }
                is JobTimeFragment -> {
                    showSelectJobMessageFragment()
                }
                is JobContactsFragment -> {
                    showSelectJobTimeFragment()
                }
            }
        }
    }

}