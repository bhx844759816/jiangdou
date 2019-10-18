package com.jxqm.jiangdou.ui.user.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * 修改用户信息
 */
class ModifyUserDetailsRepository : BaseEventRepository() {
    /**
     * 更新用户信息
     */
    fun updateUserInfo(fileMaps: Map<String, File>, paramsMaps: Map<String, String>) {
        val mulBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .apply {
                fileMaps.forEach {
                    val body = RequestBody.create(MediaType.parse("multipart/form-data"), it.value)
                    addFormDataPart(it.key, it.value.name, body)
                }
                paramsMaps.map {
                    addFormDataPart(it.key, it.value)
                }
            }.build()
        addDisposable(
            apiService.updateUserInfo(mulBody)
                .action {
                    sendData(
                        Constants.EVENT_MODIFY_USER_DETAILS,
                        Constants.TAG_UPDATE_USER_SUCCESS,
                        it
                    )
                }
        )
    }

}