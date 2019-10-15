package com.jxqm.jiangdou.ui.publish.vm.repository

import com.bhx.common.utils.LogUtils
import com.google.gson.Gson
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.model.JobDetailsModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * Created by Administrator on 2019/9/14.
 */
class JobPublishRepository : BaseEventRepository() {

    fun getAttestationDetails() {
        addDisposable(
            apiService.getAttestationStatus()
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        sendData(
                            Constants.EVENT_KEY_JOB_PUBLISH,
                            Constants.TAG_PUBLISH_JOB_ATTESTATION_DETAILS, it.data
                        )
                    }
                }, {
                    LogUtils.i("JobPublishRepository getAttestationDetails ${it.localizedMessage}")
                })
        )
    }

    fun publishJob(mapFilePath: String, paramsMap: MutableMap<String, String>) {
        val file = File(mapFilePath)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val part = MultipartBody.Part.createFormData("imageFile", file.name, requestFile)
        addDisposable(
            apiService.uploadFile(part)
                .compose(applySchedulers())
                .flatMap {
                    paramsMap["mapImg"] = it.data
                    LogUtils.i("upload file:${it.data}")
                    val body = RequestBody.create(
                        MediaType.parse("application/json;charset=UTF-8"),
                        Gson().toJson(paramsMap)
                    )
                    return@flatMap apiService.uploadPublishJob(body).compose(applySchedulers())
                }.action {
                    sendData(Constants.EVENT_KEY_JOB_PUBLISH, Constants.TAG_PUBLISH_JOB_SUCCESS, it)
                }
        )
    }

    fun updatePublishJob(mapFilePath: String, paramsMap: MutableMap<String, String>) {
        val file = File(mapFilePath)
        if (file.exists()) {
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val part = MultipartBody.Part.createFormData("imageFile", file.name, requestFile)
            apiService.uploadFile(part)
                .compose(applySchedulers())
                .flatMap {
                    paramsMap["mapImg"] = it.data
                    LogUtils.i("upload file:${it.data}")
                    val body = RequestBody.create(
                        MediaType.parse("application/json;charset=UTF-8"),
                        Gson().toJson(paramsMap)
                    )
                    return@flatMap apiService.updatePublishJob(body).compose(applySchedulers())
                }.action {
                    sendData(Constants.EVENT_KEY_JOB_PUBLISH, Constants.TAG_PUBLISH_JOB_SUCCESS, it)
                }
        } else {
            val body = RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                Gson().toJson(paramsMap)
            )
            apiService.updatePublishJob(body)
                .action {
                    sendData(Constants.EVENT_KEY_JOB_PUBLISH, Constants.TAG_PUBLISH_JOB_SUCCESS, it)
                }
        }
    }
}