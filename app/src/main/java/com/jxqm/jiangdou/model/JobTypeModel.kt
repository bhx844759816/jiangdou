package com.jxqm.jiangdou.model

/**
 * 兼职类型 一级和二级
 * Created by Administrator on 2019/9/14.
 */
data class JobTypeModel(
    val id: Int, val parentId: Int, val code: String,
    val jobTypeName: String, val orderNum: Int,
    val enable: Boolean, val hot: Boolean,
    var isChecked: Boolean,
    val imgUrl: String, val jobTypes: List<JobTypeModel>
)