package com.jxqm.jiangdou.ui.job.view

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.bhx.common.utils.SPUtils
import com.bhx.common.utils.ToastUtils
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
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
//                    writeSearchHistory(searchKey)
                    startActivity<JobCompanyListActivity>("SearchKey" to searchKey)
                    return@setOnEditorActionListener true
                } else {
                    ToastUtils.toastShort("请输入搜索关键词")
                }
            }
            return@setOnEditorActionListener false
        }
    }

    /**
     * 初始化搜索历史
     */
    private fun initHistorySearch() {

    }

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