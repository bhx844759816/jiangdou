package com.jxqm.jiangdou.ui.user.model

/**
 * 简历对象
 * Created by Administrator on 2019/9/23.
 */
data class ResumeModel(
    val id: String, val userId: String, val name: String,
    val gender: String, val tel: String, val birthday: String, val star: String,
    val academic: String,//学历
    val height: String, val weight: String, val areaCode: String,
    val area: String, val content: String,
    val images: String, //图片列表
    val perfectionDegree: String,//简历完善度
    val age:Int,
    val genderCode:Int,
    val avatar: String//头像
)