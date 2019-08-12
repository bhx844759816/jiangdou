package com.bhx.common.base;

import androidx.annotation.DimenRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.bhx.common.R;
import com.bhx.common.utils.DensityUtil;


/**
 * Dialog得基类
 * Created by Administrator on 2018/3/2.
 */
public abstract class BaseDialogFragment extends DialogFragment {
    private static final int DEFAULT_STYLES = R.style.dialog;
    Context mContext;
    private boolean isCanceledOnTouchOutside = true;
    private boolean isCancelable = true;

    public BaseDialogFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(getLayoutId(), container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        getDialog().setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        setCancelable(isCancelable);
    }


    /**
     * 获取布局得ID
     *
     * @return
     */
    protected abstract int getLayoutId();


    /**
     * 界面启动
     */
    @Override
    public void onStart() {
        super.onStart();
        initWindow();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, DEFAULT_STYLES);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

   public void initWindow() {

    }

    public void initView(View view) {

    }

    public void initData() {

    }

    void setIsCanceledOnTouchOutside(boolean isCanceledOnTouchOutside) {
        this.isCanceledOnTouchOutside = isCanceledOnTouchOutside;
    }

    void setIsCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }
}

