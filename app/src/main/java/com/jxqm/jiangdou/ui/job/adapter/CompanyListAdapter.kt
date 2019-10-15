package com.jxqm.jiangdou.ui.job.adapter

import android.content.Context
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bhx.common.adapter.rv.LoadMoreAdapter
import com.bhx.common.adapter.rv.base.ItemViewType
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.model.AttestationStatusModel
import com.jxqm.jiangdou.ui.job.view.CompanyDetailsActivity
import com.jxqm.jiangdou.utils.clickWithTrigger
import com.jxqm.jiangdou.utils.startActivity

/**
 * 搜素公司列表
 * Created by Administrator on 2019/8/18.
 */
class CompanyListAdapter(context: Context) : LoadMoreAdapter<AttestationStatusModel>(context) {

    init {
        addItemViewType(object : ItemViewType<AttestationStatusModel> {
            override fun getItemViewLayoutId(): Int = R.layout.adapter_company_list
            override fun isItemClickable(): Boolean = true
            override fun isViewForType(item: AttestationStatusModel?, position: Int): Boolean = true
            override fun convert(
                holder: ViewHolder?,
                model: AttestationStatusModel,
                position: Int
            ) {
                holder?.let {
                    val llParent = it.getView<ConstraintLayout>(R.id.llParent)
                    val tvCompanyName = it.getView<TextView>(R.id.tvCompanyName)
                    val tvCompanyPeopleNum = it.getView<TextView>(R.id.tvCompanyPeopleNum)
                    val tvCompanyType = it.getView<TextView>(R.id.tvCompanyType)
                    val tvJobCity = it.getView<TextView>(R.id.tvJobCity)
                    val tvJobArea = it.getView<TextView>(R.id.tvJobArea)
                    val tvJobNumbers = it.getView<TextView>(R.id.tvJobNumbers) //热招职位
                    tvCompanyName.text = model.employerName
                    tvCompanyPeopleNum.text = model.rygmName
                    tvCompanyType.text = model.hyflName
                    tvJobCity.text = model.city
                    tvJobArea.text = model.area
                    tvJobNumbers.text = model.jobNum
                    llParent.clickWithTrigger {
                        mContext.startActivity<CompanyDetailsActivity>("AttestationStatusModel" to model.toJson())
                    }
                }
            }
        })
    }
}