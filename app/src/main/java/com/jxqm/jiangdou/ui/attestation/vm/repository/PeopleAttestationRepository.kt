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
        LogUtils.i("submit$paramsMaps")
        val mulBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .apply {
                fileMaps.forEach {
                    val body = RequestBody.create(MediaType.parse("multipart/form-data"), it.value)
//                val part = MultipartBody.Part.createFormData(it.key, it.value.name, body)
                    addFormDataPart(it.key, it.value.name, body)
                }
                paramsMaps.map {
                    addFormDataPart(it.key, it.value)
                }
            }.build()

        apiService.submitAttestation(mulBody).action {
            sendData(
                Constants.EVENT_KEY_PEOPLE_ATTESTATION,
                Constants.TAG_PEOPLE_ATTESTATION_SUBMIT_SUCCESS, true
            )
        }

    }


}