package com.jxqm.jiangdou.ui.user.view

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.base.BaseActivity
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.CollectionItem
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.user.adapter.CollectionAdapter
import com.jxqm.jiangdou.ui.user.vm.MyCollectionJobViewModel
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_my_collection.*


/**
 * 我的收藏
 * Created By bhx On 2019/9/4 0004 15:27
 */
class MyCollectionJobActivity : BaseDataActivity<MyCollectionJobViewModel>() {

    private lateinit var mAdapter: CollectionAdapter
    private lateinit var mUiStatusController: UiStatusController
    private val mJobDetailModelList = mutableListOf<JobDetailsModel>()
    private var isRefresh = true
    private var isSelect = false
    override fun getLayoutId(): Int = R.layout.activity_my_collection
    override fun getEventKey(): Any = Constants.EVENT_KEY_MY_COLLECTION_JOB

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        mUiStatusController = UiStatusController.get().bind(recyclerView)
        mAdapter = CollectionAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        swipeRefreshLayout.setDisableContentWhenRefresh(true)
        swipeRefreshLayout.setEnableLoadMore(false)
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mViewModel.getCollectionList(isRefresh)
        }
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            mViewModel.getCollectionList(isRefresh)
        }
        myCollectionBack.clickWithTrigger {
            finish()
        }
        tvSelect.clickWithTrigger {
            changeJobListState()
        }
        mAdapter.mSelectCallBack = { position, isChecked ->
            mJobDetailModelList[position].isChecked = isChecked
            changeCollectionBtnState()
        }
        //取消收藏
        tvCancelCollection.clickWithTrigger {
            val idList = mJobDetailModelList.filter { it.isChecked }.map {
                it.id
            }
            mViewModel.cancelCollection(idList)
        }
        //全选按钮
        cbAllSelect.setOnCheckedChangeListener { _, isChecked ->
            mJobDetailModelList.forEach {
                it.isChecked = isChecked
            }
            cbAllSelect.text = if (isChecked) "全不选" else "全选"
            mAdapter.isSelect = true
            mAdapter.notifyDataSetChanged()
        }

    }

    /**
     * 判断当前列表是否有选中项
     */
    private fun changeCollectionBtnState() {
        val jobDetailsModel = mJobDetailModelList.find {
            it.isChecked
        }
        tvCancelCollection.isEnabled = jobDetailsModel != null
    }

    /**
     * 通过isSelect参数切换列表显示
     */
    private fun changeJobListState() {
        isSelect = !isSelect
        rlBottomParent.visibility = if (isSelect) View.VISIBLE else View.GONE
        if (isSelect) {
            tvSelect.text = "取消"
        } else {
            tvSelect.text = "选择"
        }
        mAdapter.isSelect = isSelect
        mAdapter.notifyDataSetChanged()
    }

    override fun initData() {
        mViewModel.getCollectionList(isRefresh)
    }

    override fun dataObserver() {
        //获取列表数据成功
        registerObserver(Constants.TAG_GET_MY_COLLECTION_LIST_SUCCESS, List::class.java).observe(
            this, Observer {
                val list = it as List<JobDetailsModel>
                if (isRefresh) {
                    if (list.isEmpty()) {
                        mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                    } else {
                        mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                        if (list.size >= 10) {
                            swipeRefreshLayout.setEnableLoadMore(true)
                        }
                    }
                    mJobDetailModelList.clear()
                    mJobDetailModelList.addAll(list)
                    mAdapter.setDataList(mJobDetailModelList)
                    if (swipeRefreshLayout.isRefreshing)
                        swipeRefreshLayout.finishRefresh()
                } else {
                    swipeRefreshLayout.finishLoadMore()
                    if (list.isEmpty()) {
                        swipeRefreshLayout.setNoMoreData(true)
                    } else {
                        mJobDetailModelList.addAll(list)
                        mAdapter.setDataList(mJobDetailModelList)
                    }
                }
            })
        //获取列表数据失败
        registerObserver(Constants.TAG_GET_MY_COLLECTION_LIST_ERROR, String::class.java).observe(
            this,
            Observer {
                mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
            })
        //
        registerObserver(Constants.TAG_CANCEL_COLLECTION_FINISH, Boolean::class.java).observe(this,
            Observer {
                isRefresh = true
                mViewModel.getCollectionList(isRefresh)
            })
    }
}