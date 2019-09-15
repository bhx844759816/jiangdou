package com.jxqm.jiangdou.ui.attestation.vm.repository

import com.bhx.common.mvvm.BaseRepository
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created By bhx On 2019/8/9 0009 12:00
 */
class PeopleAttestationRepository : BaseEventRepository() {

    fun submit(fileMaps: Map<String, File>, paramsMaps: Map<String, String>) {
        val mulBody = MultipartBody.Builder().apply {
            fileMaps.forEach {
                val body = RequestBody.create(MediaType.parse("multipart/form-data"), it.value)
                val part = MultipartBody.Part.createFormData(it.key, it.value.name, body)
                addPart(part)
            }
            paramsMaps.map {
                addFormDataPart(it.key, it.value)
            }
        }.build()

        apiService.submitAttestation(mulBody).action {
            LogUtils.i("提交成功$it")
        }

    }

}