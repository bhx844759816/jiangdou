package com.jxqm.jiangdou.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bhx.common.adapter.rv.listener.OnItemClickListener
import com.bhx.common.mvvm.BaseMVVMFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.model.JobTypeModel
import com.jxqm.jiangdou.model.SwpierModel
import com.jxqm.jiangdou.ui.city.SelectCity
import com.jxqm.jiangdou.ui.home.adapter.HomeAdapter
import com.jxqm.jiangdou.ui.home.model.*
import com.jxqm.jiangdou.ui.home.vm.HomeViewModel
import com.jxqm.jiangdou.ui.job.view.JobCompanyListActivity
import com.jxqm.jiangdou.ui.job.view.JobDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created By bhx On 2019/9/5 0005 14:38
 */

class HomeFragment : BaseMVVMFragment<HomeViewModel>() {
    override fun getEventKey(): Any = Constants.EVENT_KEY_MAIN_HOME

    private val mHomeModelList = arrayListOf<HomeModel>()
    private var isRefresh = true
    private lateinit var mAdapter: HomeAdapter
    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = HomeAdapter(mContext)
        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View?, holder: ViewHolder?, position: Int) {
                startActivity<JobDetailsActivity>()
            }

            override fun onItemLongClick(view: View?, holder: ViewHolder?, position: Int): Boolean {
                return false
            }
        })
        mAdapter.setDataList(mHomeModelList)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = mAdapter

        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mViewModel.getRecommend(isRefresh)
        }
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            mViewModel.getRecommend(isRefresh)
        }

        tvLocationCity.clickWithTrigger {
            startActivity<SelectCity>()
        }

        llSearch.clickWithTrigger {
            startActivity<JobCompanyListActivity>()
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        //注册获取轮播图
        registerObserver(Constants.TAG_GET_HOME_SWIPER, List::class.java).observe(this, Observer {
            val list = it as List<SwpierModel>
            val homeSwipeModel = HomeSwipeModel(list)
            mHomeModelList.add(homeSwipeModel)
            mAdapter.notifyDataSetChanged()
        })
        //获取兼职类型
        registerObserver(Constants.TAG_GET_HOME_SWIPER, List::class.java).observe(this, Observer {
            val list = it as List<JobTypeModel>
            val jobTypeModel = HomeJobTypeModel(list)
            mHomeModelList.add(jobTypeModel)
            mHomeModelList.add(HomeJobHelpModel())
            mHomeModelList.add(HomeJobDetailsTitleModel())
            //在添加个
            mAdapter.notifyDataSetChanged()
        })
        //获取推荐兼职列表
        registerObserver(Constants.TAG_GET_HOME_SWIPER, List::class.java).observe(this, Observer {
            val list = it as List<JobDetailsModel>
            val homeJobDetailsModelList = mutableListOf<HomeJobDetailsModel>()
            list.forEach { jobDetailsModel ->
                val homeJobDetailsModel = HomeJobDetailsModel(jobDetailsModel)
                homeJobDetailsModelList.add(homeJobDetailsModel)
            }
            if (isRefresh) {
                swipeRefreshLayout.finishRefresh()
                val iterator = mHomeModelList.iterator()
                while (iterator.hasNext()) {
                    val homeModel = iterator.next()
                    if (homeModel.type == 4) {
                        iterator.remove()
                    }
                }
                mHomeModelList.addAll(homeJobDetailsModelList)
            } else {
                swipeRefreshLayout.finishLoadMore()
                mHomeModelList.addAll(homeJobDetailsModelList)
            }
            mAdapter.notifyDataSetChanged()
        })

    }

    /**
     * 请求数据 -
     */
    override fun onFirstUserVisible() {
        mViewModel.getHomeData()
    }

}