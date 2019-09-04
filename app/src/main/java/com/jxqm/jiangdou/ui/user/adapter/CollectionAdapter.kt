package com.jxqm.jiangdou.ui.user.adapter

import android.content.Context
import com.bhx.common.adapter.rv.CommonAdapter
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.CollectionItem

/**
 * Created By bhx On 2019/9/4 0004 15:53
 */
class CollectionAdapter(context: Context) : CommonAdapter<CollectionItem>(context) {
    override fun itemLayoutId(): Int = R.layout.adapter_collection

    override fun convert(holder: ViewHolder?, t: CollectionItem?, position: Int) {
    }
}