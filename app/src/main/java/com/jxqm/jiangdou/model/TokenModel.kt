package com.jxqm.jiangdou.model

/**
 * Created By bhx On 2019/9/9 0009 14:10
 */
data class TokenModel(val access_token: String, val token_type:String,val refresh_token:String,val expires_in:Int,val scope:String)
