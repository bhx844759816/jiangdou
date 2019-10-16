package com.jxqm.jiangdou.ui.home.view

import android.Manifest
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.LogUtils
import com.bhx.common.utils.ToastUtils
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.home.vm.MainViewModel
import com.jxqm.jiangdou.ui.login.view.GuideActivity
import com.jxqm.jiangdou.ui.login.view.LoginActivity
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.startActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_job_details.*
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.content.ContextCompat.getSystemService
import com.bhx.common.utils.FileUtils
import com.bhx.common.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.http.AppUpdateManager
import com.jxqm.jiangdou.http.HttpResult
import com.jxqm.jiangdou.model.AppUpdateModel
import com.jxqm.jiangdou.ui.login.view.LoadingActivity
import com.jxqm.jiangdou.ui.publish.model.TimeRangeModel
import com.jxqm.jiangdou.view.dialog.LoadingDialog
import com.vector.update_app.UpdateAppBean
import com.vector.update_app_kotlin.check
import com.vector.update_app_kotlin.updateApp


/**
 *主页面
 * Created by Administrator on 2019/8/20.
 */
class MainActivity : BaseDataActivity<MainViewModel>() {
    override fun getEventKey(): Any = Constants.EVENT_KEY_MAIN

    private var mListFragment = arrayListOf<Fragment>()
    private var mLastPosition = 0

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
        myBottomNavigationBar.setTabSelectedListener(object :
            BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
                LogUtils.i("onTabUnselected$position")
            }

            override fun onTabSelected(position: Int) {
                if (position == 1) {
                    if (MyApplication.instance().userModel == null) {
                        ToastUtils.toastShort("未登录请先登录")
                        startActivity<LoginActivity>()
                        myBottomNavigationBar.selectTab(mLastPosition, false)
                    } else {
                        myViewPage.currentItem = position
                    }
                } else {
                    myViewPage.currentItem = position
                    mLastPosition = position
                }

            }

        })
    }

    private fun checkAppUpdate() {
        /**
         * 更新app版本
         */
        updateApp(Api.HTTP_BASE_URL + Api.GET_APP_UPDATE, AppUpdateManager())
        {
            isPost = false
            themeColor = 0xff82A2FE.toInt()
            hideDialogOnDownloading()
        }.check {
            parseJson {
                val response = it
                val appUpdateModel = Gson().fromJson<HttpResult<AppUpdateModel>>(
                    response!!,
                    object : TypeToken<HttpResult<AppUpdateModel>>() {
                    }.type
                )
                if (appUpdateModel.code == "0") {
                    val localVersionCode = Utils.getLocalVersion(this@MainActivity)
                    val isNeedUpdate = if (appUpdateModel.data.versionCode > localVersionCode)
                        "Yes" else "no"
                    UpdateAppBean()
                        .setUpdate(isNeedUpdate)
                        //（必须）新版本号，
                        .setNewVersion(appUpdateModel.data.versionName)
                        //（必须）下载地址
                        .setApkFileUrl(appUpdateModel.data.downloadUrl)
                        //（必须）更新内容
                        .setUpdateLog(appUpdateModel.data.modifyContent)
                        //大小，不设置不显示大小，可以不设置
                        .setTargetSize(FileUtils.formatFileSize(appUpdateModel.data.apkSize.toLong()))
                        //是否强制更新，可以不设置
                        .setConstraint(false)
                        //设置md5，可以不设置
                        .setNewMd5(appUpdateModel.data.apkMd5)
                } else {
                    UpdateAppBean()
                        .setUpdate("no")
                }
            }
        }
    }

    inner class MyPageAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment = mListFragment[position]
        override fun getCount(): Int = mListFragment.size

    }

    override fun protectApp() {
        startActivity<LoadingActivity>()
        finish()
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