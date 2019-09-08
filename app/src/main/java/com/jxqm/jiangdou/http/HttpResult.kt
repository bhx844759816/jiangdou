package com.jxqm.jiangdou.http

/**
 * Created by Administrator on 2019/9/8.
 */

data class HttpResult<T>(val success: Boolean, val code: String, val msg: String , val data: T)