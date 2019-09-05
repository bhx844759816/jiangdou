package com.jxqm.jiangdou

import com.bhx.common.BaseApplication
import com.jxqm.jiangdou.view.refresh.BaseRefreshHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout



/**
 * Created By bhx On 2019/9/5 0005 10:56
 */
class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)//全局设置主题颜色
            layout.setDisableContentWhenLoading(false)

            BaseRefreshHeader(context)
        }
    }
}