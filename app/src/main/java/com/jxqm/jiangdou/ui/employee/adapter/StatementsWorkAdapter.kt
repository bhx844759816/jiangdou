package com.jxqm.jiangdou.ui.employee.adapter

import android.content.Context
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.model.StatementsWorkItem

/**
 * Created By bhx On 2019/9/2 0002 17:53
 */
class StatementsWorkAdapter(context: Context) : MultiItemTypeAdapter<StatementsWorkItem>(context) {

    init {
        addItemViewType(object  : ItemViewType<StatementsWorkItem> {
            override fun getItemViewLayoutId(): Int {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun isItemClickable(): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun isViewForType(item: StatementsWorkItem?, position: Int): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun convert(holder: ViewHolder?, t: StatementsWorkItem?, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }
}