package com.jxqm.jiangdou.ui.employer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.widget.CheckBox
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
 * 待结算
 * Created By bhx On 2019/9/3 0003 18:11
 */
class EmployRecordWaitPayAdapter(context: Context) : MultiItemTypeAdapter<EmployeeResumeModel>(context) {
    var mSingleSettle: ((String, String) -> Unit)? = null //单结
    var mContactCallBack: ((String) -> Unit)? = null //联系
    var checkCallBack: ((Int, Boolean) -> Unit)? = null

    init {
        addItemViewType(object : ItemViewType<EmployeeResumeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_wait_pay
            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: EmployeeResumeModel?, position: Int): Boolean = true

            @SuppressLint("SetTextI18n")
            override fun convert(holder: ViewHolder?, model: EmployeeResumeModel, position: Int) {
                holder?.let {
                    val ivHeadPhoto = it.getView<ImageView>(R.id.ivHeadPhoto)
                    val ivUserSex = it.getView<ImageView>(R.id.ivUserSex)
                    val tvUserName = it.getView<TextView>(R.id.tvUserName)
                    val tvSingleSettle = it.getView<TextView>(R.id.tvSingleSettle)
                    val tvContact = it.getView<TextView>(R.id.tvContact)
                    val tvAmount = it.getView<TextView>(R.id.tvAmount)
                    val tvWorkTime = it.getView<TextView>(R.id.tvWorkTime)
                    val cbSelect = it.getView<CheckBox>(R.id.cbSelect)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + model.avatar)
                        .transform(GlideCircleTransform(mContext))
                        .error(R.drawable.icon_default_head_photo)
                        .placeholder(R.drawable.icon_default_head_photo)
                        .into(ivHeadPhoto)
                    tvUserName.text = model.name
                    tvAmount.text = "${model.amount}币"
                    val date = model.date.substring(model.date.indexOf("-") + 1).replace("-", "月")
                    tvWorkTime.text = "工作时段： ${model.startTime}"
                    cbSelect.isChecked = model.isChecked
                    if (model.gender == "男") {
                        ivUserSex.setBackgroundResource(R.drawable.icon_boy)
                    } else {
                        ivUserSex.setBackgroundResource(R.drawable.icon_girl)
                    }
                    //查看简历
                    ivHeadPhoto.clickWithTrigger {
                        mContext.startActivity<ResumeDetailsActivity>("UserId" to model.userId)
                    }
                    //单结
                    tvSingleSettle.clickWithTrigger {
                        mSingleSettle?.invoke(model.amount, model.id.toString())
                    }
                    //联系雇员
                    tvContact.clickWithTrigger {
                        mContactCallBack?.invoke(model.tel)
                    }
                    //选中
                    cbSelect.setOnCheckedChangeListener { _, checked ->
                        checkCallBack?.invoke(position, checked)
                    }
                }


            }
        })

    }
}