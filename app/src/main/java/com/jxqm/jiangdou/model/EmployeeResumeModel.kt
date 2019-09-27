package com.jxqm.jiangdou.model

import com.jxqm.jiangdou.base.CommonConfig

/**
 * 雇员简历对象
 * Created By bhx On 2019/9/27 0027 14:34
 */
data class EmployeeResumeModel(
val id: String = "",
val userId: String = "",
val resumId: String = "",
val pageNo: String = "",
val pageSize: String = "",
val jobId: String = "",
val offerCode: String = "",
val offer: String = "",
val offerTime: String = "",
val arrivalCode: String = "",
val arrival: String = "",
val arrivalTime: String = "",
val settlementCode: String = "",
val settlement: String = "",
val settlementTime: String = "",
val signTime: String = "",
val name: String = "",
val gender: String = "",
val avatar: String = "",
val tel: String = "",
val birthday: String = "",
val age: String = "",
val area: String = "",
val areaCode: String = "",
var isChecked: Boolean
) : CommonConfig()