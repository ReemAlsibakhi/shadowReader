package com.reemsib.shadowreader.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.model.School
import kotlinx.android.synthetic.main.school_item.view.*

class SchoolAdapter(var activity: Activity, var data: ArrayList<School>) :
    RecyclerView.Adapter<SchoolAdapter.MyViewHolder>() {

    var mListener: OnItemClickListener?=null

    interface OnItemClickListener {
        fun onClicked(clickedItemPosition: Int, id: Int , schoolName:String)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val country = itemView.tv_school


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(activity).inflate(R.layout.school_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

       holder.country.text = data[position].title


        holder.itemView.setOnClickListener {
            if (mListener != null) {
             mListener!!.onClicked(position, data[position].id,data[position].title)
            }
        }
    }
    fun filterList(filteredList: ArrayList<School>) {
        data = filteredList
        notifyDataSetChanged()
    }
}

