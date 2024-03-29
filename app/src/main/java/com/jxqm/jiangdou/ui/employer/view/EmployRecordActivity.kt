package com.jxqm.jiangdou.ui.employer.view

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.ToastUtils
import com.google.android.material.tabs.TabLayout
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ext.hideKeyboard
import com.jxqm.jiangdou.ui.employer.vm.EmployRecordViewModel
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_employ_record.*

/**
 * 雇佣记录
 * Created by Administrator on 2019/9/2.
 */
class EmployRecordActivity : BaseDataActivity<EmployRecordViewModel>() {
    override fun getEventKey(): Any = Constants.EVENT_KEY_EMPLOY_RECORD

    private val mTitles = arrayListOf("报名", "录用", "到岗", "待结算", "已结算")
    private val mListFragment = arrayListOf<Fragment>()
    private lateinit var jobId: String
    override fun getLayoutId(): Int = R.layout.activity_employ_record

    override fun initView() {
        super.initView()
        jobId = intent.getStringExtra("jobId")
        //雇佣记录跳转到对应的页面
        val type = intent.getIntExtra("type", 0)
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        mListFragment.add(EmployRecordSignUpFragment.newInstance(jobId))
        mListFragment.add(EmployRecordEmploymentFragment.newInstance(jobId))
        mListFragment.add(EmployRecordReportDutyFragment.newInstance(jobId))
        mListFragment.add(EmployRecordWaitPayFragment.newInstance(jobId))
        mListFragment.add(EmployRecordPayFragment.newInstance(jobId))
        viewPager.offscreenPageLimit = 5
        viewPager.adapter = MyPageAdapter(supportFragmentManager)
        tabLayout.addTab(mTitles[0])
        tabLayout.addTab(mTitles[1])
        tabLayout.addTab(mTitles[2])
        tabLayout.addTab(mTitles[3])
        tabLayout.addTab(mTitles[4])
        viewPager.setCurrentItem(type, false)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout.tabLayout))
        tabLayout.setupWithViewPager(viewPager)
        myEmployRecordBack.clickWithTrigger {
            finish()
        }


        etSearch.addTextChangedListener {
            afterTextChanged {
                val content = etSearch.text.toString().trim()
                if (content.isNotEmpty()) {
                    ivDelete.visibility = View.VISIBLE
                } else {
                    ivDelete.visibility = View.GONE
                }
            }
        }
        ivDelete.clickWithTrigger {
            etSearch.setText("")
        }
        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchKey = etSearch.text.toString()
                MyApplication.instance().searchKeyWork = searchKey
                if (searchKey.isNotEmpty()) {
                    etSearch.hideKeyboard()
                    //存储到SharedPreference
                    doSearch(searchKey)
                    return@setOnEditorActionListener true
                } else {
                    ToastUtils.toastShort("请输入搜索关键词")
                }
            }
            return@setOnEditorActionListener false
        }
    }

    inner class MyPageAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment = mListFragment[position]
        override fun getCount(): Int = mListFragment.size
        override fun getPageTitle(position: Int): CharSequence? {
            return mTitles[position]
        }
    }

    /**
     * 搜索
     */
    private fun doSearch(searchKey: String) {
        when (val fragment = mListFragment[viewPager.currentItem]) {
            is EmployRecordSignUpFragment -> {

            }
            is EmployRecordEmploymentFragment -> {

            }
            is EmployRecordReportDutyFragment -> {

            }
            is EmployRecordWaitPayFragment -> {

            }
            is EmployRecordPayFragment -> {

            }
        }
    }
}