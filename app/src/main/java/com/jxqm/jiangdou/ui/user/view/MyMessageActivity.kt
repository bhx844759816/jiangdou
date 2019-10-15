package com.jxqm.jiangdou.ui.user.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fengchen.uistatus.UiStatusController
import com.fengchen.uistatus.annotation.UiStatus
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.model.MessageModel
import com.jxqm.jiangdou.ui.user.adapter.MessageAdapter
import com.jxqm.jiangdou.ui.user.vm.MyMessageViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_my_message.*

/**
 * 我的消息
 */
class MyMessageActivity : BaseDataActivity<MyMessageViewModel>() {
    private var isRefresh: Boolean = true
    private lateinit var mUiStatusController: UiStatusController
    private var mMessageList = mutableListOf<MessageModel>()
    private lateinit var mAdapter: MessageAdapter


    override fun getLayoutId(): Int = R.layout.activity_my_message

    override fun getEventKey(): Any = Constants.EVENT_MY_MESSAGE

    override fun initData() {
        mViewModel.getMessageList(isRefresh)
    }

    override fun dataObserver() {
        //获取数据成功
        registerObserver(
            Constants.TAG_GET_MESSAGE_LIST_SUCCESS,
            List::class.java
        ).observe(this,
            Observer {
                val list = it as List<MessageModel>
                if (isRefresh) {
                    if (list.isEmpty()) {
                        mUiStatusController.changeUiStatus(UiStatus.EMPTY)
                    } else {
                        mUiStatusController.changeUiStatus(UiStatus.CONTENT)
                        if (list.size >= 10) {
                            swipeRefreshLayout.setEnableLoadMore(true)
                        }
                    }
                    mMessageList.clear()
                    mMessageList.addAll(list)
                    mAdapter.setDataList(mMessageList)
                    if (swipeRefreshLayout.isRefreshing)
                        swipeRefreshLayout.finishRefresh()
                } else {
                    swipeRefreshLayout.finishLoadMore()
                    if (list.isEmpty()) {
                        swipeRefreshLayout.setNoMoreData(true)
                    } else {
                        mMessageList.addAll(list)
                        mAdapter.setDataList(mMessageList)
                    }
                }
            })

        registerObserver(Constants.TAG_GET_MESSAGE_LIST_ERROR, String::class.java).observe(
            this,
            Observer {
                if (swipeRefreshLayout.isRefreshing)
                    swipeRefreshLayout.finishRefresh()
                mUiStatusController.changeUiStatus(UiStatus.NETWORK_ERROR)
            })
    }

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))
        mUiStatusController = UiStatusController.get().bind(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = MessageAdapter(this)
        recyclerView.adapter = mAdapter
        swipeRefreshLayout.setEnableLoadMore(false)
        swipeRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mViewModel.getMessageList(isRefresh)
        }
        swipeRefreshLayout.setOnLoadMoreListener {
            isRefresh = false
            mViewModel.getMessageList(isRefresh)
        }
        back.clickWithTrigger {
            finish()
        }
    }

}