package com.jxqm.jiangdou.base

import com.google.gson.Gson

/**
 * Created by Administrator on 2019/9/19.
 */

open class CommonConfig {

    companion object {

        fun <T> fromJson(json: String, clazz: Class<T>): T {
            return Gson().fromJson(json, clazz)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}