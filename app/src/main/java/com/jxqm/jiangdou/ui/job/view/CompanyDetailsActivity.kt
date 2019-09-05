package com.jxqm.jiangdou.ui.job.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.CompanyDetailsModel
import com.jxqm.jiangdou.ui.job.adapter.CompanyDetailsAdapter
import com.jxqm.jiangdou.ui.job.vm.CompanyDetailsViewModel
import com.jxqm.jiangdou.ui.user.view.UserComplainActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_company_details.*

/**
 * 公司详情
 * Created by Administrator on 2019/8/18.
 */
class CompanyDetailsActivity : BaseDataActivity<CompanyDetailsViewModel>() {
    private val mData = mutableListOf<CompanyDetailsModel>()
    private lateinit var mAdapter: CompanyDetailsAdapter
    override fun getLayoutId(): Int = R.layout.activity_company_details
    override fun getEventKey(): Any = Constants.EVENT_KEY_COMPANY_DETAILS

    override fun initView() {
        super.initView()
        val modelTop = CompanyDetailsModel(0, Any())
        mData.add(modelTop)
        val modelItem1 = CompanyDetailsModel(1, Any())
        val modelItem2 = CompanyDetailsModel(1, Any())
        mData.add(modelItem1)
        mData.add(modelItem2)
        mAdapter = CompanyDetailsAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = mAdapter
        mAdapter.setDataList(mData)

        llComplainCompany.clickWithTrigger {
            startActivity<UserComplainActivity>()
        }
        toolBar.setNavigationOnClickListener {
            finish()
        }
    }
}