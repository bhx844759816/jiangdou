package com.jxqm.jiangdou.model

/**
 * 兼职类型
 * Created by Administrator on 2019/9/14.
 */
data class JobTypeModel(
    val id: Int, val parentId: Int, val code: String,
    val jobTypeName: String, val orderNum: Int,
    val enable: Boolean, val hot: Boolean,
    val imgUrl: String, val jobTypes: List<String>
)
//{
//    "id": 44,
//    "parentId": 6,
//    "code": "0604",
//    "jobTypeName": "其他",
//    "orderNum": 10,
//    "enable": true,
//    "hot": false,
//    "imgUrl": null,
//    "jobTypes": []
//}