package com.jxqm.jiangdou.ui.user.view

import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.TradeDetailsModel
import com.jxqm.jiangdou.ui.user.vm.TradeDetailsViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_trade_details.*

/**
 * 交易详情
 */
class TradeDetailsActivity : BaseDataActivity<TradeDetailsViewModel>() {
    private var mTradeDetailsModel: TradeDetailsModel? = null
    override fun getLayoutId(): Int = R.layout.activity_trade_details

    override fun getEventKey(): Any = Constants.EVENT_TRADE_DETAILS

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        mTradeDetailsModel = Gson().fromJson(
            intent.getStringExtra("TradeDetailsModel"),
            TradeDetailsModel::class.java
        )
        rlBack.clickWithTrigger { finish() }
        mTradeDetailsModel?.let {
            tvTradeAmount.text = if (it.amount > 0) "+${it.amount}" else it.amount.toString()
            tvTradeType.text = it.tradeType
            tvTradeUser.text = it.tradeUser
            tvTradeOrder.text = it.orderNo
            tvTradeTime.text = it.tradeTime
            tvTradeSettleOrder.text = it.tradeNo
        }
    }
}