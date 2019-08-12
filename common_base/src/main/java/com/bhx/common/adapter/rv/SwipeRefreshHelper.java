package com.bhx.common.adapter.rv;

import androidx.annotation.ColorRes;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.annotations.NonNull;

/**
 * 下拉刷新的帮助类
 * Created By bhx On 2019/7/30 0030 11:39
 */
public class SwipeRefreshHelper {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRefreshListener mSwipeRefreshListener;

    static SwipeRefreshHelper create(@NonNull SwipeRefreshLayout layout, @ColorRes int... colorRes) {
        return new SwipeRefreshHelper(layout, colorRes);
    }

    private SwipeRefreshHelper(@NonNull SwipeRefreshLayout layout, @ColorRes int... colorRes) {
        this.mSwipeRefreshLayout = layout;
        init(colorRes);
    }

    private void init(int... colorRes) {
        if (colorRes == null || colorRes.length == 0) {
            mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                    android.R.color.holo_green_dark, android.R.color.holo_orange_dark);
        } else {
            mSwipeRefreshLayout.setColorSchemeResources(colorRes);
        }
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (mSwipeRefreshListener != null) {
                mSwipeRefreshListener.refresh();
            }
        });
    }

    /**
     * 设置刷新回调
     *
     * @param listener
     */
    public void setSwipeRefreshListener(SwipeRefreshListener listener) {
        this.mSwipeRefreshListener = listener;
    }

    public interface SwipeRefreshListener {
        void refresh();
    }

}
