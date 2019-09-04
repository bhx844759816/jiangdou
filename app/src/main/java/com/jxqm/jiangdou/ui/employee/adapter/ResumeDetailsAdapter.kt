package com.jxqm.jiangdou.ui.employee.adapter

import android.content.Context
import com.bhx.common.adapter.rv.CommonAdapter
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R

/**
 * 简历详情界面的 照片列表适配器
 * Created By bhx On 2019/9/4 0004 11:34
 */
class ResumeDetailsAdapter(context: Context) : CommonAdapter<String>(context) {
    override fun itemLayoutId(): Int = R.layout.adapter_resume_details

    override fun convert(holder: ViewHolder?, t: String?, position: Int) {
    }
}