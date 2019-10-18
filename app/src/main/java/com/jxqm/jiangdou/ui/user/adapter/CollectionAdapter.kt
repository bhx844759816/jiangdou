package com.jxqm.jiangdou.ui.user.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.bhx.common.adapter.rv.CommonAdapter
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.CollectionItem
import com.jxqm.jiangdou.model.JobDetailsModel
import com.jxqm.jiangdou.ui.job.view.JobDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * Created By bhx On 2019/9/4 0004 15:53
 */
class CollectionAdapter(context: Context) : CommonAdapter<JobDetailsModel>(context) {
    var mSelectCallBack: ((Int, Boolean) -> Unit)? = null
    var isSelect = false
    override fun itemLayoutId(): Int = R.layout.adapter_collection

    override fun convert(holder: ViewHolder?, model: JobDetailsModel, position: Int) {
        holder?.let {
            val rlParent = it.getView<RelativeLayout>(R.id.rlParent)
            val ivJobListImg = it.getView<ImageView>(R.id.ivJobListImg)
            val ivExpire = it.getView<ImageView>(R.id.ivExpire)
            val tvJobTitle = it.getView<TextView>(R.id.tvJobTitle)
            val tvJobCity = it.getView<TextView>(R.id.tvJobCity)
            val tvJobArea = it.getView<TextView>(R.id.tvJobArea)
            val tvJobNumbers = it.getView<TextView>(R.id.tvJobNumbers)
            val tvJobSalary = it.getView<TextView>(R.id.tvJobSalary)
            val cbSelect = it.getView<CheckBox>(R.id.cbSelect)
            Glide.with(mContext).load(Api.HTTP_BASE_URL + "/" + model.typeImgUrl).into(ivJobListImg)
            tvJobTitle.text = model.title
            tvJobCity.text = model.city
            tvJobArea.text = model.area
            tvJobNumbers.text = "招${model.recruitNum}人"
            tvJobSalary.text = "${model.salary}币/时"
            cbSelect.isChecked = model.isChecked
            cbSelect.visibility = if (isSelect) View.VISIBLE else View.GONE
            cbSelect.setOnCheckedChangeListener { _, isChecked ->
                mSelectCallBack?.invoke(position, isChecked)
            }
            if (model.expire) {
                tvJobSalary.visibility = View.GONE
                ivExpire.visibility = View.VISIBLE
            } else {
                ivExpire.visibility = View.GONE
                tvJobSalary.visibility = View.VISIBLE
            }
            rlParent.clickWithTrigger {
                if (!model.expire && !isSelect) {
                    val intent = Intent(mContext, JobDetailsActivity::class.java)
                    intent.putExtra("JobId", model.id.toString())
                    intent.putExtra("Status", JobDetailsActivity.STATUS_SINGUP)
                    mContext.startActivity(intent)
                }
            }
        }
    }
}