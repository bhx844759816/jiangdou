package com.jxqm.jiangdou.http

/**
 * Created by Administrator on 2019/9/8.
 */

data class HttpResult<T>( val code: String, val message: String , val data: T)