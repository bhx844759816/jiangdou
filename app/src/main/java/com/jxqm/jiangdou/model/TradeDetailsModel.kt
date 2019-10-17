package com.jxqm.jiangdou.model
data class TradeDetailsModel(
    val amount: Int,
    val balance: Int,
    val id: Long,
    val orderNo: String,
    val summary: String,
    val tradeNo: String,
    val tradeTime: String,
    val tradeType: String,
    val tradeTypeCode: String,
    val tradeUser: String,
    val tradeUserId: Int,
    val user: String,
    val userId: Long
)