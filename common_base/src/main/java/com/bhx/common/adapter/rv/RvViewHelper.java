package com.bhx.common.adapter.rv;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bhx.common.adapter.rv.listener.OnLoadMoreListener;

import java.util.List;

/**
 * 管理BaseRvActivity
 * Created By bhx On 2019/7/30 0030 12:02
 */
public class RvViewHelper<T> {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private MultiItemTypeAdapter<T> mMultiItemTypeAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private int[] mColorRes;
    private boolean isSupportPaging;
    private int mStartPageNum;
    private SwipeRefreshHelper.SwipeRefreshListener mRefreshListener;
    private OnLoadMoreListener mLoadMoreListener;
    private int mCurrentPageNum;//当前页数
    private SwipeRefreshHelper mSwipeRefreshHelper;
    private boolean isSlidingUpward;//

    private RvViewHelper(Builder<T> builder) {
        mSwipeRefreshLayout = builder.create.createSwipeRefresh();
        mRecyclerView = builder.create.createRecyclerView();
        mMultiItemTypeAdapter = builder.create.createRecycleViewAdapter();
        mLayoutManager = builder.create.createLayoutManager();
        mItemDecoration = builder.create.createItemDecoration();
        mColorRes = builder.create.colorRes();
        isSupportPaging = builder.create.isSupportPaging();
        mStartPageNum = builder.create.startPageNum();
        mRefreshListener = builder.mSwipeRefreshListener;
        mLoadMoreListener = builder.mLoadMoreListener;
        mCurrentPageNum = mStartPageNum;
        if (mSwipeRefreshLayout != null) {
            if (mColorRes == null || mColorRes.length == 0) {
                mSwipeRefreshHelper = SwipeRefreshHelper.create(mSwipeRefreshLayout);
            } else {
                mSwipeRefreshHelper = SwipeRefreshHelper.create(mSwipeRefreshLayout, mColorRes);
            }
        }
        init();
    }

    private void init() {
        if (mRecyclerView == null) {
            throw new RuntimeException("RecyclerView can  not  null");
        }
        if (mLayoutManager == null) {
            throw new RuntimeException("LayoutManager can not null");
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (mItemDecoration != null) {
            mRecyclerView.addItemDecoration(mItemDecoration);
        }
        //如果支持加载更多
        if (isSupportPaging) {
            if (!(mMultiItemTypeAdapter instanceof LoadMoreAdapter)) {
                throw new RuntimeException("adapter is must implement LoadMoreAdapter");
            }
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
//
//                    if (isSlideToBottom(mRecyclerView) && !isOnLoadMore() && isSlidingUpward) {
//                        if (mLoadMoreListener != null) {
//                            setOnLoadMoreState(LoadMoreAdapter.LOADING);
//                            mRecyclerView.smoothScrollToPosition(mMultiItemTypeAdapter.getItemCount());
//                            mLoadMoreListener.loadMore();
//                        }
//                    }
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    // 当不滑动时
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && manager != null) {
                        //获取最后一个完全显示的itemPosition
                        int lastItemPosition = manager.findLastVisibleItemPosition();
                        int itemCount = manager.getItemCount();
                        // 判断是否滑动到了最后一个item，并且是向上滑动 且当前加载已完成
                        if (lastItemPosition == (itemCount - 1) && isSlidingUpward && !isOnLoadMore() && getOnLoadMoreState() != LoadMoreAdapter.LOADING_END) {
                            //加载更多
                            if (mLoadMoreListener != null) {
                                setOnLoadMoreState(LoadMoreAdapter.LOADING);
                                mRecyclerView.smoothScrollToPosition(mMultiItemTypeAdapter.getItemCount());
                                mLoadMoreListener.loadMore();
                            }
                        }
                    }
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    isSlidingUpward = dy > 0;
//                    if (dy > dx && dy > 0) {
//                        isSlidingUpward = true;
//                    } else {
//                        isSlidingUpward = false;
//                    }
                }
            });
        }
        //设置下拉刷新
        mSwipeRefreshHelper.setSwipeRefreshListener(() -> {
            mCurrentPageNum = mStartPageNum;
            if (mRefreshListener != null) {
                //调用BaseRvActivity的刷新回调
                mRefreshListener.refresh();
            }
        });
    }

    /**
     * 判断RecyclerView是否滚动到底部
     *
     * @param recyclerView
     * @return
     */
    private boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    public MultiItemTypeAdapter<T> getAdapter() {
        return mMultiItemTypeAdapter;
    }

    /**
     * 取消显示下拉刷新控件
     */
    public void dismissSwipeRefresh() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * 设置加载更多的状态
     *
     * @param state
     */
    public void setOnLoadMoreState(int state) {
        if (isSupportPaging) {
            LoadMoreAdapter adapter = (LoadMoreAdapter) mMultiItemTypeAdapter;
            adapter.setLoadState(state);

        }
    }

    public int getOnLoadMoreState() {
        if (isSupportPaging) {
            LoadMoreAdapter adapter = (LoadMoreAdapter) mMultiItemTypeAdapter;
            return adapter.getLoadState();
        }
        throw new RuntimeException("isSupportPaging is false not support loadMore");
    }

    /**
     * 是否正在加载更多
     *
     * @return
     */
    public boolean isOnLoadMore() {
        if (isSupportPaging) {
            LoadMoreAdapter adapter = (LoadMoreAdapter) mMultiItemTypeAdapter;
            return adapter.isOnLoadMore();
        }
        return false;
    }

    /**
     * 更新Item显示
     *
     * @param data
     */
    public void notifyAdapterDataSetChanged(List data) {
        if (mCurrentPageNum == mStartPageNum) {
            mMultiItemTypeAdapter.updateDatas(data);
        } else {
            mMultiItemTypeAdapter.addDatas(data);
        }
        mRecyclerView.setAdapter(mMultiItemTypeAdapter);
    }

    public static class Builder<T> {
        private final RvViewCreate<T> create;
        private final SwipeRefreshHelper.SwipeRefreshListener mSwipeRefreshListener;
        private final OnLoadMoreListener mLoadMoreListener;

        public Builder(RvViewCreate<T> create, SwipeRefreshHelper.SwipeRefreshListener listener, OnLoadMoreListener loadMoreListener) {
            this.create = create;
            this.mSwipeRefreshListener = listener;
            this.mLoadMoreListener = loadMoreListener;
        }

        public RvViewHelper build() {
            return new RvViewHelper<>(this);
        }
    }
}
