package com.jxqm.jiangdou.model

data class PageModeWrap<T>(
    val records: List<T>, val pageNo: Int,
    val pageSize: Int, val total: Int
)