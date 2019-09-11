package com.jxqm.jiangdou.ui.map.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.baidu.mapapi.search.core.PoiInfo
import com.bhx.common.adapter.rv.CommonAdapter
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R

/**
 * Created by Administrator on 2019/9/11.
 */
class MapAdapter(context: Context) : CommonAdapter<PoiInfo>(context) {
    override fun itemLayoutId(): Int = R.layout.adapter_map_item

    override fun convert(holder: ViewHolder?, poiInfo: PoiInfo, position: Int) {
        holder?.let {
            val tvAreaName = holder.getView<TextView>(R.id.tvAreaName)
            val ivLocationIcon = holder.getView<ImageView>(R.id.ivLocationIcon)
            val tvAreaDescription = holder.getView<TextView>(R.id.tvAreaDescription)
            tvAreaName.text = poiInfo.name
            tvAreaDescription.text = poiInfo.address
            if(position == 0){
                tvAreaName.setTextColor(mContext.resources.getColor(R.color.colorAccent))
                ivLocationIcon.visibility = View.VISIBLE
            }else{
                ivLocationIcon.visibility = View.GONE
                tvAreaName.setTextColor(mContext.resources.getColor(R.color.text_title))
            }
        }
    }
}