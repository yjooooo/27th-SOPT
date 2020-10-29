package com.yjoos.term_project

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.profile_item_list.view.*

class SampleViewHolder (itemView: View, listener: SampleAdapter.ItemDragListener):RecyclerView.ViewHolder(itemView){
    //드래그 핸들을 통한 아이템 이동을 구현하고자 함.
    //드래그 핸들 뷰에 터치 리스너를 달아줌.
    //사용자가 ACTION_DOWN 액션을 취했을 때 listener.onStartDrag()를 호출함.
    init {
        //itemView.root 에서 root(xml아이디)이름 같게 하기
        //why? => profile_item_grid.xml과  profile_item_list.xml 두개의 레이아웃이 함께 띄워지는게 아니라 번갈아 띄워지는거라서
        //        아이디 다르게 해서 setOnLongClickListener를 각각 적용하면 띄워지지 않은 레이아웃을 가리켜서 null값때문에 오류남!!
        itemView.setOnLongClickListener { v->
            listener.onStartDrag(this)
            true
        }

    }
    private val title: TextView = itemView.findViewById(R.id.item_title)
    private val date: TextView = itemView.findViewById(R.id.item_date)
    private val imageNum: ImageView = itemView.findViewById(R.id.profile_Image)

    fun onBind(data: SampleData){
        //뷰의 요소들에 실질적으로 데이터를 넣어주는 함수, Adapter에서 해당 함수를 호출할 예정.
        title.text=data.title
        date.text=data.date
        imageNum.setImageResource(data.imageNum)
        itemView.setOnClickListener{
            var detailIntent= Intent(itemView.context, DetailActivity::class.java)

            //클릭한 아이템에 대한 정보를 intent에 담아서 DetailActivity에 보내기
            detailIntent.putExtra("album", data.title)
            detailIntent.putExtra("date", data.date)
            detailIntent.putExtra("producer", data.producer)
            detailIntent.putExtra("genre", data.genre)
            detailIntent.putExtra("image", data.imageNum)
            itemView.context.startActivity(detailIntent)
        }
    }
}