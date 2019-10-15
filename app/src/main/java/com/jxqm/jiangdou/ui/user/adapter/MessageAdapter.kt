package com.jxqm.jiangdou.ui.user.adapter

import android.content.Context
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.MessageModel

/**
 * 消息列表适配器
 */
class MessageAdapter(context: Context) : MultiItemTypeAdapter<MessageModel>(context) {

    init {
        addItemViewType(object : ItemViewType<MessageModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_message_layout
            override fun isItemClickable(): Boolean = false
            override fun isViewForType(item: MessageModel?, position: Int): Boolean = true
            override fun convert(holder: ViewHolder?, messageModel: MessageModel, position: Int) {
                holder?.let {
                    val tvTime = it.getView<TextView>(R.id.tvTitle)
                    val tvTitle = it.getView<TextView>(R.id.tvTitle)
                    val tvContent = it.getView<TextView>(R.id.tvContent)
                    tvTime.text = messageModel.createTime
                    tvTitle.text = messageModel.title
                    tvContent.text = messageModel.content
                }
            }

        })
    }
}