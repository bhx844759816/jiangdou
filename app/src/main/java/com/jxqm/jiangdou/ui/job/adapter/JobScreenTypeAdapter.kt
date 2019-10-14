package com.jxqm.jiangdou.ui.job.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.view.setPadding
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.LogUtils
import com.bhx.common.view.FlowLayout
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.model.JobTypeModel
import com.jxqm.jiangdou.view.GridRadioGroup
import kotlinx.android.synthetic.main.fragment_select_job_type.*

/**
 * Created by Administrator on 2019/10/3.
 */
class JobScreenTypeAdapter(context: Context) : MultiItemTypeAdapter<JobTypeModel>(context) {
    var mTypeSelectCallBack: ((Int) -> Unit)? = null //
    private var mJobTypeMaps = mutableMapOf<String, CheckBox>()
    var mJopTypeArrayList = mutableListOf<Int>()

    init {
        addItemViewType(object : ItemViewType<JobTypeModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_job_screen_type_layout

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: JobTypeModel?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, item: JobTypeModel?, position: Int) {
                holder?.let {
                    val ivJobTypeTitle = it.getView<ImageView>(R.id.ivJobTypeTitle)
                    val tvJobTypeTitle = it.getView<TextView>(R.id.tvJobTypeTitle)
                    val rgJobType = it.getView<FlowLayout>(R.id.rgJobType)
                    Glide.with(mContext).load(Api.HTTP_BASE_URL + item?.imgUrl).into(ivJobTypeTitle)
                    tvJobTypeTitle.text = item?.jobTypeName
                    rgJobType.removeAllViews()
                    addJobType(rgJobType, item!!.jobTypes)

                }
            }

        })
    }

    /**
     * 清除选中
     */
    fun clearCheck() {
        mJobTypeMaps.forEach {
            it.value.isChecked = false
        }
    }

    /**
     * 添加兼职类型
     */
    private fun addJobType(rgParent: FlowLayout, jobTypes: List<JobTypeModel>) {
        jobTypes.forEach {
            val checkBox = LayoutInflater.from(mContext)
                .inflate(R.layout.view_screen_job_type_item, null) as CheckBox
            checkBox.text = it.jobTypeName
            checkBox.id = it.id
            checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (isChecked) {
                    mJopTypeArrayList.add(compoundButton.id)
                } else {
                    mJopTypeArrayList.remove(compoundButton.id)
                }
            }
            checkBox.isChecked = it.isChecked
            val layoutParams = ViewGroup.MarginLayoutParams(
                DensityUtil.dip2px(mContext, 100f),
                DensityUtil.dip2px(mContext, 40f)
            )
            layoutParams.rightMargin = DensityUtil.dip2px(mContext, 10f)
            layoutParams.bottomMargin = DensityUtil.dip2px(mContext, 10f)
            checkBox.layoutParams = layoutParams
            rgParent.addView(checkBox)
            mJobTypeMaps[it.id.toString()] = checkBox
        }
    }
}