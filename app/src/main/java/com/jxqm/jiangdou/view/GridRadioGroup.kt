package com.jxqm.jiangdou.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.RadioGroup

import com.jxqm.jiangdou.R

/**
 * 九宫格的RadioGroup
 */
class GridRadioGroup @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : RadioGroup(context, attrs) {

    private var verticalSpacing = 20f

    private var horizontalSpacing = 12f

    private var numColumns = 3

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.GridRadioGroup)
        numColumns = attributes.getInt(R.styleable.GridRadioGroup_numColumns, numColumns)
        val tempHorSpacing = (HORIZONTAL_SPACING_DIP * context.resources.displayMetrics.density).toInt()
        horizontalSpacing = attributes.getDimension(R.styleable.GridRadioGroup_horizontalSpacing, tempHorSpacing.toFloat())
        val tempVerSpacing = (VERTICAL_SPACING_DIP * context.resources.displayMetrics.density).toInt()
        verticalSpacing = attributes.getDimension(R.styleable.GridRadioGroup_verticalSpacing, tempVerSpacing.toFloat())
        attributes.recycle()

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        val contentWidth = r - l - paddingRight - paddingLeft
        val itemWidth = (contentWidth - horizontalSpacing * (numColumns - 1)).toInt() / numColumns
        var x = paddingLeft// 横坐标开始
        var y = 0//纵坐标开始
        var rows = 0
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val height = view.measuredHeight
            x += itemWidth
            if (i % numColumns == 0) {
                x = paddingLeft + itemWidth
                rows++
            }
            y = rows * height + (rows - 1) * verticalSpacing.toInt() + paddingTop
            view.layout(x - itemWidth, y - height, x, y)
            x += horizontalSpacing.toInt()
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val childCount = childCount
        val specWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val contentWidth = specWidth - paddingRight - paddingLeft
        val itemWidth = (contentWidth - horizontalSpacing * (numColumns - 1)).toInt() / numColumns
        var y = 0//纵坐标开始
        var rows = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(View.MeasureSpec.makeMeasureSpec(itemWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.UNSPECIFIED)
            val height = child.measuredHeight
            if (i % numColumns == 0) {
                rows++
            }
            y = rows * height + (rows - 1) * verticalSpacing.toInt() + paddingTop + paddingBottom
        }

        setMeasuredDimension(specWidth, y)
    }

    companion object {
        private val VERTICAL_SPACING_DIP = 15

        private val HORIZONTAL_SPACING_DIP = 10
    }
}
