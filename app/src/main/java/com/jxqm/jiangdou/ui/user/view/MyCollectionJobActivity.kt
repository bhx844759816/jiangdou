package com.jxqm.jiangdou.ui.user.view

import androidx.recyclerview.widget.LinearLayoutManager
import com.bhx.common.base.BaseActivity
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.CollectionItem
import com.jxqm.jiangdou.ui.user.adapter.CollectionAdapter
import kotlinx.android.synthetic.main.activity_my_collection.*

/**
 * 我的收藏
 * Created By bhx On 2019/9/4 0004 15:27
 */
class MyCollectionJobActivity : BaseActivity() {
    private val mCollectionItems = arrayListOf<CollectionItem>()
    private lateinit var mAdapter: CollectionAdapter
    override fun getLayoutId(): Int = R.layout.activity_my_collection
    override fun initView() {
        mCollectionItems.add(CollectionItem())
        mCollectionItems.add(CollectionItem())
        mCollectionItems.add(CollectionItem())
        mCollectionItems.add(CollectionItem())
        mAdapter = CollectionAdapter(this)
        mAdapter.setDataList(mCollectionItems)
        //
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
    }
}