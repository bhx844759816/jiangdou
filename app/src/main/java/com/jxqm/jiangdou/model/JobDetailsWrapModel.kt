package com.jxqm.jiangdou.model

/**
 * Created by Administrator on 2019/9/22.
 */
data class JobDetailsWrapModel(
    val records: List<JobDetailsModel>, val pageNo: Int,
    val pageSize: Int, val total: Int)