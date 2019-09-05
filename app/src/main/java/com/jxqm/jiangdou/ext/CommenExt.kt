package com.jxqm.jiangdou.ext

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes

/**
 * Created By bhx On 2019/8/19 0019 09:15
 */
/**
 * 扩展输入框有值的时候btn才可以被点击
 */
fun Button.isEnable(et: EditText, method: () -> Boolean) {
    val btn = this
    et.addTextChangedListener {
        afterTextChanged {
            btn.isEnabled = method()
        }
    }
}
fun TextView.isEnable(et: EditText, method: () -> Boolean) {
    val textView = this
    et.addTextChangedListener {
        afterTextChanged {
            textView.isEnabled = method()
        }
    }
}

fun <T : View> Activity.find(@IdRes id: Int): T {
    return findViewById(id)
}

/**
 * dp转px
 */
internal fun Context.dpf2pxf(dpValue: Float): Float {
    if (dpValue == 0f) return 0f
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f)
}

/**
 * 创建画笔
 */
@JvmOverloads
internal fun <T : View> T.createPaint(colorString: String? = null, @ColorInt color: Int? = null): Paint {
    return Paint().apply {
        this.utilReset(colorString, color)
    }
}

/**
 * 自定义画笔重置方法
 */
@JvmOverloads
internal fun Paint.utilReset(colorString: String? = null, @ColorInt color: Int? = null) {
    this.reset()
    //这里默认值使用白色，可处理掉系统渲染抗锯齿时，人眼可观察到像素颜色
    this.color = color ?: Color.parseColor(colorString ?: "#FFFFFF")
    this.isAntiAlias = true
    this.style = Paint.Style.FILL
    this.strokeWidth = 0f
}