package com.router.certificationplatform.ui.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.router.certificationplatform.R
import com.router.certificationplatform.model.Board


class BoardRecyclerViewAdapter(private val dataSet: ArrayList<Board>) :
    RecyclerView.Adapter<BoardRecyclerViewAdapter.ViewHolder>() {


    interface ItemClick{
        fun onClick(view : View ,position: Int,board_id : String)
    }
    var itemClick : ItemClick? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val board_title_tv: TextView
        val board_writer_tv: TextView
        val board_contents_tv: TextView

        init {
            board_title_tv = view.findViewById(R.id.board_title_tv)
            board_writer_tv = view.findViewById(R.id.board_writer_tv)
            board_contents_tv = view.findViewById(R.id.board_contents_tv)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.board_list_item, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.board_title_tv.text = dataSet.get(position).title
        viewHolder.board_writer_tv.text = dataSet.get(position).writer
        viewHolder.board_contents_tv.text = dataSet.get(position).contents
        if(itemClick != null){
            viewHolder?.itemView?.setOnClickListener {
                itemClick?.onClick(it,position,dataSet.get(position).id)
            }
        }
    }
    override fun getItemCount() = dataSet.size



}