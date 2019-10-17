package com.jxqm.jiangdou.model

data class AccountModel(
    val avatar: String,
    val balance: Int,
    val enable: Boolean,
    val frozenBalance: Int,
    val id: Long,
    val integration: Int,
    val todayIncome: Int,
    val totalIncome: Int,
    val username: String,
    val withdrawBalance: Int,
    val yesterdayIncome: Int
)