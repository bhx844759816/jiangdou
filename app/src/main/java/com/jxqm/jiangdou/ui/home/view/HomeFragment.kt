package com.jxqm.jiangdou.ui.home.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bhx.common.adapter.rv.listener.OnItemClickListener
import com.bhx.common.base.BaseFragment
import com.bhx.common.base.BaseLazyFragment
import com.bhx.common.mvvm.BaseMVVMFragment
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.HomeItemModel
import com.jxqm.jiangdou.model.HomeItemTypeModel
import com.jxqm.jiangdou.model.HomeModel
import com.jxqm.jiangdou.model.HomeTopModel
import com.jxqm.jiangdou.ui.city.SelectCity
import com.jxqm.jiangdou.ui.home.adapter.HomeAdapter
import com.jxqm.jiangdou.ui.home.vm.HomeViewModel
import com.jxqm.jiangdou.ui.job.view.JobCompanyListActivity
import com.jxqm.jiangdou.ui.job.view.JobDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import com.jxqm.jiangdou.view.dialog.LoadingDialog
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created By bhx On 2019/9/5 0005 14:38
 */

class HomeFragment : BaseMVVMFragment<HomeViewModel>() {
    override fun getEventKey(): Any = Constants.EVENT_KEY_MAIN_HOME

    private val mHomeModelList = arrayListOf<HomeModel>()
    private lateinit var mAdapter: HomeAdapter
    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mHomeModelList.add(HomeTopModel(0))
        mHomeModelList.add(HomeItemTypeModel(2))
        mHomeModelList.add(HomeItemModel(1))
        mHomeModelList.add(HomeItemModel(1))
        mHomeModelList.add(HomeItemModel(1))
        mHomeModelList.add(HomeItemModel(1))
        mAdapter = HomeAdapter(mContext)

        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View?, holder: ViewHolder?, position: Int) {
                startActivity<JobDetailsActivity>()
            }

            override fun onItemLongClick(view: View?, holder: ViewHolder?, position: Int): Boolean {
                return false
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = mAdapter

        swipeRefreshLayout.setOnRefreshListener { refreshLayout ->
            refreshLayout.finishRefresh(2000)//传入false表示刷新失败
        }

        tvLocationCity.clickWithTrigger {
            startActivity<SelectCity>()
        }

        llSearch.clickWithTrigger {
            startActivity<JobCompanyListActivity>()
        }


    }

    /**
     * 请求数据 -
     */
    override fun onFirstUserVisible() {

    }

}