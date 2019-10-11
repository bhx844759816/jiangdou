package com.jxqm.jiangdou.model

import android.os.Parcelable
import com.jxqm.jiangdou.base.CommonConfig
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Created by Administrator on 2019/9/18.
 */
data class AttestationStatusModel(
    val id: String,
    val userId: String,
    val employerName: String,
    val areaCode: String,
    val area: String,
    val city: String,
    val province: String,
    val address: String,
    val addressDetail: String,
    val mapImg: String,
    val statusCode: Int,
    val status: String,
    val empType: String,
    val qylx: String,
    val hyfl: String,
    val rygm: String,
    val qylxName: String,
    val hyflName: String,
    val rygmName: String,
    val introduction: String,
    val latitude: Double,
    val longitude: Double,
    val idcardFront: String,
    val idcardBack: String,
    val businessLicense: String,
    val contact: String,
    val duty: String,
    val idcard: String,
    val alipay: String,
    val tel: String
) : CommonConfig()

//"data": {
//    "id": 1146712789046591501,
//    "userId": 1171430763342856196,
//    "employerName": "信息我",
//    "areaCode": null,
//    "area": "思达数码大厦",
//    "address": "木木木哦哦木头欧诺",
//    "mapImg": "assert/employer/cb0c9909-d765-47df-ad30-661d6020cd96.png",
//    "statusCode": 1,
//    "status": "审核中",
//    "empType": null,
//    "qylx": 38,
//    "hyfl": null,
//    "rygm": 45,
//"qylxName": "全民所有制",
//"hyflName": "智能硬件",
//"rygmName": "500-999人"
//    "introduction": "哦哟哟哦哟哦哟9我木木木哦哟哦哟9我头某某",
//    "latitude": 35.0,
//    "longitude": 113.69869905613666,
//    "idcardFront": "assert/employer/63d1021a-69ed-4c4a-9b8e-33b25334361c.png",
//    "idcardBack": "assert/employer/dce142e4-c373-4bdc-b33a-f2a2dba0a908.png",
//    "businessLicense": "assert/employer/a6fee194-c0e9-4cd4-b196-c12f0a877261.png",
//    "contact": "欧to欧牟",
//    "idcard": "555666988",
//    "alipay": "8668686868686",
//    "tel": null
//}