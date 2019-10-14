package com.jxqm.jiangdou.ui.employer.adapter

import android.content.Context
import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.EmployRecordPayItem
import com.jxqm.jiangdou.model.EmployeeResumeModel
import com.jxqm.jiangdou.ui.user.view.ResumeDetailsActivity
import com.jxqm.jiangdou.utils.GlideCircleTransform
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * Created By bhx On 2019/9/3 0003 17:18
 */
class EmployRecordPayAdapter(context: Context) : MultiItemTypeAdapter<EmployeeResumeModel>(context) {
    var repeatSettleCallBack:((String, String) -> Unit)? = null //重新结算

    init {
        //已结算
        addItemViewType(object : ItemViewType<EmployeeResumeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_pay_finish_layout

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: EmployeeResumeModel?, position: Int): Boolean = item?.settleCode == 1

            override fun convert(holder: ViewHolder?, item: EmployeeResumeModel, position: Int) {
                holder?.let {
                    val ivHeadPhoto = it.getView<ImageView>(R.id.ivHeadPhoto)
                    val ivUserSex = it.getView<ImageView>(R.id.ivUserSex)
                    val tvUserName = it.getView<TextView>(R.id.tvUserName)
                    val tvWorkTime = it.getView<TextView>(R.id.tvWorkTime)
                    val tvAmount = it.getView<TextView>(R.id.tvAmount)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + item.avatar)
                        .transform(GlideCircleTransform(mContext))
                        .into(ivHeadPhoto)
                    if (item.gender == "男") {
                        ivUserSex.setBackgroundResource(R.drawable.icon_boy)
                    } else {
                        ivUserSex.setBackgroundResource(R.drawable.icon_girl)
                    }
                    tvUserName.text = item.name
                    tvWorkTime.text = "工作时段：  ${item.startTime}  "
                    tvAmount.text = "${item.amount}币"
                }
            }

        })
        //待结算
        addItemViewType(object : ItemViewType<EmployeeResumeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_pay_wait_layout

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: EmployeeResumeModel?, position: Int): Boolean = item?.settleCode == 0 ||
                    item?.settleCode == 2

            override fun convert(holder: ViewHolder?, item: EmployeeResumeModel, position: Int) {
                holder?.let {
                    val ivHeadPhoto = it.getView<ImageView>(R.id.ivHeadPhoto)
                    val ivUserSex = it.getView<ImageView>(R.id.ivUserSex)
                    val tvUserName = it.getView<TextView>(R.id.tvUserName)
                    val tvWorkTime = it.getView<TextView>(R.id.tvWorkTime)
                    val tvAmount = it.getView<TextView>(R.id.tvAmount)
                    val tvAmountSettle = it.getView<TextView>(R.id.tvAmountSettle)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + item.avatar)
                        .transform(GlideCircleTransform(mContext))
                        .into(ivHeadPhoto)
                    if (item.gender == "男") {
                        ivUserSex.setBackgroundResource(R.drawable.icon_boy)
                    } else {
                        ivUserSex.setBackgroundResource(R.drawable.icon_girl)
                    }
                    tvUserName.text = item.name
                    tvWorkTime.text = "工作时段： ${item.startTime}  "
                    tvAmount.text = "${item.amount}币"
                    tvAmountSettle.text = "${item.settleAmount}币"
                    tvAmount.paintFlags = tvAmount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            }

        })
        //已拒绝
        addItemViewType(object : ItemViewType<EmployeeResumeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_record_pay_refuse_layout

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: EmployeeResumeModel?, position: Int): Boolean = item?.settleCode == 3

            override fun convert(holder: ViewHolder?, item: EmployeeResumeModel, position: Int) {
                holder?.let {
                    val ivHeadPhoto = it.getView<ImageView>(R.id.ivHeadPhoto)
                    val ivUserSex = it.getView<ImageView>(R.id.ivUserSex)
                    val tvUserName = it.getView<TextView>(R.id.tvUserName)
                    val tvWorkTime = it.getView<TextView>(R.id.tvWorkTime)
                    val tvAmount = it.getView<TextView>(R.id.tvAmount)
                    val tvAgainSettle = it.getView<TextView>(R.id.tvAgainSettle)
                    val tvAmountSettle = it.getView<TextView>(R.id.tvAmountSettle)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + item.avatar)
                        .transform(GlideCircleTransform(mContext))
                        .into(ivHeadPhoto)
                    if (item.gender == "男") {
                        ivUserSex.setBackgroundResource(R.drawable.icon_boy)
                    } else {
                        ivUserSex.setBackgroundResource(R.drawable.icon_girl)
                    }
                    tvUserName.text = item.name
                    tvWorkTime.text = "工作时段： ${item.startTime}  "
                    tvAmount.text = "${item.amount}币"
                    tvAmountSettle.text = "${item.settleAmount}币"
                    tvAmount.paintFlags = tvAmount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvAgainSettle.clickWithTrigger {
                        repeatSettleCallBack?.invoke(item.amount, item.id.toString())
                    }

                }
            }

        })
    }
}