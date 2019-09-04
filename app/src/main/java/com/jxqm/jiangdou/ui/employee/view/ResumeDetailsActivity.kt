package com.jxqm.jiangdou.ui.employee.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.common.base.BaseActivity
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.employee.adapter.ResumeDetailsAdapter
import kotlinx.android.synthetic.main.activity_resume_details.*

/**
 * 简历详情
 * Created By bhx On 2019/9/4 0004 10:11
 */
class ResumeDetailsActivity : BaseActivity() {
    private val mPhotoList = arrayListOf("", "", "", "")
    private lateinit var mAdapter: ResumeDetailsAdapter
    override fun getLayoutId(): Int = R.layout.activity_resume_details
    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        mAdapter = ResumeDetailsAdapter(this)
        mAdapter.setDataList(mPhotoList)
        recyclerView.adapter = mAdapter
    }
}