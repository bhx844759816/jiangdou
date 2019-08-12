package com.jxqm.jiangdou.adapter

import com.contrarywind.adapter.WheelAdapter

/**
 * Created By bhx On 2019/8/10 0010 09:21
 */
class ArrayWheelAdapter(items: List<String>) : WheelAdapter<String> {
    private var mItems: List<String> = items
    override fun indexOf(o: String?): Int = mItems.indexOf(o)

    override fun getItemsCount(): Int = mItems.size

    override fun getItem(index: Int): String = mItems[index]
}