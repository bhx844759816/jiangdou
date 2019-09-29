package com.jxqm.jiangdou.model

/**
 * Created By bhx On 2019/9/29 0029 11:17
 */
data class JobSignModel(
    val id: String,
    val jobTypeId: String,
    val title: String,
    val content: String,
    val salary: String,
    val genderCode: String,
    val gender: String,
    val contact: String,
    val tel: String,
    val email: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val statusCode: String,
    val status: String,
    val areaCode: String,
    val area: String,
    val mapImg: String,
    val typeImg: String,
    val employerId: String,
    val userId: String,
    val recruitNum: String,
    val frozenDeposit: String,
    val datesJson: String,
    val timesJson: String,
    val createTime: String,
    val employerName: String,
    val jobTypeName: String,
    val typeImgUrl: String
) : JobSignModelBase(0)