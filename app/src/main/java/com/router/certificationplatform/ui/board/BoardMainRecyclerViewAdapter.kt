package com.router.certificationplatform.ui.board

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.router.certificationplatform.R
import com.router.certificationplatform.model.Board
import java.text.SimpleDateFormat


class BoardMainRecyclerViewAdapter(private val dataSet: ArrayList<Board>) :
    RecyclerView.Adapter<BoardMainRecyclerViewAdapter.ViewHolder>() {

    lateinit var context : Context

    interface ItemClick{
        fun onClick(view : View ,position: Int,board_id : String)
    }
    var itemClick : ItemClick? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val board_title_tv: TextView
        val board_list_time_tv: TextView
        val board_contents_tv: TextView

        init {
            board_title_tv = view.findViewById(R.id.board_title_tv)
            board_list_time_tv = view.findViewById(R.id.board_list_time_tv)
            board_contents_tv = view.findViewById(R.id.board_contents_tv)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.board_list_item, viewGroup, false)

        context = view.context
        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.board_title_tv.text = dataSet.get(position).title
        val nowformat = SimpleDateFormat("yyyyMddhhmmss")
        val newformat = SimpleDateFormat("M/dd   hh:mm")
        val formatDate = nowformat.parse(dataSet.get(position).time)
        val newformatDate = newformat.format(formatDate)
        viewHolder.board_list_time_tv.text = newformatDate
        viewHolder.board_contents_tv.text = dataSet.get(position).contents
        if(itemClick != null){
            viewHolder?.itemView?.setOnClickListener {
                itemClick?.onClick(it,position,dataSet.get(position).id)
            }
        }
    }
    override fun getItemCount() = dataSet.size



}