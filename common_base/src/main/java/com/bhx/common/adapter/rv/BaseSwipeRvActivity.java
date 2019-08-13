package com.bhx.common.adapter.rv;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.bhx.common.R;
import com.bhx.common.adapter.rv.listener.OnLoadMoreListener;
import com.bhx.common.mvvm.BaseMVVMActivity;
import com.bhx.common.mvvm.BaseViewModel;

import java.util.List;

/**
 * 上拉刷新和下拉加载更多
 * Created By bhx On 2019/7/30 0030 12:01
 */
public abstract class BaseSwipeRvActivity<T extends BaseViewModel> extends BaseMVVMActivity<T>
        implements RvViewCreate, SwipeRefreshHelper.SwipeRefreshListener, OnLoadMoreListener {
    public RvViewHelper rvViewHelper;

    @Override
    protected void initView() {
        super.initView();
        rvViewHelper = new RvViewHelper.Builder(this, this, this).build();
    }

    @Override
    public SwipeRefreshLayout createSwipeRefresh() {
        return findViewById(R.id.swipeRefreshLayout);
    }

    @Override
    public int[] colorRes() {
        return new int[0];
    }

    @Override
    public RecyclerView createRecyclerView() {
        return findViewById(R.id.recyclerView);
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    }

    @Override
    public RecyclerView.ItemDecoration createItemDecoration() {
        return new DividerItemDecoration(this, RecyclerView.VERTICAL);
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
