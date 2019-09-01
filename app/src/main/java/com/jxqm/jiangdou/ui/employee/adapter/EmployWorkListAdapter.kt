package com.jxqm.jiangdou.ui.employee.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployWorkItem

/**
 * Created by Administrator on 2019/9/1.
 */
class EmployWorkListAdapter(context: Context) : MultiItemTypeAdapter<EmployWorkItem>(context) {
    init {
        addItemViewType(object : ItemViewType<EmployWorkItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employee_work_top_item
            override fun isItemClickable(): Boolean = false
            override fun isViewForType(item: EmployWorkItem?, position: Int): Boolean {
                return item?.type == -1
            }

            override fun convert(holder: ViewHolder?, t: EmployWorkItem?, position: Int) {
                val title = holder?.getView<TextView>(R.id.tvTitle)
                title?.text = "失效"
            }

        })


        addItemViewType(object : ItemViewType<EmployWorkItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employ_work_list

            override fun isItemClickable(): Boolean = true

            override fun isViewForType(item: EmployWorkItem?, position: Int): Boolean = item?.type != -1

            override fun convert(holder: ViewHolder?, item: EmployWorkItem, position: Int) {
                val type = item.type
                val tvJobMoney = holder?.getView<TextView>(R.id.tvJobMoney)
                val ivTips = holder?.getView<ImageView>(R.id.ivWorkTip)
                val tvAccept = holder?.getView<TextView>(R.id.tvAccept)
                val tvRefuse = holder?.getView<TextView>(R.id.tvRefuse)
                if (item.isTimeOut) {
                    ivTips?.setBackgroundResource(R.drawable.icon_timeout)
                } else {
                    if (!item.isAccept) {
                        ivTips?.setBackgroundResource(R.drawable.icon_refuse)
                    }
                }
                if (item.isNormal) {
                    //正常状态
                    tvAccept?.visibility = View.VISIBLE
                    tvRefuse?.visibility = View.VISIBLE
                    tvJobMoney?.visibility = View.VISIBLE
                    ivTips?.visibility = View.GONE
                } else {
                    if (item.isAccept && !item.isTimeOut) {
                        //接受
                        tvAccept?.visibility = View.GONE
                        tvJobMoney?.visibility = View.VISIBLE
                        tvRefuse?.visibility = View.GONE
                        ivTips?.visibility = View.GONE
                    } else {
                        //拒绝
                        ivTips?.visibility = View.VISIBLE
                        tvJobMoney?.visibility = View.GONE
                        tvAccept?.visibility = View.GONE
                        tvRefuse?.visibility = View.GONE
                    }
                }

            }
        })


    }
}