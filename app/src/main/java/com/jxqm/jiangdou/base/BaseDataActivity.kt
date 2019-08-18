package com.jxqm.jiangdou.base

import androidx.appcompat.widget.DialogTitle
import androidx.appcompat.widget.Toolbar
import com.bhx.common.mvvm.BaseMVVMActivity
import com.bhx.common.mvvm.BaseViewModel
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * 公共类
 * Created By bhx On 2019/8/6 0006 09:38
 */
abstract class BaseDataActivity<T : BaseViewModel<*>> : BaseMVVMActivity<T>()