package com.jxqm.jiangdou.model

/**
 * 雇员简历对象的包装类
 * Created By bhx On 2019/9/27 0027 14:48
 */
data class EmployeeResumeModelWrap(
    val records: List<EmployeeResumeModel>, val pageNo: Int,
    val pageSize: Int, val total: Int
)