package com.jxqm.jiangdou

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.multidex.MultiDex
import com.baidu.mapapi.SDKInitializer
import com.bhx.common.BaseApplication
import com.bhx.common.http.RetrofitManager
import com.bhx.common.utils.AppManager
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.NetworkUtils
import com.bhx.common.utils.SPUtils
import com.fengchen.uistatus.UiStatusManager
import com.fengchen.uistatus.UiStatusNetworkStatusProvider
import com.fengchen.uistatus.annotation.UiStatus
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.http.interceptor.TokenInterceptor
import com.jxqm.jiangdou.model.TokenModel
import com.jxqm.jiangdou.model.UserModel
import com.jxqm.jiangdou.model.AttestationStatusModel
import com.jxqm.jiangdou.model.LocationModel
import com.jxqm.jiangdou.utils.Utils_CrashHandler
import com.jxqm.jiangdou.view.refresh.BaseRefreshFooter
import com.jxqm.jiangdou.view.refresh.BaseRefreshHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshInitializer
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig


/**
 * Created By bhx On 2019/9/5 0005 10:56
 */
class MyApplication : BaseApplication() {

    var accessToken: String? = null
    var refreshToken: String? = null
    var userModel: UserModel? = null //存储用户信息
    var attestationViewModel: AttestationStatusModel? = null
    var locationModel: LocationModel? = null
    var isRecyclerFlag = -1
    var searchKeyWork = "" //工作台-搜索
    var searchKeyEmployRecord = ""//雇佣记录 - 搜索
    private val mActivityCallBack = object : ActivityLifecycleCallbacks {
        override fun onActivityPaused(p0: Activity) {
        }

        override fun onActivityStarted(p0: Activity) {
        }

        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        }

        override fun onActivityStopped(p0: Activity) {
        }

        override fun onActivityCreated(p0: Activity, p1: Bundle?) {
            AppManager.getAppManager().addActivity(p0)
        }

        override fun onActivityDestroyed(p0: Activity) {
            AppManager.getAppManager().removeActivity(p0)
        }

        override fun onActivityResumed(p0: Activity) {
        }

    }

    /**
     * 获取MyApplication得单例
     */
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: MyApplication? = null

        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        accessToken = SPUtils.get(this.applicationContext, Constants.ACCESS_TOKEN, "") as String?
        refreshToken = SPUtils.get(this.applicationContext, Constants.REFRESH_TOKEN, "") as String?
        //配置Http请求
        val builder = RetrofitManager.Builder()
            .setInterceptorList(listOf(TokenInterceptor()))
            .setBaseUrl(Api.HTTP_BASE_URL)
        RetrofitManager.getInstance().init(builder)
        SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
            //全局设置（优先级最低）
            layout.setEnableAutoLoadMore(true)
            layout.setEnableOverScrollDrag(false)
            layout.setEnableOverScrollBounce(true)
            layout.setEnableLoadMoreWhenContentNotFull(false)
            layout.setEnableScrollContentWhenRefreshed(true)
            layout.setEnableFooterFollowWhenNoMoreData(true) //显示底部布局
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
        }
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)//全局设置主题颜色
            layout.setDisableContentWhenLoading(false)

            BaseRefreshHeader(context)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)//全局设置主题颜色
            layout.setDisableContentWhenLoading(false)
            BaseRefreshFooter(context)

        }
        /**
         * 多状态布局统一设置
         */
        UiStatusManager.getInstance()
            .addUiStatusConfig(UiStatus.LOADING, R.layout.view_state_loading_layout)
            .addUiStatusConfig(UiStatus.EMPTY, R.layout.view_state_empty_layout)
            .addUiStatusConfig(
                UiStatus.NETWORK_ERROR,
                R.layout.view_state_network_error_layout,
                R.id.tvRetryAgain,
                null
            )
        //配置网络监听
        UiStatusNetworkStatusProvider.getInstance()
            .registerOnRequestNetworkStatusEvent { context -> NetworkUtils.isConnected(context) }
        registerActivityLifecycleCallbacks(mActivityCallBack)
        //百度地图初始化
        SDKInitializer.initialize(this)
        // UMENG初始化SDK
        UMConfigure.init(
            this,
            "5db023503fc1951923000ac2",
            "Umeng",
            UMConfigure.DEVICE_TYPE_PHONE,
            null
        )
        if (BuildConfig.DEBUG)
            UMConfigure.setLogEnabled(true)
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
//        PlatformConfig.setWeixin("wx2108d96dd642c746", "")
//        PlatformConfig.setQQZone("wx2108d96dd642c746", "")
    }

    fun saveToken(it: TokenModel) {
        accessToken = it.access_token
        refreshToken = it.refresh_token
        SPUtils.put(this.applicationContext, Constants.ACCESS_TOKEN, accessToken)
        SPUtils.put(this.applicationContext, Constants.REFRESH_TOKEN, it.refresh_token)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    fun doLogOut() {
        userModel = null
        attestationViewModel = null
        accessToken = null
        refreshToken = null
        SPUtils.put(this.applicationContext, Constants.ACCESS_TOKEN, "")
        SPUtils.put(this.applicationContext, Constants.REFRESH_TOKEN, "")
    }
}