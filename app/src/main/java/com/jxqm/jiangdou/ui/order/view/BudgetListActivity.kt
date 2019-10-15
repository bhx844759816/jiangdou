package com.jxqm.jiangdou.ui.order.view

import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.order.vm.BudgetListViewModel

/**
 * 收支明细
 */
class BudgetListActivity : BaseDataActivity<BudgetListViewModel>() {
    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEventKey(): Any = Constants.EVENT_MY_BUDGET_LIST
}