package com.jxqm.jiangdou.listener

/**
 * Created By bhx On 2019/8/8 0008 11:18
 */
interface OnJobPublishCallBack {

    fun jobTypNextStep()

    fun jobMessageNextStep()

    fun jobTimeNextStep()

    fun jobContactsNextStep()
}