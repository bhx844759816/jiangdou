package com.jxqm.jiangdou.model

import com.jxqm.jiangdou.base.CommonConfig
import com.jxqm.jiangdou.ui.publish.model.TimeRangeModel

/**
 * Created by Administrator on 2019/9/19.
 */


data class JobDetailsModel(
    val address: String,
    val addressDetail: String,
    val area: String,
    val city: String,
    val province: String,
    val areaCode: String,
    val contact: String,
    val content: String,
    val createTime: String,
    val datesJson: String,
    val email: String,
    val employerId: String,//雇主ID
    val employerName: String,//雇主名称
    val frozenDeposit: String,
    val gender: String,
    val genderCode: Int,
    val id: Int,
    val jobTypeId: Int,
    val jobTypeName: String,
    val latitude: Double,
    val longitude: Double,
    val mapImg: String,
    val pageNo: Any,
    val pageSize: Any,
    val recruitNum: Int,
    val signNum: Int,
    val offerNum: Int,
    val salary: Int,
    val status: String,
    val sign: Boolean,
    val statusCode: Int,
    val tel: String,
    val timesJson: String,
    val title: String,
    val typeImg: Any,
    val typeImgUrl: String,
    val userId: Int,
    val isCollection: Boolean,
    var isChecked: Boolean //是否选中
) : CommonConfig()

//{
//    "id": 3,
//    "pageNo": null,
//    "pageSize": null,
//    "jobTypeId": 21,
//    "title": "哈开机",
//    "content": "UK噜噜噜8图谋戒律牧某某头目M8他M8头摸头",
//    "salary": 36,
//    "genderCode": 1,
//    "gender": "男",
//    "contact": "呼呼呼呼女女女女",
//    "tel": "19137629693",
//    "email": "alllaal",
//    "address": "木华广场",
//    "latitude": 34.800702925920056,
//    "longitude": 113.81593690824388,
//    "statusCode": 0,
//    "status": "待提交",
//    "areaCode": null,
//    "area": "金水区",
//    "province": "河南省",
//    "city": "郑州市",
//    "addressDetail": "挪钭奴隶科恩哦lol",
//    "mapImg": "static/assert/upload/537931c4-9a2a-46f2-a7e3-b3efe056e450.png",
//    "typeImg": "static/assert/upload/25aaa234-6263-46b5-8e98-358118de0e4d.png",
//    "userId": 1,
//    "employerId": 5,
//    "recruitNum": 25,
//    "frozenDeposit": null,
//    "datesJson": "[]",
//    "timesJson": "[]",
//    "createTime": "2019.10.07 18:37",
//    "employerName": null,
//    "jobTypeName": "派单",
//    "typeImgUrl": null
//}