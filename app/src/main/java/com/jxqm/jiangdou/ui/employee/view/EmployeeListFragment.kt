package com.jxqm.jiangdou.ui.employee.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.utils.LogUtils
import com.google.android.material.tabs.TabLayout
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.job.view.JobListFragment
import kotlinx.android.synthetic.main.fragment_employee_list_layout.*
import kotlinx.android.synthetic.main.fragment_employee_list_layout.tabLayout
import kotlinx.android.synthetic.main.fragment_employee_list_layout.viewPager
import kotlinx.android.synthetic.main.fragment_employer_job_list.*

/**
 * Created by Administrator on 2019/8/31.
 */
class EmployeeListFragment : BaseLazyFragment() {
    private val mTitles = arrayListOf("已报名", "已录用", "已到岗", "已结算")
    private val mListFragment = arrayListOf<Fragment>()


    override fun getLayoutId(): Int = R.layout.fragment_employee_list_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt("position")
        LogUtils.i("position=$position")
        mListFragment.add(EmployeeSignUpFragment())
        mListFragment.add(EmployeeEmploymentFragment())
        mListFragment.add(EmployeeReportDutyFragment())
        mListFragment.add(EmployeePayFinishFragment())
        viewPager.offscreenPageLimit = 4
        viewPager.adapter = MyPageAdapter(childFragmentManager)
        tabLayout.addTab(mTitles[0])
        tabLayout.addTab(mTitles[1])
        tabLayout.addTab(mTitles[2])
        tabLayout.addTab(mTitles[3])
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout.tabLayout))
        tabLayout.setupWithViewPager(viewPager)
        viewPager.setCurrentItem(position ?: 0, false)
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
    fun doSearch(searchKey: String) {
        when (val fragment = mListFragment[viewPager.currentItem]) {
            is EmployeeSignUpFragment -> {
                fragment.doSearch(searchKey)
            }
            is EmployeeEmploymentFragment -> {
                fragment.doSearch(searchKey)
            }
            is EmployeeReportDutyFragment -> {
                fragment.doSearch(searchKey)
            }
            is EmployeePayFinishFragment -> {
                fragment.doSearch(searchKey)
            }
        }
    }
}