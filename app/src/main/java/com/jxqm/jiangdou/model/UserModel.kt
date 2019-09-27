package com.jxqm.jiangdou.model

/**
 * Created By bhx On 2019/9/9 0009 17:53
 */

data class UserModel(
    val avatar: String,
    val balance: String,
    val phone: String,
    val rankPoints: String,
    val username: String
)
//"data": {
//    "avatar": "string",//头像
//    "balance": 0, //余额
//    "phone": "string",//手机号
//    "rankPoints": 0,//收藏
//    "username": "string"
//}