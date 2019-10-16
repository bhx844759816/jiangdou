package com.jxqm.jiangdou.http

import android.annotation.SuppressLint
import android.util.Log
import android.util.Pair
import com.bhx.common.http.RetrofitManager
import com.bhx.common.http.downland.DownlandFileObservable
import com.bhx.common.http.downland.DownlandManager
import com.bhx.common.http.downland.FileInfo
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.config.Constants
import com.vector.update_app.HttpManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import okhttp3.RequestBody
import java.io.File

@SuppressLint("CheckResult")
class AppUpdateManager : HttpManager {

    init {
        DownlandManager.init(Api.HTTP_BASE_URL)
    }

    private var apiService: ApiService =
        RetrofitManager.getInstance().createApiService(ApiService::class.java)

    override fun download(
        url: String,
        path: String,
        fileName: String,
        callback: HttpManager.FileCallback
    ) {
        val fileInfo = FileInfo()
        fileInfo.fileName = fileName
        fileInfo.filePath = path
        DownlandManager.downland(
            url,
            fileInfo
        )
            .compose(applySchedulers())
            .doOnSubscribe {
                callback.onBefore()
            }.subscribeOn(AndroidSchedulers.mainThread())
            .compose(applySchedulers())
            .subscribe({
                if (it is FileInfo) {
                    callback.onResponse(File(it.filePath, it.fileName))
                } else {
                    val pair = it as Pair<Long, Long>
                    val downloadSize = pair.first
                    val totalSize = pair.second
                    val progress = (downloadSize * 1.0f) / totalSize
                    LogUtils.i("progress$progress")
                    callback.onProgress(progress, totalSize)
                }
            }, {
                callback.onError(it.localizedMessage)
            })
//
//        apiService.download("https://ali-fir-pro-binary.fir.im/7a317e1d24b3dc6a6c5d3e791ef4e35436069cf2.apk?auth_key=1571203851-0-0-80a9aada818ebb5e84a2bea22c0ab441")
//            .compose(applySchedulers())
//            .doOnSubscribe {
//                callback.onBefore()
//            }.subscribeOn(AndroidSchedulers.mainThread())
//            .flatMap {
//                val fileInfo = FileInfo()
//                fileInfo.fileName = fileName
//                fileInfo.filePath = path
//                Observable.create(DownlandFileObservable(it, fileInfo)).compose(applySchedulers())
//            }.compose(applySchedulers()).subscribe({
//                if (it is File) {
//                    callback.onResponse(it)
//                } else {
//                    val pair = it as Pair<Long, Long>
//                    val downloadSize = pair.first
//                    val totalSize = pair.second
//                    val progress = ((downloadSize * 1.0f) / totalSize) * 100
//                    callback.onProgress(progress, totalSize)
//                }
//            }, {
//                callback.onError(it.localizedMessage)
//            })
    }


    override fun asyncGet(
        url: String,
        params: MutableMap<String, String>,
        callBack: HttpManager.Callback
    ) {
        apiService.asyncGet(url, params)
            .compose(applySchedulers())
            .subscribe({
                LogUtils.i("asyncGet response=$it")
                callBack.onResponse(it.string())
            }, {
                LogUtils.i("asyncGet error=$it")
                callBack.onError(it.localizedMessage)
            })


    }

    override fun asyncPost(
        url: String,
        params: MutableMap<String, String>,
        callBack: HttpManager.Callback
    ) {


        val requestBodyMaps = mutableMapOf<String, RequestBody>()
        params.forEach {
            val key = it.key
            val requestBody =
                RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), it.value)
            requestBodyMaps[key] = requestBody
        }
        apiService.asyncPost(url, requestBodyMaps)
            .compose(applySchedulers())
            .subscribe({
                callBack.onResponse(it.string())
            }, {
                callBack.onError(it.localizedMessage)
            })

    }
}