package com.jxqm.jiangdou

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.bhx.common.BaseApplication
import com.bhx.common.http.RetrofitManager
import com.bhx.common.utils.AppManager
import com.bhx.common.utils.LogUtils
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
import com.jxqm.jiangdou.view.refresh.BaseRefreshHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout


/**
 * Created By bhx On 2019/9/5 0005 10:56
 */
class MyApplication : BaseApplication() {

    private var accessToken: String? = null
    var userModel: UserModel? = null //存储用户信息

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
        LogUtils.i("token$accessToken")
        //配置Http请求
        val builder = RetrofitManager.Builder()
            .setInterceptorList(listOf(TokenInterceptor()))
            .setBaseUrl(Api.HTTP_BASE_URL)
        RetrofitManager.getInstance().init(builder)
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)//全局设置主题颜色
            layout.setDisableContentWhenLoading(false)

            BaseRefreshHeader(context)
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
        SDKInitializer.setCoordType(CoordType.BD09LL)
    }

    fun saveToken(it: TokenModel) {
        accessToken = it.access_token
        SPUtils.put(this.applicationContext, Constants.ACCESS_TOKEN, accessToken)
        SPUtils.put(this.applicationContext, Constants.REFRESH_TOKEN, it.refresh_token)
    }

    fun getAccessToken(): String? = accessToken


}