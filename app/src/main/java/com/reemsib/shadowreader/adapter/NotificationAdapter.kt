package com.reemsib.shadowreader.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.model.Notification
import kotlinx.android.synthetic.main.notification_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationAdapter (var activity: Activity, var data: ArrayList<Notification>):
    RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {


    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClicked(clickedItemPosition: Int, id: String,rate:String,pos:String,neg:String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lesson = itemView.tv_lessonName
        val videoName = itemView.tv_videoName
        val createdAt = itemView.tv_createdAt
        val positive = itemView.tv_positive
        val negatives = itemView.tv_negatives
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(activity).inflate(R.layout.notification_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.lesson.text = data[position].lesson_name
        holder.videoName.text = data[position].video_name
        holder.createdAt.text = getFormatDate(data[position].created_at)
        holder.positive.text = data[position].positive_review
        holder.negatives.text = data[position].negative_review

        holder.itemView.setOnClickListener {
            if (mListener != null) {
              mListener!!.onClicked(position, data[position].id,
                  data[position].rate,
                  data[position].positive_review,
                  data[position].negative_review)
            }
        }
    }
    private fun getFormatDate(createdAt: String):String {
        val date1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(createdAt)
        val dateFormat = SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH)
        dateFormat.timeZone = TimeZone.getDefault()
        val s= dateFormat.format(date1!!)
        return s
    }
}




