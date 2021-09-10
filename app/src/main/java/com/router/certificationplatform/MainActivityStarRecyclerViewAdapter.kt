package com.router.certificationplatform

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.star_list_item.view.*


class MainActivityStarRecyclerViewAdapter(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<MainActivityStarRecyclerViewAdapter.ViewHolder>() {


    interface ItemClick{
        fun onClick(view : View ,position: Int)
    }
    var itemClick : ItemClick? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val certificate_name_tv: TextView

        init {
            certificate_name_tv = view.findViewById(R.id.certificate_name_tv)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.star_list_item, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.certificate_name_tv.text = dataSet.get(position)
        if(itemClick != null){
            viewHolder?.itemView?.setOnClickListener {
                itemClick?.onClick(it,position)
            }
        }
    }
    override fun getItemCount() = dataSet.size



}