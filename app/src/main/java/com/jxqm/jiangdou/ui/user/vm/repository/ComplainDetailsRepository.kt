package com.jxqm.jiangdou.ui.user.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * 投诉
 */
class ComplainDetailsRepository : BaseEventRepository() {

    /**
     * 更新用户信息
     */
    fun complaintJob(files: List<File>, paramsMaps: Map<String, Any>) {
        val mulBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .apply {
                files.forEach {
                    val body = RequestBody.create(MediaType.parse("multipart/form-data"), it)
                    addFormDataPart("imagesFiles", it.name, body)
                }
                paramsMaps.map {
                    addFormDataPart(it.key, it.value.toString())
                }
            }.build()
        addDisposable(
            apiService.complaintJob(mulBody)
                .action {
                    sendData(
                        Constants.EVENT_COMPLAIN_DETAILS,
                        Constants.TAG_JOB_COMPLAIN_SUCCESS,
                        it
                    )
                }
        )
    }
}