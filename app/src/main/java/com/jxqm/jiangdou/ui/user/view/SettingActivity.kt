package com.jxqm.jiangdou.ui.user.view

import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.FileUtils
import com.bhx.common.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.http.AppUpdateManager
import com.jxqm.jiangdou.http.HttpResult
import com.jxqm.jiangdou.model.AppUpdateModel
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.view.dialog.LoadingDialog
import com.vector.update_app.UpdateAppBean
import com.vector.update_app_kotlin.check
import com.vector.update_app_kotlin.updateApp
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * 设置界面
 * Created By bhx On 2019/9/5 0005 09:25
 */
class SettingActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initView() {
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        rlBack.clickWithTrigger { finish() }
        tvCurrentVersion.text = Utils.getLocalVersionName(this)
        rlCheckUpdate.clickWithTrigger {
            checkAppUpdate()
        }
    }

    private fun checkAppUpdate(){
        /**
         * 更新app版本
         */
        updateApp(Api.HTTP_BASE_URL + Api.GET_APP_UPDATE, AppUpdateManager())
        {
            isPost = false
            themeColor = 0xff82A2FE.toInt()
            hideDialogOnDownloading()
        }.check {
            onBefore {  LoadingDialog.show(this@SettingActivity) }
            parseJson {
                val response = it
                val appUpdateModel = Gson().fromJson<HttpResult<AppUpdateModel>>(
                    response!!,
                    object : TypeToken<HttpResult<AppUpdateModel>>() {
                    }.type
                )
                if (appUpdateModel.code == "0") {
                    val localVersionCode = Utils.getLocalVersion(this@SettingActivity)
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
            onAfter {
                LoadingDialog.dismiss(this@SettingActivity)
            }
        }
    }
}