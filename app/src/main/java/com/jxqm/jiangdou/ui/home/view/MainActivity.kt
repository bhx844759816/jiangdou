package com.jxqm.jiangdou.ui.home.view

import android.Manifest
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.home.vm.MainViewModel
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.startActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer
import com.bhx.common.event.LiveBus
import com.bhx.common.utils.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.http.AppUpdateManager
import com.jxqm.jiangdou.model.HttpResult
import com.jxqm.jiangdou.model.AppUpdateModel
import com.jxqm.jiangdou.ui.login.view.LoadingActivity
import com.jxqm.jiangdou.ui.login.view.LoginActivity
import com.jxqm.jiangdou.view.dialog.AppUpdateDialog
import com.vector.update_app.UpdateAppBean
import com.vector.update_app.UpdateAppManager
import com.vector.update_app.utils.AppUpdateUtils
import com.vector.update_app_kotlin.check
import com.vector.update_app_kotlin.download
import com.vector.update_app_kotlin.updateApp
import io.reactivex.Observable
import java.io.File
import java.util.concurrent.TimeUnit


/**
 *主页面
 * Created by Administrator on 2019/8/20.
 */
class MainActivity : BaseDataActivity<MainViewModel>() {
    override fun getEventKey(): Any = Constants.EVENT_KEY_MAIN

    private var mListFragment = arrayListOf<Fragment>()
    private var mLastPosition = 0
    private var exitTime: Long = 0

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        super.initView()

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

                        myViewPage.setCurrentItem(position, false)
                    }
                } else {
                    myViewPage.setCurrentItem(position, false)
                    mLastPosition = position
                }

            }

        })

        //跳转到工作台 雇员
        registerObserver(
            Constants.TAG_STATUS_WORK_FRAGMENT,
            Boolean::class.java
        ).observe(this,
            Observer {
                myBottomNavigationBar.selectTab(1)
                LiveBus.getDefault().postEvent(
                    Constants.EVENT_KEY_WORK,
                    Constants.TAG_STATUS_EMPLOYEE_SETTLEMENT,
                    true
                )
            })
        //登录成功切换到工作台
        registerObserver(
            Constants.TAG_SIGN_UP_SUCCESS_STATUS,
            Boolean::class.java
        ).observe(this,
            Observer {
                myBottomNavigationBar.selectTab(1)
            })
        doCheckAppUpdate()
    }

    override fun dataObserver() {

    }

    /**
     * 延迟1s检查更新
     */
    private fun doCheckAppUpdate() {
        addDisposable(Observable.timer(1000, TimeUnit.MILLISECONDS)
            .subscribe {
                checkAppUpdate()
            })
    }

    /**
     * 检查App更新
     */
    private fun checkAppUpdate() {
        updateApp(Api.HTTP_BASE_URL + Api.GET_APP_UPDATE, AppUpdateManager())
        {
            isPost = false
            themeColor = 0xff82A2FE.toInt()
            isIgnoreDefParams = true
        }.check {
            parseJson {
                val response = it
                val appUpdateModel = Gson().fromJson<HttpResult<AppUpdateModel>>(
                    response!!,
                    object : TypeToken<HttpResult<AppUpdateModel>>() {
                    }.type
                )
                if (appUpdateModel.code == "0" && appUpdateModel.data != null) {
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
                        .setTargetSize(appUpdateModel.data.apkSize)
                        //是否强制更新，可以不设置
                        .setConstraint(false)
                        //设置md5，可以不设置
                        .setNewMd5(appUpdateModel.data.apkMd5)
                } else {
                    UpdateAppBean()
                        .setUpdate("no")
                }
            }
            hasNewApp { updateApp, updateAppManager ->
                showAppUpdateDialog(updateApp, updateAppManager)
            }
        }
    }

    /**
     * 展示app更新的Dialog
     */
    private fun showAppUpdateDialog(updateApp: UpdateAppBean, updateAppManager: UpdateAppManager) {
        AppUpdateDialog.show(
            this,
            updateApp.newVersion,
            updateApp.targetSize,
            updateApp.updateLog
        ) {
            updateAppManager.download {
                this.onFinish {
                    true
                }
                this.onInstallAppAndAppOnForeground {
                    AppUpdateUtils.installApp(this@MainActivity,it)
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

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtils.toastShort("再按一次退出程序")
            exitTime = System.currentTimeMillis()
        } else {
            AppManager.getAppManager().AppExit(this)
        }
    }

}