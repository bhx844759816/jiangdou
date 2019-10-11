package com.jxqm.jiangdou.ui.job.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.base.CommonConfig
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.AttestationStatusModel
import com.jxqm.jiangdou.model.CompanyDetailsModel
import com.jxqm.jiangdou.model.JobDetailsModel
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
    private var mAttestationStatusModel: AttestationStatusModel? = null
    override fun getLayoutId(): Int = R.layout.activity_company_details
    override fun getEventKey(): Any = Constants.EVENT_KEY_COMPANY_DETAILS

    override fun initView() {
        super.initView()
        mAdapter = CompanyDetailsAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = mAdapter
        intent.getStringExtra("AttestationStatusModel")?.let {
            mAttestationStatusModel = CommonConfig.fromJson(it, AttestationStatusModel::class.java)
            val modelTop = CompanyDetailsModel(0, mAttestationStatusModel!!)
            mData.add(modelTop)
            mAdapter.setDataList(mData)
            mViewModel.getCompanyJobList(mAttestationStatusModel!!.userId)
        }
        llComplainCompany.clickWithTrigger {
            startActivity<UserComplainActivity>()
        }
        toolBar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun dataObserver() {
        registerObserver(Constants.TAG_GET_COMPANY_JOB_LIST_SUCCESS, List::class.java).observe(this, Observer {
            val list = it as List<JobDetailsModel>
            list.forEach {
                val modelItem = CompanyDetailsModel(1, it)
                mData.add(modelItem)
            }
            mAdapter.setDataList(mData)
        })
    }
}