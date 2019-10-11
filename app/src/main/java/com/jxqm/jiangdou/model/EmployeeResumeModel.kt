package com.jxqm.jiangdou.model

import com.jxqm.jiangdou.base.CommonConfig

/**
 * 雇员简历对象
 * Created By bhx On 2019/9/27 0027 14:34
 */
data class EmployeeResumeModel(
    val id: Long,
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
    val settleCode:Int,
    val settle: String = "",
    val settleTime: String = "",
    val signTime: String = "",
    val name: String = "",
    val gender: String = "",
    val avatar: String = "",
    val tel: String = "",
    val title: String = "",
    val birthday: String = "",
    val age: String = "",
    val area: String = "",
    val areaCode: String = "",
    val date: String = "",//工作日期
    val startTime: String = "",//工作开始时间
    val endTime: String = "",//工作结束时间
    val count: String = "", //工作时间
    val amount: String = "", //总共需要结算得
    val settleAmount: String = "", //雇主自己结算得
    var isChecked: Boolean
) : CommonConfig()
//
//"date":"2019-10-17","startTime":"08:30",
//"endTime":"16:30","count":8.0,
//"amount":24,"settleAmount":null