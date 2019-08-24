package com.jxqm.jiangdou.ui.job.view

import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bhx.common.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.city.SelectCity
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
    override fun getLayoutId(): Int = R.layout.activity_job_company_list

    override fun initView() {
        super.initView()
        mListFragment.add(JobListFragment())
        mListFragment.add(CompanyListFragment())
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = MyPageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        tvCity.clickWithTrigger {
            startActivity<SelectCity>()
        }
        tvSearchJob.clickWithTrigger {
            startActivity<JobSearchActivity>()
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                val title =
//                    ((tabLayout.getChildAt(0) as LinearLayout).getChildAt(tabLayout.selectedTabPosition) as LinearLayout).getChildAt(
//                        1
//                    ) as TextView
//                title.textSize = resources.getDimension(R.dimen.text_small_size)
//                title.setTextAppearance(this@JobCompanyListActivity, Typeface.NORMAL)
                val title = tab?.customView
                title?.let {
                    if (it is TextView) {
                        it.textSize = resources.getDimension(R.dimen.text_small_size)
                        it.setTextAppearance(this@JobCompanyListActivity, R.style.TabLayoutTextSize)
                    }
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val title = tab?.customView
                title?.let {
                    if (it is TextView) {
                        it.textSize = resources.getDimension(R.dimen.text_default_size)
                        it.setTextAppearance(this@JobCompanyListActivity, R.style.TabLayoutTextStyle)
                    }
                }
//                val title =
//                    ((tabLayout.getChildAt(0) as LinearLayout).getChildAt(tabLayout.selectedTabPosition) as LinearLayout).getChildAt(
//                        1
//                    ) as TextView

            }
        })
    }

    inner class MyPageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment = mListFragment[position]
        override fun getCount(): Int = mListFragment.size
        override fun getPageTitle(position: Int): CharSequence? {
            return mTitles[position]
        }
    }
}