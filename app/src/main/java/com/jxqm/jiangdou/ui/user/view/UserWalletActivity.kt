package com.jxqm.jiangdou.ui.user.view

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.AccountModel
import com.jxqm.jiangdou.ui.user.vm.UserWalletViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_user_wallet.*
import kotlinx.android.synthetic.main.activity_user_wallet.toolbar

/**
 * 我的钱包
 * Created By bhx On 2019/9/6 0006 10:37
 */
class UserWalletActivity : BaseDataActivity<UserWalletViewModel>() {
    override fun getEventKey(): Any = Constants.EVENT_USER_WALLET

    override fun getLayoutId(): Int = R.layout.activity_user_wallet

    override fun initView() {
        super.initView()
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        tvCashOutMoney.clickWithTrigger {
            startActivity<CashOutActivity>()
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
        rlTradeDetails.clickWithTrigger {
            startActivity<TradeDetailsListActivity>()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun dataObserver() {
        //获取账户信息成功
        registerObserver(Constants.TAG_GET_USER_ACCOUNT, AccountModel::class.java).observe(this,
            Observer {
                tvWalletMoney.text = it.balance.toString()
                tvWalletMoneyState.text =
                    "冻结${it.frozenBalance}币，可提现${it.withdrawBalance}币"
                tvYesterdayProfit.text = it.yesterdayIncome.toString()
                tvTodayProfit.text = it.todayIncome.toString()
                tvTotalProfit.text = it.totalIncome.toString()
                tvBalance.text = it.withdrawBalance.toString()
            })
    }

    override fun initData() {
        mViewModel.getAccount()
    }

}