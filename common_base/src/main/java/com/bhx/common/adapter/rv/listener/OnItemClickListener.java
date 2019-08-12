package com.bhx.common.adapter.rv.listener;

import android.view.View;
import com.bhx.common.adapter.rv.holder.ViewHolder;

/**
 * RecyclerView得点击事件
 */
public interface OnItemClickListener {

    void onItemClick(View view, ViewHolder holder, int position);

    boolean onItemLongClick(View view, ViewHolder holder, int position);

}
