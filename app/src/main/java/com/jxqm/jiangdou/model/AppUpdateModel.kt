package com.jxqm.jiangdou.model

/**
 * app更新Model
 */
data class AppUpdateModel(
    val apkMd5: String,
    val apkSize: String,
    val current: Boolean,
    val downloadUrl: String,
    val flag: Int,
    val id: Long,
    val modifyContent: String,
    val updateTime: String,
    val versionCode: Int,
    val versionName: String
)