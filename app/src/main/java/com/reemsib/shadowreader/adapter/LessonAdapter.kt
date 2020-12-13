package com.reemsib.shadowreader.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.model.Lesson
import kotlinx.android.synthetic.main.lesson_item.view.*

class LessonAdapter (var activity: Activity, var data: ArrayList<Lesson>):
    RecyclerView.Adapter<LessonAdapter.MyViewHolder>() {


    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClicked(clickedItemPosition: Int, id: Int,lesson:String?)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lesson = itemView.tv_lessonName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(activity).inflate(R.layout.lesson_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.lesson.text = data[position].title
        holder.itemView.setOnClickListener {
            if (mListener != null) {
                mListener!!.onClicked(position, data[position].id,data[position].title)
            }
        }
        //holder.isCompleted.text = data[position].isComplete.toString()
//        if (data[position].isComplete){
//            holder.isCompleted.text = "Complete"
//           holder.imgCompleted.setImageResource(R.drawable.ic_complete)
//        }else{
//            holder.isCompleted.text = "Not Completed"
//            holder.imgCompleted.setImageResource(R.drawable.ic_not_completed)
//        }
//
//        holder.linearCate.setOnClickListener {
//            holder.expanSubcateg.toggle()
//        }

    }
}




