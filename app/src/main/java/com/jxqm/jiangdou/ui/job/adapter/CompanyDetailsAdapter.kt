package com.jxqm.jiangdou.ui.job.adapter

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.AttestationStatusModel
import com.jxqm.jiangdou.model.CompanyDetailsModel
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.job.view.JobDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.ms.square.android.expandabletextview.ExpandableTextView
import java.lang.StringBuilder

/**
 * 企业详情适配器
 * Created by Administrator on 2019/8/18.
 */
class CompanyDetailsAdapter constructor(context: Context) : MultiItemTypeAdapter<CompanyDetailsModel>(context) {

    init {
        addCompanyDetailsViewType()
        addCompanyPublishJobsViewType()
    }

    private fun addCompanyDetailsViewType() {
        addItemViewType(object : ItemViewType<CompanyDetailsModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_company_details_item

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: CompanyDetailsModel, position: Int): Boolean {
                return item.type == 0
            }

            override fun convert(holder: ViewHolder, t: CompanyDetailsModel, position: Int) {
                val expandTextView = holder.getView<ExpandableTextView>(R.id.expand_text_view)
                val mAttestationStatusModel = t.data as AttestationStatusModel
                val tvCompanyName = holder.getView<TextView>(R.id.tvCompanyName)
                val tvCompanyArea = holder.getView<TextView>(R.id.tvCompanyArea)
                val tvCompanyPeopleNum = holder.getView<TextView>(R.id.tvCompanyPeopleNum)
                val tvCompanyType = holder.getView<TextView>(R.id.tvCompanyType)
                val tvCompanyAddressDetails = holder.getView<TextView>(R.id.tvCompanyAddressDetails)
                val ivMapView = holder.getView<ImageView>(R.id.ivMapView)
                tvCompanyName.text = mAttestationStatusModel.employerName
                tvCompanyArea.text = mAttestationStatusModel.address
                expandTextView.text =mAttestationStatusModel.introduction
                Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + mAttestationStatusModel.mapImg).into(ivMapView)
                val stringBuilder = StringBuilder()
                stringBuilder.append("详细地址:")
                stringBuilder.append(mAttestationStatusModel.province)
                stringBuilder.append(" ")
                stringBuilder.append(mAttestationStatusModel.city)
                stringBuilder.append(" ")
                stringBuilder.append(mAttestationStatusModel.area)
                stringBuilder.append(" ")
                stringBuilder.append(mAttestationStatusModel.address)
                stringBuilder.append(mAttestationStatusModel.addressDetail)
                tvCompanyAddressDetails.text = stringBuilder.toString()
                tvCompanyPeopleNum.text = mAttestationStatusModel.rygmName
                tvCompanyType.text = mAttestationStatusModel.hyflName
            }
        })
    }

    private fun addCompanyPublishJobsViewType() {
        addItemViewType(object : ItemViewType<CompanyDetailsModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_job_item

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: CompanyDetailsModel, position: Int): Boolean {
                return item.type == 1
            }

            override fun convert(holder: ViewHolder?, model: CompanyDetailsModel, position: Int) {
                holder?.let {
                    val llParent = holder.getView<LinearLayout>(R.id.llParent)
                    val ivJobListImg = holder.getView<ImageView>(R.id.ivJobListImg)
                    val tvJobTitle = holder.getView<TextView>(R.id.tvJobTitle)
                    val tvJobCity = holder.getView<TextView>(R.id.tvJobCity)
                    val tvJobArea = holder.getView<TextView>(R.id.tvJobArea)
                    val tvJobNumbers = holder.getView<TextView>(R.id.tvJobNumbers)
                    val tvJobSalary = holder.getView<TextView>(R.id.tvJobSalary)
                    val jobDetailsModel=model.data as  JobDetailsModel
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + jobDetailsModel.typeImgUrl).into(ivJobListImg)
                    tvJobTitle.text = jobDetailsModel.title
                    tvJobCity.text = jobDetailsModel.city
                    tvJobArea.text = jobDetailsModel.area
                    tvJobNumbers.text = "招${jobDetailsModel.recruitNum}人"
                    tvJobSalary.text = "${jobDetailsModel.salary}币/时"
                    llParent.clickWithTrigger {
                        val intent = Intent(mContext, JobDetailsActivity::class.java)
                        intent.putExtra("JobId", jobDetailsModel.id.toString())
                        intent.putExtra("Status", JobDetailsActivity.STATUS_SINGUP)
                        mContext.startActivity(intent)
                    }
                }
            }
        })
    }
}