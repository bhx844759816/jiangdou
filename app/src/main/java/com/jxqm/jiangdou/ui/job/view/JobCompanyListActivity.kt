package com.jxqm.jiangdou.ui.job.view

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.ToastUtils
import com.google.android.material.tabs.TabLayout
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ext.hideKeyboard
import com.jxqm.jiangdou.ui.city.SelectCity
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_job_company_list.*
import kotlinx.android.synthetic.main.view_search_top.*


/**
 * 工作和公司列表
 * Created by Administrator on 2019/8/17.
 */
class JobCompanyListActivity : BaseActivity() {
    private val mTitles = arrayListOf("搜职位", "搜公司")
    private val mListFragment = arrayListOf<Fragment>()
    private var mCurrentFragment: Fragment? = null
    private var mSearchKey: String = ""
    override fun getLayoutId(): Int = R.layout.activity_job_company_list

    override fun initView() {
        super.initView()
        mSearchKey = intent.getStringExtra("SearchKey")
        tvSearchJob.setText(mSearchKey)
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        mListFragment.add(JobListFragment.newInstance(mSearchKey))
        mListFragment.add(CompanyListFragment.newInstance(mSearchKey))
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = MyPageAdapter(supportFragmentManager)
        tvCity.clickWithTrigger {
            startActivity<SelectCity>()
        }
        tabLayout.addTab(mTitles[0])
        tabLayout.addTab(mTitles[1])
        mCurrentFragment = mListFragment[0]
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout.tabLayout))
        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                mCurrentFragment = mListFragment[position]
            }
        })
        cancel.clickWithTrigger {
            finish()
        }

        tvSearchJob.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mSearchKey = tvSearchJob.text.toString()
                if (mSearchKey.isNotEmpty()) {
                    when (mCurrentFragment) {
                        is JobListFragment -> {
                          (mCurrentFragment as JobListFragment).startSearch(mSearchKey)
                        }
                        is CompanyListFragment -> {
                            (mCurrentFragment as CompanyListFragment).startSearch(mSearchKey)
                        }
                    }
                    tvSearchJob.hideKeyboard()
                    return@setOnEditorActionListener true
                } else {
                    ToastUtils.toastShort("请输入搜索关键词")
                }
            }
            return@setOnEditorActionListener false
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