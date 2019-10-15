package com.jxqm.jiangdou.ui.job.view

import android.view.KeyEvent
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.SPUtils
import com.bhx.common.utils.ToastUtils
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.hideKeyboard
import com.jxqm.jiangdou.model.HotSearchModel
import com.jxqm.jiangdou.ui.job.vm.JobSearchViewModel
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity
import kotlinx.android.synthetic.main.activity_job_search.*

/**
 * 工作搜索界面
 * Created by Administrator on 2019/8/17.
 */
class JobSearchActivity : BaseDataActivity<JobSearchViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_job_search

    override fun getEventKey(): Any = Constants.EVENT_KEY_JOB_SEARCH

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        jobSearchBack.clickWithTrigger {
            finish()
        }
        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchKey = etSearch.text.toString()
                if (searchKey.isNotEmpty()) {
                    writeSearchHistory(searchKey)
                    startActivity<JobCompanyListActivity>("SearchKey" to searchKey)
                    etSearch.hideKeyboard()
                    return@setOnEditorActionListener true
                } else {
                    ToastUtils.toastShort("请输入搜索关键词")
                }
            }
            return@setOnEditorActionListener false
        }
    }

    override fun initData() {
        mViewModel.getHotSearchList()
    }

    override fun dataObserver() {
        registerObserver(Constants.GET_HOT_SEARCH_LIST_SUCCESS, List::class.java).observe(this,
            Observer {
                val hotSearchList = it as List<HotSearchModel>
                val searchKeyList = hotSearchList.map { model ->
                    model.keyword
                }
                addHotJobSearch(searchKeyList)
            })
    }

    /**
     * 热门搜索
     */
    private fun addHotJobSearch(searchKeyList: List<String>) {
        flHotSearchParent.removeAllViews()
        searchKeyList.forEach {
            val textView = TextView(this)
            val layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT
            )
            layoutParams.rightMargin = DensityUtil.dip2px(this, 10f)
            layoutParams.bottomMargin = DensityUtil.dip2px(this, 5f)
            textView.setTextColor(resources.getColor(R.color.text_default))
            textView.setBackgroundResource(R.drawable.shape_half_empty_circle_bg)
            textView.textSize = DensityUtil.dip2px(this, 5f).toFloat()
            textView.text = it
            textView.setPadding(
                DensityUtil.dip2px(this, 20f),
                DensityUtil.dip2px(this, 10f),
                DensityUtil.dip2px(this, 20f),
                DensityUtil.dip2px(this, 10f)
            )
            textView.layoutParams = layoutParams
            textView.clickWithTrigger {
                startActivity<JobCompanyListActivity>("SearchKey" to textView.text.toString().trim())
            }
            flHotSearchParent.addView(textView)
        }
    }

    /**
     * 初始化搜索历史
     */
    private fun initHistorySearch() {
        val searchKeyContent = SPUtils.get(this, Constants.SEARCH_KEY, "") as String
    }

    /**
     * 写入搜索历史
     */
    private fun writeSearchHistory(searchKey: String) {
        val searchKeyContent = SPUtils.get(this, Constants.SEARCH_KEY, "") as String
        val stringBuilder = StringBuilder()
        if (searchKeyContent.isNotEmpty()) {
            stringBuilder.append(searchKey)
        } else {
            stringBuilder.append(searchKeyContent)
            stringBuilder.append("|")
            stringBuilder.append(searchKey)
        }


    }

}