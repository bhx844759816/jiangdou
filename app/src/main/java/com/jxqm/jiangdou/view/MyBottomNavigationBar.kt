package com.jxqm.jiangdou.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.bhx.common.utils.DensityUtil
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.utils.BottomNavigationBarUtils
import java.util.jar.Attributes

/**
 * APP主页面得底部导航栏
 * Created by Administrator on 2019/8/20.
 */
class MyBottomNavigationBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) :
    BottomNavigationBar(context, attributeSet, defStyle) {

    init {
        val homeItemView = BottomNavigationItem(
            R.drawable.icon_home_select,
            "首页"
        ).setInactiveIconResource(R.drawable.icon_home_default)
            .setActiveColorResource(R.color.colorAccent)
            .setInActiveColorResource(R.color.text_hint)

        val workItemView = BottomNavigationItem(
            R.drawable.icon_work_select,
            "工作台"
        ).setInactiveIconResource(R.drawable.icon_work_default)
            .setActiveColorResource(R.color.colorAccent)
            .setInActiveColorResource(R.color.text_hint)

        val myItemView = BottomNavigationItem(
            R.drawable.icon_my_select,
            "我的"
        ).setInactiveIconResource(R.drawable.icon_my_default)
            .setActiveColorResource(R.color.colorAccent)
            .setInActiveColorResource(R.color.text_hint)
        addItem(homeItemView)
            .addItem(workItemView)
            .addItem(myItemView)
            .setBackgroundStyle(BACKGROUND_STYLE_STATIC)
            .setMode(MODE_DEFAULT)
            .setFirstSelectedPosition(0)
            .initialise()
        // 取消自动隐藏
        isAutoHideEnabled = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            elevation = 0f
        }
        setBottomNavigationItem(10, 26, 12)

    }


    private fun setBottomNavigationItem(space: Int, imgLen: Int, textSize: Int) {
        val barClasses = javaClass.superclass
        barClasses?.let {
            val fileds = it.declaredFields
            for (filed in fileds) {
                filed.isAccessible = true
                if ("mTabContainer" == filed.name) {
                    //反射得到 mTabContainer
                    val mTabContainer = filed.get(this) as LinearLayout
                    for (i in 0 until mTabContainer.childCount) {
                        //获取到容器内的各个Tab
                        val view = mTabContainer.getChildAt(i)
                        //获取到Tab内的各个显示控件
                        var params = LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            dpToPx(56)
                        )
                        val container = view.findViewById<View>(R.id.fixed_bottom_navigation_container) as FrameLayout
                        container.layoutParams = params
                        container.setPadding(dpToPx(12), 0, dpToPx(12), 0)

                        //获取到Tab内的文字控件
                        val labelView =
                            view.findViewById<View>(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title) as TextView
                        //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
                        labelView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize.toFloat())
                        labelView.includeFontPadding = false
                        labelView.setPadding(0, 0, 0, dpToPx(20 - textSize - space / 2))
                        //获取到Tab内的图像控件
                        val iconView =
                            view.findViewById<View>(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_icon) as ImageView
                        //设置图片参数，其中，MethodUtils.dip2px()：换算dp值
                        params = LayoutParams(dpToPx(imgLen), dpToPx(imgLen))
                        params.setMargins(0, 0, 0, space / 2)
                        params.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                        iconView.layoutParams = params

                    }
                }
            }
        }
    }

    private fun dpToPx(dpValue: Int): Int {
        val scale = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


}