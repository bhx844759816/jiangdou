package com.jxqm.jiangdou.model

/**
 * Created by Administrator on 2019/9/24.
 */
data class SwpierModel(
    val id: String, val title: String, val targetUrl: String,
    val imageUrl: String, val imageType: String, val orderNum: Int, val eanble: Boolean
)

//{"id":1,"title":"app-banner-1","targetUrl":"#","imageUrl":
//    "static/assert/upload/26f2c1f3-a327-4b50-b01c-7522bbdb7576.png",
//    "imageType":"home-swiper","orderNum":1,"enable":true}