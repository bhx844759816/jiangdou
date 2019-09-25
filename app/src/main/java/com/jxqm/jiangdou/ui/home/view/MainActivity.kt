package com.jxqm.jiangdou.ui.home.view

import android.Manifest
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.LogUtils
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.home.vm.MainViewModel
import com.jxqm.jiangdou.ui.login.view.GuideActivity
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.startActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_job_details.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 *主页面
 * Created by Administrator on 2019/8/20.
 */
class MainActivity : BaseDataActivity<MainViewModel>() {
    override fun getEventKey(): Any = Constants.EVENT_KEY_MAIN

    private var mListFragment = arrayListOf<Fragment>()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        requestPermission()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        mListFragment.add(HomeFragment())
        mListFragment.add(WorkFragment())
        mListFragment.add(MyFragment())
        myViewPage.offscreenPageLimit = 3
        myViewPage.adapter = MyPageAdapter(supportFragmentManager)
        myBottomNavigationBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
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

    /**
     * 请求权限 SD卡存储和拍照权限
     */
    private fun requestPermission() {
        val disposable =
            RxPermissions(this).request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION
            ).subscribe {
            }
        addDisposable(disposable)

    }

}