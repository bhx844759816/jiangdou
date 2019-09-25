package com.jxqm.jiangdou.ui.login.view

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.employer.view.EmployRecordSignUpFragment
import com.jxqm.jiangdou.ui.home.view.MainActivity
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_guide.*
import kotlinx.android.synthetic.main.activity_job_details.*

/**
 * 引导页
 * Created By bhx On 2019/9/5 0005 15:29
 */
class GuideActivity : BaseActivity() {
    private val mListFragment = arrayListOf<Fragment>()
    override fun getLayoutId(): Int = R.layout.activity_guide

    override fun initView() {
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        mListFragment.add(GuideFragment.newInstance(0))
        mListFragment.add(GuideFragment.newInstance(1))
        mListFragment.add(GuideFragment.newInstance(2))
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = MyPageAdapter(supportFragmentManager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        rgDot.check(R.id.rbDotOne)
                        tvJumpMain.visibility = View.GONE
                    }
                    1 -> {
                        rgDot.check(R.id.rbDotTwo)
                        tvJumpMain.visibility = View.GONE
                    }
                    2 -> {
                        tvJumpMain.visibility = View.VISIBLE
                        rgDot.check(R.id.rbDotThree)
                    }
                }
            }
        })
        tvJumpMain.clickWithTrigger {
            startActivity<MainActivity>()
            finish()
        }
    }

    override fun onBackPressed() {
    }

    inner class MyPageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment = mListFragment[position]
        override fun getCount(): Int = mListFragment.size
    }
}