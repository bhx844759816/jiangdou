package com.jxqm.jiangdou.ui.employee.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.bhx.common.adapter.rv.LoadMoreAdapter
import com.bhx.common.adapter.rv.MultiItemTypeAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.EmployeeWorkBaseItem

/**
 * 雇员-工作列表-已报名—适配器
 * Created by Administrator on 2019/8/31.
 */
class SignUpWorkListAdapter(context: Context) : MultiItemTypeAdapter<EmployeeWorkBaseItem>(context) {
    private var mType = 0

    init {
        addItemViewType(object : ItemViewType<EmployeeWorkBaseItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employee_work_top_item
            override fun isItemClickable(): Boolean = false
            override fun isViewForType(item: EmployeeWorkBaseItem?, position: Int): Boolean {
                return item?.type == -1
            }

            override fun convert(holder: ViewHolder?, t: EmployeeWorkBaseItem?, position: Int) {
            }

        })

        addItemViewType(object : ItemViewType<EmployeeWorkBaseItem> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_employee_work_list

            override fun isItemClickable(): Boolean = true

            override fun isViewForType(item: EmployeeWorkBaseItem?, position: Int): Boolean {
                return item?.type != -1
            }

            override fun convert(holder: ViewHolder?, item: EmployeeWorkBaseItem?, position: Int) {
                val type = item?.type
                val tvJobMoney = holder?.getView<TextView>(R.id.tvJobMoney)
                val tvSignUpSuccess = holder?.getView<TextView>(R.id.tvSignUpSuccess)
                val tvSignUpPeopleCounts = holder?.getView<TextView>(R.id.tvSignUpPeopleCounts)
                type?.let {
                    if (it == 0) {
                        tvJobMoney?.visibility = View.VISIBLE
                        tvSignUpSuccess?.visibility = View.VISIBLE
                        tvSignUpPeopleCounts?.visibility = View.VISIBLE
                    } else if (it == 1) {
                        tvJobMoney?.visibility = View.INVISIBLE
                        tvSignUpSuccess?.visibility = View.INVISIBLE
                        tvSignUpPeopleCounts?.visibility = View.INVISIBLE
                    }
                }
            }
        })
    }

    fun setListType(type: Int) {
        mType = type
    }
}