package com.jxqm.jiangdou.model

/**
 * 工作简历ID
 * Created by Administrator on 2019/9/29.
 */
data class JobEmployeeModel(
    val address: String,
    val addressDetail: String,
    val area: String,
    val areaCode: Any,
    val arrival: String,
    val arrivalCode: Int,
    val arrivalTime: String,
    val city: String,
    val contact: String,
    val content: String,
    val createTime: String,
    val datesJson: String,
    val email: String,
    val employerId: Int,
    val employerName: String,
    val logo: String,
    val endTime: String,
    val frozenDeposit: Any,
    val gender: String,
    val genderCode: Int,
    val id: Int,
    val jobWorkId: Int,
    val invalid: Boolean,
    val jobResumeId: Int,
    val jobTypeId: Int,
    val jobTypeName: String,
    val latitude: Double,
    val longitude: Double,
    val mapImg: String,
    val offer: String,
    val offerCode: Int,
    val offerNum: Int,
    val offerTime: String,
    val pageNo: Any,
    val pageSize: Any,
    val province: String,
    val recruitNum: Int,
    val salary: Int,
    val settle: String,
    val settleCode: Int,
    val settleTime: Any,
    val count: String, //工作时长
    val amount: String, //结算金额
    val settleAmount: String,//待结算金额
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


//"count":null,"amount":null,"settleAmount":null}
