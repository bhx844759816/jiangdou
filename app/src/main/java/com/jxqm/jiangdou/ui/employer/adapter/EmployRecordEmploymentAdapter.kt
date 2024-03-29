package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bhx.common.utils.RegularUtils
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.EmployeeResumeModel
import com.jxqm.jiangdou.ui.user.view.ResumeDetailsActivity
import com.jxqm.jiangdou.utils.GlideCircleTransform
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * 雇佣记录 - 已录用-适配器
 * Created By bhx On 2019/9/3 0003 13:50
 */
class EmployRecordEmploymentAdapter(context: Context) :
    MultiItemTypeAdapter<EmployeeResumeModel>(context) {

    var status: Int = 0 //
    var mWithdrawOfferCallBack: ((Long) -> Unit)? = null //撤回
    var mRepeatOfferCallBack: ((Long) -> Unit)? = null //重发
    var contactCallBack: ((EmployeeResumeModel) -> Unit)? = null

    init {
        addItemViewType(object : ItemViewType<EmployeeResumeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_employment
            override fun isItemClickable(): Boolean = false
            override fun isViewForType(item: EmployeeResumeModel, position: Int): Boolean = true
            override fun convert(holder: ViewHolder?, model: EmployeeResumeModel, position: Int) {
                holder?.let {
                    val ivHeadPhoto = it.getView<ImageView>(R.id.ivHeadPhoto)
                    val ivUserSex = it.getView<ImageView>(R.id.ivUserSex)
                    val tvUserName = it.getView<TextView>(R.id.tvUserName)
                    val tvArea = it.getView<TextView>(R.id.tvArea)
                    val tvAge = it.getView<TextView>(R.id.tvAge)
                    val tvContact = it.getView<TextView>(R.id.tvContact)
                    val tvReplySend = it.getView<TextView>(R.id.tvReplySend)
                    val tvDetails = it.getView<TextView>(R.id.tvDetails)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    if (RegularUtils.isTelPhoneNumber(model.name)) {
                        tvUserName.text = RegularUtils.mobileEncrypt(model.name)
                    } else {
                        tvUserName.text = model.name
                    }
                    tvArea.text = model.area
                    tvAge.text = model.age
                    tvSingUpTime.text = model.signTime
                    when (status) {
                        0 -> { //已邀请
                            tvReplySend.text = "撤回"
                            tvReplySend.visibility = View.VISIBLE
                        }
                        1 -> {//已接受
                            tvReplySend.visibility = View.GONE
                        }
                        2 -> {//已拒绝
                            tvReplySend.visibility = View.GONE
                        }
                        3 -> {//未恢复
                            tvReplySend.text = "重发"
                            tvReplySend.visibility = View.VISIBLE
                        }
                    }
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + model.avatar)
                        .transform(GlideCircleTransform(mContext))
                        .error(R.drawable.icon_default_head_photo)
                        .placeholder(R.drawable.icon_default_head_photo)
                        .into(ivHeadPhoto)
                    if (model.genderCode == 0) {
                        ivUserSex.setBackgroundResource(R.drawable.icon_girl)
                    } else {
                        ivUserSex.setBackgroundResource(R.drawable.icon_boy)
                    }
                    ivHeadPhoto.clickWithTrigger {
                        mContext.startActivity<ResumeDetailsActivity>("UserId" to model.userId)
                    }
                    tvDetails.clickWithTrigger {
                        mContext.startActivity<ResumeDetailsActivity>("UserId" to model.userId)
                    }
                    //联系
                    tvContact.clickWithTrigger {
                        contactCallBack?.invoke(model)
                    }
                    //撤回或重发
                    tvReplySend.clickWithTrigger {
                        when (status) {
                            0 -> {//已邀请 撤回
                                mWithdrawOfferCallBack?.invoke(model.id)
                            }
                            3 -> {//未恢复 重发
                                mRepeatOfferCallBack?.invoke(model.id)
                            }
                        }

                    }

                }
            }

        })
    }
}