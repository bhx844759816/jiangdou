package com.jxqm.jiangdou.ui.user.vm.repository

import com.bhx.common.mvvm.BaseRepository
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.handleResult
import com.jxqm.jiangdou.http.handleResultForLoadingDialog
import io.reactivex.functions.Consumer
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created By bhx On 2019/8/19 0019 14:07
 */
class MyResumeRepository : BaseEventRepository() {
    /**
     * 获取学历列表
     */
    fun getEduList() {
        addDisposable(
            apiService.getEduList().compose(handleResult())
                .subscribe({
                    LogUtils.i("获取学历列表$it")
                    sendData(Constants.EVENT_KEY_MY_RESUME, Constants.TAG_GET_EDU_LIST_RESULT, it)
                }, {
                    LogUtils.i("获取学历列表error$it")
                })
        )
    }

    /**
     * 上传用户简历
     */
    fun upLoadResume(paramsMap: Map<String, String>, fileMaps: Map<String, File>, fileList: List<File>) {
        val mulBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .apply {
                fileMaps.forEach {
                    val body = RequestBody.create(MediaType.parse("multipart/form-data"), it.value)
                    addFormDataPart(it.key, it.value.name, body)
                }
                fileList.forEach {
                    val body = RequestBody.create(MediaType.parse("multipart/form-data"), it)
                    addFormDataPart("photos", it.name, body)
                }
                paramsMap.map {
                    addFormDataPart(it.key, it.value)
                }
            }.build()
        addDisposable(
            apiService.uploadUserResume(mulBody).action {
                sendData(Constants.EVENT_KEY_MY_RESUME, Constants.TAG_UPLOAD_RESUME_RESULT, true)
            }
        )
    }

    /**
     * 更新用户简历
     */
    fun updateResume(paramsMap: Map<String, String>, fileMaps: Map<String, File>, fileList: List<File>) {
        val mulBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .apply {
                fileMaps.forEach {
                    val body = RequestBody.create(MediaType.parse("multipart/form-data"), it.value)
                    addFormDataPart(it.key, it.value.name, body)
                }
                fileList.forEach {
                    val body = RequestBody.create(MediaType.parse("multipart/form-data"), it)
                    addFormDataPart("photos", it.name, body)
                }
                paramsMap.map {
                    addFormDataPart(it.key, it.value)
                }
            }.build()
        addDisposable(
            apiService.updateUserResume(mulBody).action {
                sendData(Constants.EVENT_KEY_MY_RESUME, Constants.TAG_UPLOAD_RESUME_RESULT, true)
            }
        )
    }

    /**
     * 获取用户简历
     */
    fun getUserResume() {
        addDisposable(
            apiService.getUserResume()
                .action {
                    sendData(Constants.EVENT_KEY_MY_RESUME, Constants.TAG_GET_USER_RESUME_RESULT, it)
                    LogUtils.i("获取用户简历$it")
                }
        )
    }
}