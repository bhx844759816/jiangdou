package com.jxqm.jiangdou.adapter

import android.content.Context
import android.widget.RadioButton
import android.widget.RelativeLayout
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.JobTypeModel
import com.jxqm.jiangdou.model.JobTypeModelWrap
import com.jxqm.jiangdou.utils.clickWithTrigger

/**
 * 兼职类型
 */
class JobTypeAdapter(context: Context) : MultiItemTypeAdapter<JobTypeModelWrap>(context) {
    var mJobTypeSelectCallBack: ((Int) -> Unit)? = null
    var mExpandCallBack: (() -> Unit)? = null

    init {
        //展开的Item
        addItemViewType(object : ItemViewType<JobTypeModelWrap> {
            override fun getItemViewLayoutId(): Int = R.layout.view_publish_job_expand_type_item

            override fun isItemClickable(): Boolean = false


            override fun isViewForType(item: JobTypeModelWrap?, position: Int): Boolean =
                item?.type == 1

            override fun convert(holder: ViewHolder?, t: JobTypeModelWrap?, position: Int) {
                holder?.let {
                    it.getView<RelativeLayout>(R.id.rlExpand).clickWithTrigger {
                        mExpandCallBack?.invoke()
                    }
                }
            }

        })
        //选择的Item
        addItemViewType(object : ItemViewType<JobTypeModelWrap> {
            override fun getItemViewLayoutId(): Int = R.layout.view_publish_job_type_item

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: JobTypeModelWrap?, position: Int): Boolean =
                item?.type == 2

            override fun convert(holder: ViewHolder?, model: JobTypeModelWrap, position: Int) {
                holder?.let {
                    val rbJobType = it.getView<RadioButton>(R.id.rbJobType)
                    rbJobType.text = model.jobTypeModel?.jobTypeName
                    rbJobType.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
//                            mJobTypeSelectCallBack?.invoke(model.jobTypeModel!!.id)
                        }
                    }
                }
            }

        })

    }
}