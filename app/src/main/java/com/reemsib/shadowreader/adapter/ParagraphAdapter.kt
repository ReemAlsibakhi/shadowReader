package com.reemsib.shadowreader.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.model.Paragraph
import kotlinx.android.synthetic.main.paragraph_item.view.*

class ParagraphAdapter (var activity: Activity, var data: ArrayList<Paragraph>):
    RecyclerView.Adapter<ParagraphAdapter.MyViewHolder>() {


    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClicked(clickedItemPosition: Int, id: Int,title:String?,video:String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val paragraph = itemView.tv_paragraph
        val watched = itemView.tv_watched
   //     val imgWatched = itemView.img_watched
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(activity).inflate(R.layout.paragraph_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.paragraph.text = data[position].title
        holder.itemView.setOnClickListener {
            if (mListener != null) {
              mListener!!.onClicked(position, data[position].id,data[position].title,data[position].video_path)
            }
        }
    }
}




