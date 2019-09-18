package com.jxqm.jiangdou.ui.attestation.model

import com.google.gson.annotations.SerializedName

/**
 * 企业类型
 * Created By bhx On 2019/9/12 0012 15:40
 */
data class CompanyTypeModel(
    val id: Int,
    val codeType: String,
    val code: String,
    val codeName: String,
    @SerializedName("enanble")
    val enable: Boolean,
    val orderNum: Int
)

//{
//    "id": 34,
//    "codeType": "qylx",
//    "code": null,
//    "codeName": "合资",
//    "enanble": true,
//    "orderNum": 10
//}