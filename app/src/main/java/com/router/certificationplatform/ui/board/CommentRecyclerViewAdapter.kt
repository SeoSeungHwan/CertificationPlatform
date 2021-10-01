package com.router.certificationplatform.ui.board

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.router.certificationplatform.R
import com.router.certificationplatform.model.Board
import com.router.certificationplatform.model.Comment
import kotlinx.android.synthetic.main.fragment_board_info.*
import java.text.SimpleDateFormat


class CommentRecyclerViewAdapter(private val dataSet: ArrayList<Comment>) :
    RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder>() {

    lateinit var context : Context

    interface ItemClick{
        fun onClick(view : View ,position: Int,comment_id : String)
    }
    var itemClick : ItemClick? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val comment_writer_tv: TextView
        val comment_contents_tv: TextView
        val comment_time_tv: TextView

        init {
            comment_writer_tv = view.findViewById(R.id.comment_writer_tv)
            comment_contents_tv = view.findViewById(R.id.comment_contents_tv)
            comment_time_tv = view.findViewById(R.id.comment_time_tv)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.board_comment_item, viewGroup, false)

        context = view.context
        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.comment_writer_tv.text ="***"+ dataSet.get(position).nickname.substring(3)
        val nowformat = SimpleDateFormat("yyyyMMddhhmmss")
        val newformat = SimpleDateFormat("M/dd   hh:mm")
        val formatDate = nowformat.parse(dataSet.get(position).time)
        val newformatDate = newformat.format(formatDate)
        viewHolder.comment_time_tv.text = newformatDate
        viewHolder.comment_contents_tv.text = dataSet.get(position).contents
        if(itemClick != null){
            viewHolder?.itemView?.setOnClickListener {
                itemClick?.onClick(it,position,dataSet.get(position).commentId)
            }
        }
    }
    override fun getItemCount() = dataSet.size



}