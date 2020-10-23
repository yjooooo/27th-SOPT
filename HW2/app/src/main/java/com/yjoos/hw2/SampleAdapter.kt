package com.yjoos.hw2

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SampleAdapter(private val context: Context): RecyclerView.Adapter<SampleViewHolder>(){
    var data = mutableListOf<SampleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.profile_item_list,
            parent, false)

        return SampleViewHolder(view)
    }

    override fun getItemCount(): Int = data.size
    /*
    //위의 코드랑 같은 것임. return만 해주는 간단한 코드라 저렇게 작성함.
    override fun getItemCount(): Int{
        return data.size
    }
    */

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.onBind(data[position])
    }
}