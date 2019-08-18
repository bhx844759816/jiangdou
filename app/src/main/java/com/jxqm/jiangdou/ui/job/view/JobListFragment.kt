package com.jxqm.jiangdou.ui.job.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.bhx.common.adapter.rv.BaseSwipRvFragment
import com.bhx.common.adapter.rv.LoadMoreAdapter
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bhx.common.adapter.rv.listener.OnItemClickListener
import com.bhx.common.mvvm.BaseMVVMFragment
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.job.adapter.JobListAdapter
import com.jxqm.jiangdou.ui.job.vm.JobListViewModel
import com.jxqm.jiangdou.ui.job.widget.JobListSortPopupWindow
import com.jxqm.jiangdou.ui.publish.view.JobPreviewActivity
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.fragment_job_list.*

/**
 * Created by Administrator on 2019/8/17.
 */
class JobListFragment : BaseSwipRvFragment<JobListViewModel>() {

    private var mSortPopupWindow: JobListSortPopupWindow? = null
    private val mData = arrayListOf("", "", "", "", "", "", "", "", "", "")


    override fun createRecycleViewAdapter(): MultiItemTypeAdapter<*> = JobListAdapter(mContext)

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

    override fun getLayoutId(): Int = R.layout.fragment_job_list

    override fun getEventKey(): Any = Constants.EVENT_KEY_JOB_LIST

    override fun isSupportPaging(): Boolean = true

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)
        rgJobSortParent.check(R.id.rbSortDistance)
//        rbSortWhole.setOnCheckedChangeListener { _, p1 -> showSortPopupWindow() }

        rgJobSortParent.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.rbSortWhole -> {
                    showSortPopupWindow()
                }
                R.id.rbSortPublishNew -> {
                    mSortPopupWindow?.dismissPopup()
                }
                R.id.rbSortDistance -> {
                    mSortPopupWindow?.dismissPopup()
                }
            }
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

    private fun showSortPopupWindow() {
        if (mSortPopupWindow == null && activity != null) {
            mSortPopupWindow = JobListSortPopupWindow(activity!!)
            mSortPopupWindow!!.setCallBack { index, content ->
                rbSortWhole.text = content
            }
        }
        mSortPopupWindow!!.showPopup(rgJobSortParent)
    }

    override fun onFirstUserVisible() {
        notifyAdapterDataSetChanged(mData)

    }

}