package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.EmployeeResumeModel
import com.jxqm.jiangdou.ui.user.view.ResumeDetailsActivity
import com.jxqm.jiangdou.utils.GlideCircleTransform
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * 雇佣记录 - 到岗 -
 * Created By bhx On 2019/9/3 0003 16:24
 */
class EmployRecordReportDutyAdapter(context: Context) : MultiItemTypeAdapter<EmployeeResumeModel>(context) {
    var contactCallBack: ((EmployeeResumeModel) -> Unit)? = null
    init {
        addItemViewType(object : ItemViewType<EmployeeResumeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_report_duty

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: EmployeeResumeModel?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, model: EmployeeResumeModel, position: Int) {

                holder?.let {
                    val ivHeadPhoto = it.getView<ImageView>(R.id.ivHeadPhoto)
                    val ivUserSex = it.getView<ImageView>(R.id.ivUserSex)
                    val tvUserName = it.getView<TextView>(R.id.tvUserName)
                    val tvSignInTime = it.getView<TextView>(R.id.tvSignInTime)//
                    val tvJobTitle = it.getView<TextView>(R.id.tvJobTitle) //职位标题
                    val tvContact = it.getView<TextView>(R.id.tvContact)
                    val tvDetails = it.getView<TextView>(R.id.tvDetails)
                    tvUserName.text = model.name
                    tvSignInTime.text = "签到时间： ${model.arrivalTime}"
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + model.avatar)
                        .error(R.drawable.icon_default_head_photo)
                        .placeholder(R.drawable.icon_default_head_photo)
                        .transform(GlideCircleTransform(mContext))
                        .into(ivHeadPhoto)
                    tvJobTitle.text = "岗位:${model.title}"
                    if (model.gender == "男") {
                        ivUserSex.setBackgroundResource(R.drawable.icon_boy)
                    } else {
                        ivUserSex.setBackgroundResource(R.drawable.icon_girl)
                    }
                    ivHeadPhoto.clickWithTrigger {
                        mContext.startActivity<ResumeDetailsActivity>("UserId" to model.userId)
                    }
                    tvDetails.clickWithTrigger {
                        mContext.startActivity<ResumeDetailsActivity>("UserId" to model.userId)
                    }
                    //录用
                    tvContact.clickWithTrigger {
                        contactCallBack?.invoke(model)
                    }

                }
            }

        })

    }
}