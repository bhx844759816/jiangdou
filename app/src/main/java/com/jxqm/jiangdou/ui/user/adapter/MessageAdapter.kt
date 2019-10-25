package com.jxqm.jiangdou.ui.user.adapter

import android.app.Activity
import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bhx.common.event.LiveBus
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.MessageModel
import com.jxqm.jiangdou.ui.attestation.view.CompanyAttestationActivity
import com.jxqm.jiangdou.ui.employer.view.EmployRecordActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * 消息列表适配器
 */
class MessageAdapter(context: Context) : MultiItemTypeAdapter<MessageModel>(context) {
    private val seeCodeArray = arrayOf(2, 7, 9, 6) //2 企业认证失败  7自动结算通知  9商家结算异常 6待结算通知
    private var mSettleCallBack: (() -> Unit)? = null

    init {
        addItemViewType(object : ItemViewType<MessageModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_message_layout
            override fun isItemClickable(): Boolean = false
            override fun isViewForType(item: MessageModel?, position: Int): Boolean = true
            override fun convert(holder: ViewHolder?, messageModel: MessageModel, position: Int) {
                holder?.let {
                    val llContentParent = it.getView<LinearLayout>(R.id.llContentParent)
                    val tvTime = it.getView<TextView>(R.id.tvTime)
                    val tvTitle = it.getView<TextView>(R.id.tvTitle)
                    val tvContent = it.getView<TextView>(R.id.tvContent)
                    val tvSee = it.getView<TextView>(R.id.tvSee)
                    tvTime.text = messageModel.createTime
                    tvTitle.text = messageModel.title
                    tvContent.text = messageModel.content
                    tvSee.isVisible = seeCodeArray.contains(messageModel.notifyTypeCode)
                    llContentParent.clickWithTrigger {
                        when (messageModel.notifyTypeCode) {
                            2 -> {//2企业认证失败
                                mContext.startActivity<CompanyAttestationActivity>()
                            }
                            7 -> {//7自动结算通知
                                mContext.startActivity<EmployRecordActivity>(
//                                "jobId" to messageModel.refId.toString(),
                                    "jobId" to "10",
                                    "type" to 4
                                )
                            }
                            9 -> {//9商家结算异常
                                LiveBus.getDefault().postEvent(
                                    Constants.EVENT_KEY_MAIN,
                                    Constants.TAG_STATUS_WORK_FRAGMENT,
                                    true
                                )
                                (mContext as Activity).finish()
                            }
                            6 -> {//6待结算通知
                                mContext.startActivity<EmployRecordActivity>(
//                                "jobId" to messageModel.refId.toString(),
                                    "jobId" to "10",
                                    "type" to 3
                                )
                            }
                        }
                    }

                }
            }

        })
    }
}