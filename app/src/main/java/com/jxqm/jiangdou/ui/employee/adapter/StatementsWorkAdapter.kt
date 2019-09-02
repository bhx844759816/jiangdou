package com.jxqm.jiangdou.ui.employee.adapter

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.StatementsWorkItem

/**
 *
 * Created By bhx On 2019/9/2 0002 17:53
 */
class StatementsWorkAdapter(context: Context) : MultiItemTypeAdapter<StatementsWorkItem>(context) {

    init {
        addItemViewType(object : ItemViewType<StatementsWorkItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_statements_work_item
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: StatementsWorkItem?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, statementWorkItem: StatementsWorkItem, position: Int) {
                val tvJobMoneyDelete = holder?.getView<TextView>(R.id.tvJobMoneyDelete)
                val tvJobMoney = holder?.getView<TextView>(R.id.tvJobMoney)
                val tvRefuseTips = holder?.getView<TextView>(R.id.tvRefuseTips)
                val ivRefuse = holder?.getView<ImageView>(R.id.ivRefuse)
                val rlNormalState = holder?.getView<RelativeLayout>(R.id.rlNormalState)
                when {
                    statementWorkItem.isNormal -> {
                        //正常状态
                        tvJobMoney?.visibility = View.VISIBLE
                        tvJobMoneyDelete?.visibility = View.VISIBLE
                        rlNormalState?.visibility = View.VISIBLE
                        ivRefuse?.visibility = View.GONE
                        tvJobMoneyDelete?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    statementWorkItem.isAccept -> {
                        //接受
                        tvRefuseTips?.visibility = View.GONE
                        rlNormalState?.visibility = View.GONE
                        ivRefuse?.visibility = View.GONE
                        tvJobMoneyDelete?.paint?.flags = Paint.ANTI_ALIAS_FLAG
                        tvJobMoneyDelete?.text = "共赚1000币"
                    }
                    else -> {
                        //拒绝
                        ivRefuse?.visibility = View.VISIBLE
                        tvRefuseTips?.visibility = View.VISIBLE
                        tvJobMoney?.visibility = View.GONE
                        tvJobMoneyDelete?.visibility = View.GONE
                        rlNormalState?.visibility = View.GONE
                    }
                }
            }

        })

    }
}