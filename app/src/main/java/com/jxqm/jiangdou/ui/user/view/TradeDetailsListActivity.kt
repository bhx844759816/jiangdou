package com.jxqm.jiangdou.ui.user.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.utils.DateUtils
import com.bhx.common.utils.ToastUtils
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.TradeDetailsModel
import com.jxqm.jiangdou.ui.user.adapter.TradeDetailsItemAdapter
import com.jxqm.jiangdou.ui.user.vm.TradeDetailsListViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_trade_details_list.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 交易明细
 */
class TradeDetailsListActivity : BaseDataActivity<TradeDetailsListViewModel>() {

    private lateinit var mAdapter: TradeDetailsItemAdapter
    private val mTradeDetailsModelList = mutableListOf<TradeDetailsModel>()
    private var mTimePickView: TimePickerView? = null
    private val mYear = Calendar.getInstance().get(Calendar.YEAR)
    private val mMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
    override fun getLayoutId(): Int = R.layout.activity_trade_details_list

    override fun getEventKey(): Any = Constants.EVENT_TRADE_DETAILS_LIST

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        mAdapter = TradeDetailsItemAdapter(this)
        rvTradeList.layoutManager = LinearLayoutManager(this)
        rvTradeList.adapter = mAdapter
        tvSelectDate.text =
            "${mYear}年${mMonth}月"
        tvSelectDate.clickWithTrigger {
            showTimePickedDialog()
        }
        rlBack.clickWithTrigger {
            finish()
        }
    }

    /**
     * 展示日期选择对话框
     */
    private fun showTimePickedDialog() {
        if (mTimePickView == null) {
            mTimePickView = TimePickerBuilder(this, OnTimeSelectListener { date, _ ->
                run {
                    val format = SimpleDateFormat("yyyy-MM")
                    val times = format.format(date).split("-")
                    val format2 = SimpleDateFormat("yyyy年MM月")
                    tvSelectDate.text = format2.format(date)
                    mViewModel.getTradeDetailsList(times[0], times[1])

                }
            }).setType(booleanArrayOf(true, true, false, false, false, false))
                .isCyclic(true)
                .build()
        }
        if (!mTimePickView!!.isShowing) {
            mTimePickView!!.show()
        }
    }

    override fun initData() {
        mViewModel.getTradeDetailsList(
            mYear.toString(),
            mMonth.toString()
        )
    }

    override fun dataObserver() {
        registerObserver(Constants.TAG_GET_TRADE_DETAILS_LIST_SUCCESS, List::class.java).observe(
            this,
            Observer {
                val list = it as List<TradeDetailsModel>
                mTradeDetailsModelList.clear()
                mTradeDetailsModelList.addAll(list)
                mAdapter.setDataList(mTradeDetailsModelList)
            })
    }
}