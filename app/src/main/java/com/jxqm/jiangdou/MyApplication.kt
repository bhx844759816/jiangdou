package com.jxqm.jiangdou

import android.annotation.SuppressLint
import android.content.Context
import com.bhx.common.BaseApplication
import com.bhx.common.http.RetrofitManager
import com.bhx.common.utils.NetworkUtils
import com.fengchen.uistatus.UiStatusManager
import com.fengchen.uistatus.UiStatusNetworkStatusProvider
import com.fengchen.uistatus.annotation.UiStatus
import com.fengchen.uistatus.listener.OnRequestNetworkStatusEvent
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.view.refresh.BaseRefreshHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout


/**
 * Created By bhx On 2019/9/5 0005 10:56
 */
class MyApplication : BaseApplication() {

    private var access_token: String? = null

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
        //配置Http请求
        val builder = RetrofitManager.Builder()
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
        UiStatusNetworkStatusProvider.getInstance()
            .registerOnRequestNetworkStatusEvent { context -> NetworkUtils.isConnected(context) }
    }
}