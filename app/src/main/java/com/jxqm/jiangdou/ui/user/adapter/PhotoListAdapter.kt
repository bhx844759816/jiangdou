package com.jxqm.jiangdou.ui.user.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bhx.common.utils.LogUtils
import com.bumptech.glide.Glide
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.ui.user.view.PhotoViewPageActivity
import com.jxqm.jiangdou.utils.startActivity
import java.io.File
import java.util.ArrayList

/**
 * Created By bhx On 2019/8/19 0019 15:28
 */
class PhotoListAdapter(context: Context, fileList: List<Any>) :
    RecyclerView.Adapter<PhotoListAdapter.MyHolder>() {
    private val mContext: Context = context
    private val mInflater = LayoutInflater.from(context)
    private val mFileList = fileList
    private var mAddCallBack: (() -> Unit)? = null
    private var mDeleteCallBack: ((Int) -> Unit)? = null
    var mClickItemCallBack: (() -> Unit)? = null
    private var maxSelectPhotoCounts = 9

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = mInflater.inflate(R.layout.adapter_photo_list, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        if (mFileList.size < maxSelectPhotoCounts) {
            return mFileList.size + 1
        }
        return mFileList.size
    }

    public fun setMaxSelectPhotoCounts(counts: Int) {
        maxSelectPhotoCounts = counts
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        if (position < mFileList.size) {
            holder.addView.setBackgroundResource(0)
            Glide.with(mContext).load(mFileList[position]).into(holder.addView)
            holder.deleteView.visibility = View.VISIBLE
        } else {
            holder.addView.setBackgroundResource(0)
            Glide.with(mContext).load(R.drawable.icon_add_photo).into(holder.addView)
            holder.deleteView.visibility = View.GONE
        }

        holder.addView.setOnClickListener {
            if (position == mFileList.size) {
                mAddCallBack?.invoke()
            } else {
                val intent = Intent(mContext, PhotoViewPageActivity::class.java)
                val list = mFileList.map {
                    if (it is File) {
                        it.absolutePath
                    } else {
                        it as String
                    }
                }
                intent.putStringArrayListExtra("ImageUrls", list as ArrayList<String>?)
                mContext.startActivity(intent)
            }
        }
        holder.deleteView.setOnClickListener {
            mDeleteCallBack?.invoke(position)
        }

    }

    public fun setDeleteCallBack(callBack: (Int) -> Unit) {
        this.mDeleteCallBack = callBack
    }

    public fun setAddCallBack(callBack: () -> Unit) {
        this.mAddCallBack = callBack
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addView: ImageView = itemView.findViewById(R.id.ivAddPhoto)
        val deleteView: ImageView = itemView.findViewById(R.id.ivDeletePhoto)
    }

    public fun setMaxSelectCount(count: Int) {
        maxSelectPhotoCounts = count
    }

}