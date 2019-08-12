package com.bhx.common.adapter.rv;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhx.common.adapter.rv.base.ItemViewType;
import com.bhx.common.adapter.rv.holder.ItemViewTypeManager;
import com.bhx.common.adapter.rv.holder.ViewHolder;
import com.bhx.common.adapter.rv.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    public List<T> mDatas = new ArrayList<>();
    public Context mContext;
    ItemViewTypeManager<T> mItemViewTypeManager;
    OnItemClickListener mItemListener;

    public MultiItemTypeAdapter(Context context) {
        this.mContext = context;
        mItemViewTypeManager = new ItemViewTypeManager<>();
    }

    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewTypeManager = new ItemViewTypeManager<>();
    }

    /**
     * 设置Item点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewTypeManager()) {
            return super.getItemViewType(position);
        }
        return mItemViewTypeManager.getViewType(mDatas.get(position), position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewType itemViewType = mItemViewTypeManager.getItemViewType(viewType);
        int layoutId = itemViewType.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position >= mDatas.size()) {
            convert(holder, null);
        } else {
            convert(holder, mDatas.get(position));
        }
    }

    private void setListener(final ViewGroup parent, ViewHolder holder, int viewType) {
        if (!isEnabled(viewType))
            return;
        holder.getConvertView().setOnClickListener(v -> {
            if (mItemListener != null) {
                int position = holder.getAdapterPosition();
                mItemListener.onItemClick(v, holder, position);
            }
        });
        holder.getConvertView().setOnLongClickListener(v -> {
            if (mItemListener != null) {
                int position = holder.getAdapterPosition();
                return mItemListener.onItemLongClick(v, holder, position);
            }
            return false;
        });
    }

    /**
     * 用于判断是否viewType是否可以设置点击事件
     *
     * @param viewType 布局得类型
     * @return
     */
    public boolean isEnabled(int viewType) {
        return mItemViewTypeManager.isItemOpenClick(viewType);
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {

    }

    private void convert(ViewHolder holder, T t) {

        mItemViewTypeManager.convert(holder, t, holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public List<T> getDatas() {
        return mDatas;
    }

    public MultiItemTypeAdapter addItemViewType(ItemViewType<T> itemViewDelegate) {
        mItemViewTypeManager.addItemViewType(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewType(int viewType, ItemViewType<T> itemViewType) {
        mItemViewTypeManager.addItemViewType(itemViewType, viewType);
        return this;
    }

    /**
     * 是否是多item布局
     *
     * @return
     */
    private boolean useItemViewTypeManager() {
        return mItemViewTypeManager.getItemViewTypeCount() > 0;
    }

    /**
     * 清空集合，设置适配器数据
     *
     * @param list
     */
    public void setDataList(Collection<T> list) {
        this.mDatas.clear();
        this.mDatas.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 设置适配器的数据，添加数据
     *
     * @param dataList
     */
    public void addDatas(List<T> dataList) {
        if (dataList != null) {
            mDatas.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置适配器数据
     *
     * @param dataList
     */
    public void updateDatas(List<T> dataList) {
        mDatas.clear();
        if (dataList != null) {
            mDatas.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    /**
     * 移除指定位置得数据
     *
     * @param position
     */
    public void remove(int position) {
        if (mDatas != null && mDatas.size() > 0) {
            // 数据移除
            this.mDatas.remove(position);
            // 界面移除
            notifyItemRemoved(position - 1);
            // 刷新位置
            if (position != (mDatas.size())) { // 如果移除的是最后一个，忽略
                notifyItemRangeChanged(position - 1, getItemCount() - position);
            }
        }
    }


}
