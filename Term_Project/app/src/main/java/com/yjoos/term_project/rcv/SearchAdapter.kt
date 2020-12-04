package com.yjoos.term_project.rcv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yjoos.term_project.R

class SearchAdapter(private val context: Context): RecyclerView.Adapter<SearchViewHolder>() {
    var data = mutableListOf<SearchData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        var view =LayoutInflater.from(context).inflate(R.layout.search_item_list, parent, false)

        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.onBind(data[position])
    }
}