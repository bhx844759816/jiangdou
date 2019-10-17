package com.jxqm.jiangdou.model

/**
 * 消息实体
 */
data class MessageModel(
    val id: Long,
    val userId: Long,
    val title: String,
    val content: String,
    val notifyType: String,
    val notifyTypeCode: Int,
    val refId: Long,
    val createTime: String
)
