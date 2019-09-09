package com.jxqm.jiangdou.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 封装扩展函数
 */

/**
 * 扩展StartActivity
 */
inline fun <reified T : Activity> Context.startActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}
/**
 * 扩展StartActivity
 */
inline fun <reified T : Activity> Fragment.startActivity() {
    val intent = Intent(context, T::class.java)
    startActivity(intent)
}
inline fun <reified T : Activity> Context.startActivity(bundle: Bundle) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundle)
    startActivity(intent)
}

