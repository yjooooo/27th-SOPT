package com.yjoos.term_project.rcv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yjoos.term_project.R

class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.searchItem_title)
    private val content: TextView = itemView.findViewById(R.id.searchItem_content)
    private val url: TextView = itemView.findViewById(R.id.searchItem_url)
    fun onBind(data: SearchData){
        title.text = data.title
        content.text = data.contents
        url.text = data.url
    }
}