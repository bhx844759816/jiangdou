package com.bhx.common.adapter.rv;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bhx.common.R;
import com.bhx.common.adapter.rv.base.ItemViewType;
import com.bhx.common.adapter.rv.holder.ViewHolder;
import com.bhx.common.utils.LogUtils;

/**
 * 加载更多的布局
 *
 * @param <T>
 */
public class LoadMoreAdapter<T> extends MultiItemTypeAdapter<T> {

    // 当前加载状态，默认为加载完成
    public int loadState = 2;
    // 正在加载
    public static final int LOADING = 1;
    // 加载完成
    public static final int LOADING_COMPLETE = 2;
    // 加载到底
    public static final int LOADING_END = 3;

    public LoadMoreAdapter(Context context) {
        super(context);
        //添加底部加载更多得布局
        addItemViewType(new ItemViewType<T>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.adapter_load_more_item;
            }

            @Override
            public boolean isItemClickable() {
                return false;
            }

            @Override
            public boolean isViewForType(T item, int position) {
                return mDatas.size() == position;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                ProgressBar progressBar = holder.getView(R.id.id_progressBar);
                TextView contentView = holder.getView(R.id.id_loadingMore);
                TextView noLoadMoreDataView = holder.getView(R.id.id_noLoadMoreData);
                LogUtils.i("loadState=" + loadState);
                switch (loadState) {
                    case LOADING: //正在加载
                        progressBar.setVisibility(View.VISIBLE);
                        contentView.setVisibility(View.VISIBLE);
                        noLoadMoreDataView.setVisibility(View.GONE);
                        break;
                    case LOADING_COMPLETE:
                        progressBar.setVisibility(View.GONE);
                        contentView.setVisibility(View.GONE);
                        noLoadMoreDataView.setVisibility(View.GONE);
                        break;
                    case LOADING_END:
                        progressBar.setVisibility(View.GONE);
                        contentView.setVisibility(View.GONE);
                        noLoadMoreDataView.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    /**
     * 设置加载更多的状态
     *
     * @param state
     */
    void setLoadState(int state) {
        this.loadState = state;
        notifyItemChanged(mDatas.size());
    }

    int getLoadState() {
        return loadState;
    }

    public boolean isOnLoadMore() {
        return this.loadState == LOADING;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mDatas.size()) {
            return mItemViewTypeManager.getViewType(null, position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }
}
