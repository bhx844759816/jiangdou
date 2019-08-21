package com.bhx.common.adapter.rv;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bhx.common.R;
import com.bhx.common.adapter.rv.listener.OnLoadMoreListener;
import com.bhx.common.mvvm.BaseMVVMFragment;
import com.bhx.common.mvvm.BaseViewModel;

import java.util.List;

/**
 * Created by Administrator on 2019/8/17.
 */
public abstract class BaseSwipRvFragment<T extends BaseViewModel>  extends BaseMVVMFragment<T> implements RvViewCreate, SwipeRefreshHelper.SwipeRefreshListener, OnLoadMoreListener {
    public RvViewHelper rvViewHelper;
    @Override
    protected void initView(Bundle bundle) {
        super.initView(bundle);
        rvViewHelper = new RvViewHelper.Builder(this, this, this).build();
    }

    @Override
    public SwipeRefreshLayout createSwipeRefresh() {
         return rootView.findViewById(R.id.swipeRefreshLayout);
    }

    @Override
    public int[] colorRes() {
        return new int[0];
    }

    @Override
    public RecyclerView createRecyclerView() {
        return rootView.findViewById(R.id.recyclerView);
    }


    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
    }

    @Override
    public int startPageNum() {
        return 0;
    }

    @Override
    public boolean isSupportPaging() {
        return false;
    }

    public void notifyAdapterDataSetChanged(List data) {
        rvViewHelper.notifyAdapterDataSetChanged(data);
    }
}
