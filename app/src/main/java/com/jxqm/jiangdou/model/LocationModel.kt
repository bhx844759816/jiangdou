package com.jxqm.jiangdou.model

/**
 * 定位得信息
 * Created by Administrator on 2019/10/10.
 */
data class LocationModel(
    val province: String,
    val city: String,
    val area: String,
    val latitude: Double,//经度
    val longitude: Double//维度
)