package com.jxqm.jiangdou.ui.job.adapter

import android.content.Context
import android.widget.CheckBox
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R

/**
 * 兼职检索得区域适配器
 * Created by Administrator on 2019/10/10.
 */
class JobScreenAreaAdapter(context: Context) : MultiItemTypeAdapter<String>(context) {
    var mSelectPosition = 0
    init {
        addItemViewType(object : ItemViewType<String> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_job_screen_area

            override fun isItemClickable(): Boolean = false

            override fun isViewForType(item: String?, position: Int): Boolean = true

            override fun convert(holder: ViewHolder?, t: String, position: Int) {
                holder?.let {
                    val cbSelectArea = it.getView<CheckBox>(R.id.cbSelectArea)
                    cbSelectArea.text = t
                    cbSelectArea.isChecked = mSelectPosition == position
                }
            }

        })
    }
}