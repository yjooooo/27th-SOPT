package com.yjoos.hw2
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SampleViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
    private val title: TextView = itemView.findViewById(R.id.item_title)
    private val subtitle: TextView = itemView.findViewById(R.id.item_subtitle)

    fun onBind(data: SampleData){
        //뷰의 요소들에 실질적으로 데이터를 넣어주는 함수, Adapter에서 해당 함수를 호출할 예정.
        title.text=data.title
        subtitle.text=data.subTitle
    }
}