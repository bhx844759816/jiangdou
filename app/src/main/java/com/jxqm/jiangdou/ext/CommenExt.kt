package com.jxqm.jiangdou.ext

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.EditText
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

fun <T : View> Activity.find(@IdRes id: Int): T {
    return findViewById(id)
}