package com.jxqm.jiangdou.ui.home.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bhx.common.adapter.rv.BaseSwipeRvFragment
import com.bhx.common.adapter.rv.LoadMoreAdapter
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bhx.common.adapter.rv.listener.OnItemClickListener
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
import com.jxqm.jiangdou.ui.job.view.JobSearchActivity
import com.jxqm.jiangdou.ui.publish.view.JobPreviewActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 首页Fragment
 * Created by Administrator on 2019/8/20.
 */
class HomeFragment : BaseSwipeRvFragment<HomeViewModel>() {
    private val mHomeModelList = arrayListOf<HomeModel>()
    override fun createRecycleViewAdapter(): MultiItemTypeAdapter<*> = HomeAdapter(mContext)

    override fun refresh() {
        Handler().postDelayed({
            rvViewHelper.dismissSwipeRefresh()
        }, 2000)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        llSearch.clickWithTrigger {
            startActivity<JobCompanyListActivity>()
        }
        tvLocationCity.clickWithTrigger {
            startActivity<SelectCity>()
        }
        
        rvViewHelper.adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View?, holder: ViewHolder?, position: Int) {
                context?.startActivity<JobPreviewActivity>()
            }

            override fun onItemLongClick(view: View?, holder: ViewHolder?, position: Int): Boolean {
                return false
            }
        })
    }

    override fun createItemDecoration(): RecyclerView.ItemDecoration? = null

    override fun loadMore() {
        Handler().postDelayed({
            rvViewHelper.setOnLoadMoreState(LoadMoreAdapter.LOADING_END)
        }, 2000)
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun getEventKey(): Any = Constants.EVENT_KEY_MAIN_HOME

    override fun isSupportPaging(): Boolean = true

    /**
     * 页面第一次显示的时候加载
     */
    override fun onFirstUserVisible() {
        super.onFirstUserVisible()
        mHomeModelList.add(HomeTopModel(0))
        mHomeModelList.add(HomeItemTypeModel(2))
        mHomeModelList.add(HomeItemModel(1))
        mHomeModelList.add(HomeItemModel(1))
        mHomeModelList.add(HomeItemModel(1))
        mHomeModelList.add(HomeItemModel(1))
        notifyAdapterDataSetChanged(mHomeModelList)
    }
}