package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.widget.CheckBox
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
 * 雇佣记录 - 报名
 * Created By bhx On 2019/9/3 0003 10:03
 */
class EmployRecordSignUpAdapter(context: Context) : MultiItemTypeAdapter<EmployeeResumeModel>(context) {
    var acceptCallBack: ((EmployeeResumeModel) -> Unit)? = null
    var contactCallBack: ((EmployeeResumeModel) -> Unit)? = null
    var checkCallBack: ((Int, Boolean) -> Unit)? = null

    init {
        addItemViewType(object : ItemViewType<EmployeeResumeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_sign_up_item

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: EmployeeResumeModel?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, model: EmployeeResumeModel, position: Int) {
                holder?.let {
                    val ivHeadPhoto = it.getView<ImageView>(R.id.ivHeadPhoto)
                    val ivUserSex = it.getView<ImageView>(R.id.ivUserSex)
                    val tvUserName = it.getView<TextView>(R.id.tvUserName)
                    val tvArea = it.getView<TextView>(R.id.tvArea)
                    val tvAge = it.getView<TextView>(R.id.tvAge)
                    val tvAccept = it.getView<TextView>(R.id.tvSingleSettle)
                    val tvSignUpRecord = it.getView<TextView>(R.id.tvSignUpRecord)
                    val tvDetails = it.getView<TextView>(R.id.tvDetails)
                    val tvSingUpTime = it.getView<TextView>(R.id.tvSingUpTime)
                    val cbSelect = it.getView<CheckBox>(R.id.cbSelect)
                    cbSelect.isChecked = model.isChecked
                    if (RegularUtils.isTelPhoneNumber(model.name)) {
                        tvUserName.text = RegularUtils.mobileEncrypt(model.name)
                    } else {
                        tvUserName.text = model.name
                    }
                    tvArea.text = model.area
                    tvAge.text = model.age
                    tvSingUpTime.text = model.signTime
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
                    //录用
                    tvAccept.clickWithTrigger {
                        acceptCallBack?.invoke(model)
                    }
                    //联系
                    tvSignUpRecord.clickWithTrigger {
                        contactCallBack?.invoke(model)
                    }
                    cbSelect.setOnCheckedChangeListener { _, checked ->
                        checkCallBack?.invoke(position,checked)
                    }

                }
            }

        })
    }
}