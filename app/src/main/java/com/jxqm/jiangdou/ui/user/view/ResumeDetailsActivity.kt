package com.jxqm.jiangdou.ui.user.view

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.ui.employee.adapter.ResumeDetailsAdapter
import com.jxqm.jiangdou.ui.employee.vm.ResumeDetailsViewModel
import com.jxqm.jiangdou.ui.user.model.ResumeModel
import com.jxqm.jiangdou.utils.GlideCircleTransform
import com.jxqm.jiangdou.utils.StatusBarTextUtils
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_resume_details.*

/**
 * 简历详情
 * Created By bhx On 2019/9/4 0004 10:11
 */
class ResumeDetailsActivity : BaseDataActivity<ResumeDetailsViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_resume_details
    override fun getEventKey(): Any = Constants.EVENT_KEY_RESUME_DETAILS
    private var mResumeModel: ResumeModel? = null
    private val mPhotoList = mutableListOf<String>()
    private lateinit var mAdapter: ResumeDetailsAdapter
    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
        StatusBarTextUtils.setLightStatusBar(this, true)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        mAdapter = ResumeDetailsAdapter(this)
        recyclerView.adapter = mAdapter
        resumeDetailsBack.clickWithTrigger {
            finish()
        }
    }

    override fun initData() {
        val userId = intent.getStringExtra("UserId")
        userId?.let {
            mViewModel.getUserResume(it.toLong())
        }
    }

    override fun dataObserver() {
        registerObserver(Constants.TAG_GET_RESUME_DETAILS_SUCCESS, ResumeModel::class.java)
            .observe(this, Observer {
                mResumeModel = it
                initUiStatus()
            })
    }

    /**
     * 初始化界面数据
     */
    @SuppressLint("SetTextI18n")
    private fun initUiStatus() {
        mResumeModel?.let {
            Glide.with(this).load(Api.HTTP_BASE_URL + "/" + it.avatar)
                .transform(GlideCircleTransform(this))
                .into(ivHeadPhoto)
            tvUserName.text = it.name
            if (it.gender == "女") {
                ivUserGender.setBackgroundResource(R.drawable.icon_girl)
            } else {
                ivUserGender.setBackgroundResource(R.drawable.icon_boy)
            }
            tvUserBirthday.text = it.birthday
            tvUserPhone.text = it.tel
            tvUserStar.text = it.star
            tvUserEdu.text = "学历:  ${it.academic}"
            tvUserWeight.text = "体重:  ${it.weight}kg"
            tvUserHeight.text = "身高:  ${it.height}cm"
            tvUserLocation.text = "位置:  ${it.area}"
            tvUserDescription.text = it.content
            if (it.images.isNotEmpty()) {
                val images = it.images.split(",").map { image ->
                    Api.HTTP_BASE_URL + "/" + image
                }
                mPhotoList.addAll(images)
                mAdapter.setDataList(mPhotoList)
            }
        }
    }
}