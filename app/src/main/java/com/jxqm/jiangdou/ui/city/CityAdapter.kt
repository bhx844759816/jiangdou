package com.jxqm.jiangdou.ui.city

import android.content.Context
import android.os.Handler
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhx.common.utils.DensityUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.city.model.City
import com.jxqm.jiangdou.ui.city.model.HotCity
import com.jxqm.jiangdou.view.GridRadioGroup

/**
 * Created By bhx On 2019/8/16 0016 10:16
 */
class CityAdapter() : RecyclerView.Adapter<CityAdapter.BaseViewHolder>() {
    private lateinit var inflater: LayoutInflater
    private lateinit var mData: List<City>
    private lateinit var mHotCity: List<City>
    private lateinit var mContext: Context
    private lateinit var mLayoutManager: LinearLayoutManager
    private var stateChanged: Boolean = false

    constructor(context: Context, data: List<City>, hotCity: List<City>) : this() {
        inflater = LayoutInflater.from(context)
        this.mContext = context
        this.mData = data
        this.mHotCity = hotCity
    }

    fun setLayoutManager(manager: LinearLayoutManager) {
        this.mLayoutManager = manager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_HOT -> {
                val view = inflater.inflate(R.layout.adapter_city_hot_view, parent, false)
                HotCityHolder(view)
            }
            VIEW_TYPE_LOCATION -> {
                val view = inflater.inflate(R.layout.adapter_city_location_view, parent, false)
                LocationHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.adapter_city_item, parent, false)
                DefaultHolder(view)
            }
        }
    }

    override fun getItemCount(): Int = mData.size


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is LocationHolder -> {
                val pos = holder.getAdapterPosition()
            }
            is HotCityHolder -> {
                addHotCity(holder.mHotCityGroup)
            }

            is DefaultHolder -> {
                val pos = holder.getAdapterPosition()
                val data = mData[pos]
                holder.mCityItem.text = data.name
            }
        }
    }

    /**
     * 添加热门城市
     */
    private fun addHotCity(viewGroup: ViewGroup) {
        viewGroup.removeAllViews()
        mHotCity.forEach {
            val radioButton = RadioButton(mContext)
            radioButton.text = it.name
            radioButton.setButtonDrawable(0)
            radioButton.setBackgroundResource(R.drawable.shape_gray_empty_circle_rectage)
            radioButton.setPadding(
                DensityUtil.dip2px(mContext, 25f),
                DensityUtil.dip2px(mContext, 10f),
                DensityUtil.dip2px(mContext, 25f),
                DensityUtil.dip2px(mContext, 10f)
            )
            radioButton.gravity = Gravity.CENTER
            viewGroup.addView(radioButton)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && TextUtils.equals("定", mData[position].section.substring(0, 1)))
            return VIEW_TYPE_LOCATION
        return if (position == 1 && TextUtils.equals("热", mData[position].section.substring(0, 1))) VIEW_TYPE_HOT
        else super.getItemViewType(position)
    }

    open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * 定位的ViewHolder
     */
    class LocationHolder(itemView: View) : BaseViewHolder(itemView) {
        val locationView: TextView = itemView.findViewById(R.id.tvLocationView)
    }

    /**
     * 热门城市
     */
    class HotCityHolder(itemView: View) : BaseViewHolder(itemView) {
        val mHotCityGroup: GridRadioGroup = itemView.findViewById(R.id.rgHotCityParent)
    }


    /**
     * 普通的Item
     */
    class DefaultHolder(itemView: View) : BaseViewHolder(itemView) {
        val mCityItem: TextView = itemView.findViewById(R.id.tvItem)
    }

    /**
     * 滚动RecyclerView到索引位置
     * @param index
     */
    fun scrollToSection(index: String) {
        if (mData.isEmpty()) return
        if (TextUtils.isEmpty(index)) return
        val size = mData.size
        for (i in 0 until size) {
            if (TextUtils.equals(index.substring(0, 1), mData[i].section.substring(0, 1))) {
                mLayoutManager.scrollToPositionWithOffset(i, 0)
                if (TextUtils.equals(index.substring(0, 1), "定")) {
                    //防止滚动时进行刷新
                    Handler().postDelayed({ if (stateChanged) notifyItemChanged(0) }, 1000)
                }
                return
            }
        }
    }

    companion object {
        const val VIEW_TYPE_LOCATION = 10
        const val VIEW_TYPE_HOT = 11
    }
}