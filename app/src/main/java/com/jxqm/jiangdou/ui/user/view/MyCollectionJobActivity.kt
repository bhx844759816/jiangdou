package com.jxqm.jiangdou.ui.user.view

import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.base.BaseActivity
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.CollectionItem
import com.jxqm.jiangdou.ui.user.adapter.CollectionAdapter
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_my_collection.*
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener


/**
 * 我的收藏
 * Created By bhx On 2019/9/4 0004 15:27
 */
class MyCollectionJobActivity : BaseActivity() {
    private val mCollectionItems = arrayListOf<CollectionItem>()
    private lateinit var mAdapter: CollectionAdapter

    override fun getLayoutId(): Int = R.layout.activity_my_collection

    override fun initView() {
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        mCollectionItems.add(CollectionItem())
        mCollectionItems.add(CollectionItem())
        mCollectionItems.add(CollectionItem())
        mCollectionItems.add(CollectionItem())
        mAdapter = CollectionAdapter(this)
        mAdapter.setDataList(mCollectionItems)
        //
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        refreshLayout.setDisableContentWhenRefresh(true)
        refreshLayout.setOnRefreshListener { refreshLayout ->
            refreshLayout.finishRefresh(2000)//传入false表示刷新失败
        }

        myCollectionBack.clickWithTrigger {
            finish()
        }
    }
}