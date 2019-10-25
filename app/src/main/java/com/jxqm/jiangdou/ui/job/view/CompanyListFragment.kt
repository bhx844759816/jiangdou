package com.jxqm.jiangdou.ui.job.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.mvvm.BaseMVVMFragment
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.fengchen.uistatus.listener.OnCompatRetryListener
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.AttestationStatusModel
import com.jxqm.jiangdou.ui.job.adapter.CompanyListAdapter
import com.jxqm.jiangdou.ui.job.vm.CompanyListViewModel
import kotlinx.android.synthetic.main.fragment_company_list.*

/**
 * Created by Administrator on 2019/8/17.
 */
class CompanyListFragment : BaseMVVMFragment<CompanyListViewModel>() {
    private lateinit var mUiStatusController: UiStatusController
    private lateinit var mAdapter: CompanyListAdapter
    private var mCompanyDetailList = mutableListOf<AttestationStatusModel>()
    private var mSearchKey: String? = null
    private var isRefresh = true
    override fun getLayoutId(): Int = R.layout.fragment_company_list

    override fun getEventKey(): Any = Constants.EVENT_KEY_SEARCH_COMPANY_LIST
    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        mSearchKey = arguments?.getString("SearchKey", "")
        //搜索公司列表成功
        registerObserver(Constants.TAG_GET_SEARCH_COMPANY_LIST_SUCCESS, List::class.java).observe(
            this,
            Observer {
                val list = it as List<AttestationStatusModel>
                if (isRefresh) {
                    mCompanyDetailList.clear()
                    mCompanyDetailList.addAll(list)
                    if (mCompanyDetailList.isEmpty()) {
                        mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                    } else {
                        mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                    }
                    mAdapter.setDataList(mCompanyDetailList)
                    if (swipeRefreshLayout.isRefreshing) {
                        swipeRefreshLayout.finishRefresh()
                    }
                    swipeRefreshLayout.resetNoMoreData()
                } else {
                    if (list.isEmpty()) {
                        swipeRefreshLayout.finishLoadMoreWithNoMoreData()
                    } else {
                        swipeRefreshLayout.finishLoadMore()
                        mCompanyDetailList.addAll(list)
                        mAdapter.setDataList(mCompanyDetailList)
                    }
                }

            })
        //搜索公司列表失败
        registerObserver(Constants.TAG_GET_SEARCH_COMPANY_LIST_ERROR, String::class.java).observe(
            this,
            Observer {
                if(mCompanyDetailList.isEmpty()){
                    mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
                }
            })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUiStatusController = UiStatusController.get().bind(recyclerView)
        mAdapter = CompanyListAdapter(mContext)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = mAdapter
        mUiStatusController.onCompatRetryListener =
            OnCompatRetryListener { p0, p1, p2, p3 ->
                mUiStatusController.changeUiStatus(UiStatus.LOADING)
                isRefresh = true
                mSearchKey?.let {
                    isRefresh = true
                    mViewModel.getSearchCompanyList(it, isRefresh)
                }
            }
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mSearchKey?.let {
                isRefresh = true
                mViewModel.getSearchCompanyList(it, isRefresh)
            }
        }
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            mSearchKey?.let {
                isRefresh = true
                mViewModel.getSearchCompanyList(it, isRefresh)
            }
        }
    }


    override fun onFirstUserVisible() {
        mSearchKey?.let {
            mViewModel.getSearchCompanyList(it, isRefresh)
        }
    }

    fun startSearch(searchKey: String) {
        mSearchKey = searchKey
        mSearchKey?.let {
            isRefresh = true
            mViewModel.getSearchCompanyList(it, isRefresh)
        }
    }


    companion object {
        fun newInstance(searchKey: String): CompanyListFragment {
            val fragment = CompanyListFragment()
            val bundle = Bundle()
            bundle.putString("SearchKey", searchKey)
            fragment.arguments = bundle
            return fragment
        }
    }

}