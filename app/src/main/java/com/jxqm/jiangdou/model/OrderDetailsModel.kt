package com.jxqm.jiangdou.model

import com.jxqm.jiangdou.base.CommonConfig


/**
 * Created By bhx On 2019/9/25 0025 14:50
 */
data class OrderDetailsModel(
    val id: String, val employerId: String, val jobId: String, val orderNo: String,
    val salary: String, val count: String, val recruitNum: String, val amount: String,
    val datesJson: String, val timesJson: String,
    val payment: String, val paymentNo: String, val statusCode: String, val status: String, val commission: String,
    val paymentTime: String,val endTime: String,val createTime: String,
    val frozenDeposit: String
) : CommonConfig()
