package com.jxqm.jiangdou.ui.map.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.utils.DistanceUtil
import com.bhx.common.adapter.rv.CommonAdapter
import com.bhx.common.adapter.rv.holder.ViewHolder
import com.jxqm.jiangdou.R
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by Administrator on 2019/9/13.
 */
class MapSearchAdapter(context: Context) : CommonAdapter<PoiInfo>(context) {
    private var mLatLng: LatLng? = null
    override fun itemLayoutId(): Int = R.layout.adapter_map_search_item

    override fun convert(holder: ViewHolder?, poiInfo: PoiInfo?, position: Int) {
        holder?.let {
            val tvAreaName = it.getView<TextView>(R.id.tvAreaName)
            val tvAreaDescription = it.getView<TextView>(R.id.tvAreaDescription)
            val tvAreaDistance = it.getView<TextView>(R.id.tvAreaDistance)
            tvAreaName.text = poiInfo?.name
            tvAreaDescription.text = poiInfo?.address
            if (mLatLng != null && poiInfo?.location != null) {
                val distance =  String.format("%.2f", DistanceUtil.getDistance(poiInfo.location,mLatLng!!)/1000)
                tvAreaDistance.text = "$distance km"
            }
        }
    }

    fun updateDatas(dataList: MutableList<PoiInfo>?, latLng: LatLng?) {
        mLatLng = latLng
        super.updateDatas(dataList)
    }

}