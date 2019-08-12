package com.bhx.common.adapter.rv;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 创建列表布局的接口封装类
 * Created By bhx On 2019/7/30 0030 11:49
 */
public interface RvViewCreate<T> {
    /**
     * 获取下拉刷新的SwipeRefreshLayout
     *
     * @return
     */
    SwipeRefreshLayout createSwipeRefresh();

    /**
     * 获取下拉刷新的颜色数组
     *
     * @return
     */
    int[] colorRes();

    /**
     * 获取RecyclerView
     *
     * @return
     */
    RecyclerView createRecyclerView();

    /**
     * 创建RecyclerView的Adapter
     *
     * @return
     */
    MultiItemTypeAdapter<T> createRecycleViewAdapter();

    /**
     * 创建RecyclerView显示的模式
     *
     * @return
     */
    RecyclerView.LayoutManager createLayoutManager();

    /**
     * 创建分割线
     *
     * @return
     */
    RecyclerView.ItemDecoration createItemDecoration();

    /**
     * 请求数据的起始页码
     *
     * @return
     */
    int startPageNum();

    /**
     * 是否支持分页
     *
     * @return
     */
    boolean isSupportPaging();


}
