package com.jxqm.jiangdou.ui.user.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.google.gson.Gson
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.TradeDetailsModel
import com.jxqm.jiangdou.ui.user.view.TradeDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * 交易明细
 */
class TradeDetailsItemAdapter(context: Context) : MultiItemTypeAdapter<TradeDetailsModel>(context) {

    init {
        addItemViewType(object : ItemViewType<TradeDetailsModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_trade_details_item_layout

            override fun isItemClickable(): Boolean = false
            override fun isViewForType(item: TradeDetailsModel?, position: Int): Boolean = true
            override fun convert(holder: ViewHolder?, model: TradeDetailsModel, position: Int) {
                holder?.let {
                    val llParent = it.getView<LinearLayout>(R.id.llParent)
                    val tvTradeTime = it.getView<TextView>(R.id.tvTradeTime)
                    val tvTradeAmount = it.getView<TextView>(R.id.tvTradeAmount)
                    val tvTradeType = it.getView<TextView>(R.id.tvTradeType)
                    val tvTradeBalance = it.getView<TextView>(R.id.tvTradeBalance)
                    val ivTradeState = it.getView<ImageView>(R.id.ivTradeState)
                    tvTradeTime.text = model.tradeTime
                    tvTradeAmount.text =
                        if (model.amount > 0) "+${model.amount}" else model.amount.toString()
                    tvTradeType.text = model.tradeType
                    tvTradeBalance.text = model.balance.toString()
                    if (model.amount > 0) {
                        ivTradeState.setBackgroundResource(R.drawable.icon_settlement_add)
                    } else {
                        ivTradeState.setBackgroundResource(R.drawable.icon_settlement_reduce)
                    }

                    llParent.clickWithTrigger {
                        mContext.startActivity<TradeDetailsActivity>(
                            "TradeDetailsModel" to Gson().toJson(
                                model
                            )
                        )
                    }
                }
            }

        })
    }
}