package com.jxqm.jiangdou.ui.job.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bhx.common.adapter.rv.BaseSwipeRvFragment
import com.bhx.common.adapter.rv.LoadMoreAdapter
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bhx.common.adapter.rv.listener.OnItemClickListener
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.job.adapter.CompanyListAdapter
import com.jxqm.jiangdou.ui.job.vm.CompanyListViewModel
import com.jxqm.jiangdou.utils.startActivity

/**
 * Created by Administrator on 2019/8/17.
 */
class CompanyListFragment : BaseSwipeRvFragment<CompanyListViewModel>() {
    private val mData = arrayListOf("", "", "", "", "", "", "", "", "", "")
    override fun createRecycleViewAdapter(): MultiItemTypeAdapter<*> = CompanyListAdapter(mContext)

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        rvViewHelper.adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View?, holder: ViewHolder?, position: Int) {
                context?.startActivity<CompanyDetailsActivity>()
            }

            override fun onItemLongClick(view: View?, holder: ViewHolder?, position: Int): Boolean {
                return false
            }
        })
    }

    override fun refresh() {
        Handler().postDelayed({
            rvViewHelper.dismissSwipeRefresh()
        }, 2000)
    }

    override fun createItemDecoration(): RecyclerView.ItemDecoration? = null

    override fun loadMore() {
        Handler().postDelayed({
            rvViewHelper.setOnLoadMoreState(LoadMoreAdapter.LOADING_END)
        }, 2000)
    }

    override fun getLayoutId(): Int = R.layout.fragment_company_list

    override fun getEventKey(): Any = Constants.EVENT_KEY_COMPANY_LIST

    override fun isSupportPaging(): Boolean = true

    override fun onFirstUserVisible() {
        notifyAdapterDataSetChanged(mData)
    }

}