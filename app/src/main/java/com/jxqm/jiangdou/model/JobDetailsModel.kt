package com.jxqm.jiangdou.model

import com.jxqm.jiangdou.base.CommonConfig
import com.jxqm.jiangdou.ui.publish.model.TimeRangeModel

/**
 * Created by Administrator on 2019/9/19.
 */

class JobDetailsModel : CommonConfig() {
    var address: String = ""
    var area: String = ""
    var areaCode: String = ""
    var contact: String = ""
    var content: String = ""
    var createTime: String = ""
    var dates: List<String> = listOf()
    var datesJson: String = ""
    var email: String = ""
    var employerId: String = ""
    var gender: String = ""
    var id: String = ""
    var jobTypeId: String = ""
    var jobTypeValue: String = ""
    var latitude: String = ""
    var longitude: String = ""
    var mapImg: String = ""
    var recruitNum: String = ""
    var salary: String = ""
    var status: String? = null
    var statusCode: String = ""
    var tel: String = ""
    var title: String = ""
    var times: List<TimeRangeModel> = arrayListOf()
    var timesJson: String = ""
    var typeImg: String = ""
    var userId: String=""
    override fun toString(): String {
        return "JobDetailsModel(address='$address', area='$area', areaCode='$areaCode', contact='$contact', content='$content', createTime='$createTime', dates=$dates, datesJson='$datesJson', email='$email', employerId='$employerId', gender='$gender', id='$id', jobTypeId='$jobTypeId', jobTypeValue='$jobTypeValue', latitude='$latitude', longitude='$longitude', mapImg='$mapImg', recruitNum='$recruitNum', salary='$salary', status='$status', statusCode='$statusCode', tel='$tel', title='$title', times=$times, timesJson='$timesJson', typeImg='$typeImg', userId='$userId')"
    }


}



//{
//    "address": "string",
//    "area": "string",
//    "areaCode": "string",
//    "contact": "string",
//    "content": "string",
//    "createTime": "2019-09-19T14:51:10.432Z",
//    "dates": [recruitNum
//    "string"
//    ],
//    "datesJson": "string",
//    "email": "string",
//    "employerId": 0,
//    "gender": 0,
//    "id": 0,
//    "jobTypeId": 0,
//    "latitude": 0,
//    "longitude": 0,
//    "mapImg": "string",
//    "recruitNum": 0,
//    "salary": 0,
//    "status": "created",
//    "statusCode": 0,
//    "tel": "string",
//    "times": [
//    {
//        "end": "string",
//        "start": "string"
//    }
//    ],
//    "timesJson": "string",
//    "title": "string",
//    "typeImg": "string",
//    "userId": 0
//}
//]