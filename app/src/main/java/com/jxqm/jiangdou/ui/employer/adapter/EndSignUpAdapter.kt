package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EndSignUpItem
import com.jxqm.jiangdou.model.WaitExamineJobItem

/**
 * 雇主- 截至报名
 * Created by Administrator on 2019/9/2.
 */
class EndSignUpAdapter(context: Context) : MultiItemTypeAdapter<EndSignUpItem>(context) {
    init {
        addItemViewType(object : ItemViewType<EndSignUpItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_end_sign_up_item
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: EndSignUpItem?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, endSignUpItem: EndSignUpItem, position: Int) {
                val ivRefund = holder?.getView<ImageView>(R.id.ivRefund)
                val tvPass = holder?.getView<TextView>(R.id.tvPass)
                val tvSignUpRecord = holder?.getView<TextView>(R.id.tvSignUpRecord)
                if (endSignUpItem.isPass) {
                    tvPass?.setBackgroundResource(R.drawable.shape_wait_pay_bg)
                    tvPass?.text = "已结束"
                    tvSignUpRecord?.text = "招聘记录"
                    ivRefund?.visibility = View.GONE
                } else {
                    tvPass?.setBackgroundResource(R.drawable.shape_no_pass_bg)
                    tvPass?.text = "未通过"
                    tvSignUpRecord?.text = "删除"
                    ivRefund?.visibility = View.VISIBLE
                }

            }
        })
    }
}