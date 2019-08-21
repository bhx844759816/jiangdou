package com.jxqm.jiangdou.ui.home.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.bhx.common.base.BaseActivity
import com.jxqm.jiangdou.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 *主页面
 * Created by Administrator on 2019/8/20.
 */
class MainActivity : BaseActivity() {
    private var mListFragment = arrayListOf<Fragment>()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        super.initView()
        mListFragment.add(HomeFragment())
        mListFragment.add(WorkFragment())
        mListFragment.add(MyFragment())
        myViewPage.offscreenPageLimit = 3
        myViewPage.adapter = MyPageAdapter(supportFragmentManager)
        myBottomNavigationBar.setTabSelectedListener(object: BottomNavigationBar.OnTabSelectedListener{
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                myViewPage.currentItem = position
            }

        })
    }

    inner class MyPageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment = mListFragment[position]
        override fun getCount(): Int = mListFragment.size
    }
}