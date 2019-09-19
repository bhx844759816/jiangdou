package com.jxqm.jiangdou.ui.publish.view

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bhx.common.base.BaseActivity
import com.bhx.common.utils.DensityUtil
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.CommonConfig
import com.jxqm.jiangdou.ui.publish.model.JobDetailsModel
import kotlinx.android.synthetic.main.activity_job_preview.*

/**
 * 预览简历
 * Created By bhx On 2019/9/4 0004 14:31
 */
class PublishJobPreviewActivity : BaseActivity() {
    private var mJobDetailsModel: JobDetailsModel? = null
    override fun getLayoutId(): Int = R.layout.activity_publish_job_preview

    override fun initView() {
        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar)
        val jsonString = intent.getStringExtra("JobDetailsModel")
        mJobDetailsModel = CommonConfig.fromJson(jsonString!!, JobDetailsModel::class.java)
        mJobDetailsModel?.let {

        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}