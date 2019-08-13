package com.bhx.common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bhx.common.R;
import com.jaeger.library.StatusBarUtil;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    private CompositeDisposable mDisposables;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setStatusBarColor();
        initView();
        initData();
    }


    protected abstract int getLayoutId();

    protected void initView() {

    }

    protected void initData() {

    }

    protected void addDisposable(Disposable disposable) {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposables != null) {
            mDisposables.clear();
            mDisposables = null;
        }
    }

    protected void setStatusBarColor() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent));
    }
}
