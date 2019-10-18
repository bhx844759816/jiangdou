package com.jxqm.jiangdou.model

/**
 * Created By bhx On 2019/9/9 0009 17:53
 */

data class UserModel(
    val avatar: String,
    val balance: String,
    val phone: String,
    val rankPoints: String,
    val username: String,
    val nick: String,
    val gender: String,
    val genderCode: Int,
    val perfectionDegree: String //简历完善度
)
//"data": {
//    "avatar": "string",//头像
//    "balance": 0, //余额
//    "phone": "string",//手机号
//    "rankPoints": 0,//收藏
//    "username": "string"
//}

//"username": "19137629693",
//"nick": "小豆青年",
//"phone": "19137629693",
//"avatar": null,
//"balance": 1000000,
//"rankPoints": 0,
//"perfectionDegree": 30,
//"gender": "女",
//"genderCode": null