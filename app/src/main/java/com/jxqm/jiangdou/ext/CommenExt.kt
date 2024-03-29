package com.jxqm.jiangdou.ext

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import com.google.gson.Gson
import com.jxqm.jiangdou.R
import kotlinx.android.synthetic.main.fragment_job_work_time.*

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

/**
 * 判断是否可以点击
 */
fun TextView.isEnable(et: EditText, method: () -> Boolean) {
    val textView = this
    et.addTextChangedListener {
        afterTextChanged {
            textView.isEnabled = method()
        }
    }
}

/**
 * 判断EditText在右边输入
 */
fun EditText.isRightInput() {
    gravity = Gravity.START or Gravity.CENTER_VERTICAL
    textDirection = View.TEXT_DIRECTION_RTL
    addTextChangedListener {
        afterTextChanged {
            if (TextUtils.isEmpty(it.toString())) {
                gravity = Gravity.START or Gravity.CENTER_VERTICAL
                textDirection = View.TEXT_DIRECTION_RTL
            } else {
                gravity = Gravity.END or Gravity.CENTER_VERTICAL
                textDirection = View.TEXT_DIRECTION_LTR
            }
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

internal fun EditText.showSoftInput() {
    this.isFocusable = true
    this.isFocusableInTouchMode = true
    this.requestFocus()
    val inputManager =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(this, 0)
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


/**
 * 扩展gson解析
 */
inline fun <reified T> Gson.fromJson(json: String) = fromJson(json, T::class.java)

/**
 * 隐藏软键盘
 */
fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)

}