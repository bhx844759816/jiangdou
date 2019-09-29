package com.jxqm.jiangdou.model

/**
 * 异常状态雇员列表(截至报名 失效)
 * Created by Administrator on 2019/9/29.
 */
data class JobEmployeeExceptionModel(
    val address: String,
    val area: String,
    val areaCode: String,
    val arrival: String,
    val arrivalTime: Any,
    val contact: String,
    val content: String,
    val createTime: String,
    val datesJson: String,
    val email: String,
    val employerId: Int,
    val employerName: Any,
    val endTime: Any,
    val frozenDeposit: Any,
    val gender: String,
    val genderCode: Int,
    val id: Int,
    val invalid: Boolean,
    val jobTypeId: Int,
    val jobTypeName: String,
    val latitude: Double,
    val longitude: Double,
    val mapImg: String,
    val offer: String,
    val offerNum: Int,
    val offerTime: String,
    val pageNo: Any,
    val pageSize: Any,
    val recruitNum: Int,
    val salary: Int,
    val settlement: String,
    val settlementTime: Any,
    val signNum: Int,
    val signTime: String,
    val startTime: String,
    val status: String,
    val statusCode: Int,
    val tel: String,
    val timesJson: String,
    val title: String,
    val typeImg: String,
    val typeImgUrl: Any,
    val userId: Int
) : JobEmployeeBaseModel()

