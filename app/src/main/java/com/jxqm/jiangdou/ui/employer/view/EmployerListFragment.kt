package com.jxqm.jiangdou.ui.employer.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bhx.common.base.BaseLazyFragment
import com.google.android.material.tabs.TabLayout
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.publish.view.JobPublishActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.fragment_employer_job_list.*

/**
 * 雇主界面
 * Created by Administrator on 2019/9/2.
 */
class EmployerListFragment : BaseLazyFragment() {
    private val mTitles = arrayListOf("等待发布", "等待审核", "正在招聘", "截止报名")
    private val mListFragment = arrayListOf<Fragment>()
    override fun getLayoutId(): Int = R.layout.fragment_employer_job_list
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mListFragment.add(EmployerJobListFragment.newInstance(0))
        mListFragment.add(EmployerJobListFragment.newInstance(1))
        mListFragment.add(EmployerJobListFragment.newInstance(2))
        mListFragment.add(EmployerJobListFragment.newInstance(3))
        viewPager.offscreenPageLimit = 4
        viewPager.adapter = MyPageAdapter(childFragmentManager)
        tabLayout.addTab(mTitles[0])
        tabLayout.addTab(mTitles[1])
        tabLayout.addTab(mTitles[2])
        tabLayout.addTab(mTitles[3])
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout.tabLayout))
        tabLayout.setupWithViewPager(viewPager)

        ivPublishJob.clickWithTrigger {
            startActivity<JobPublishActivity>()
        }
    }

    inner class MyPageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment = mListFragment[position]
        override fun getCount(): Int = mListFragment.size
        override fun getPageTitle(position: Int): CharSequence? {
            return mTitles[position]
        }
    }

}