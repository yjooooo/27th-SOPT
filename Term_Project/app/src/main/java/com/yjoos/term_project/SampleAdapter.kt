package com.yjoos.term_project

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SampleAdapter(private val context: Context): RecyclerView.Adapter<SampleViewHolder>(){
    var data = mutableListOf<SampleData>()
    var changeViewType = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        var view =
        when (viewType){
            1->{
                LayoutInflater.from(context).inflate(R.layout.profile_item_grid,
                    parent, false)
            }
            else ->{
                LayoutInflater.from(context).inflate(R.layout.profile_item_list,
                    parent, false)
            }
        }

        return SampleViewHolder(view,itemDragListener)
    }

    override fun getItemViewType(position: Int): Int {
        return changeViewType
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

    //아이템 액션 인터페이스 정의
    interface ItemActionListener{
        fun onItemMoved(from: Int, to: Int)
        fun onItemSwiped(position: Int)
    }
    //액션리스너 선언
    private lateinit var itemActionListener: ItemActionListener
    //액션리스너 등록 메소드
    fun setItemActionListener(itemActionListener: ItemActionListener){
        this.itemActionListener = itemActionListener
    }
    fun getItemActionListener(): ItemActionListener{
        return itemActionListener
    }

    //아이템 드래그 인터페이스 정의
    interface ItemDragListener{
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }
    //드래그리스너 선언
    private lateinit var itemDragListener: ItemDragListener
    //드래그ㄹ스너 등록 메소드
    fun setItemDragListener(itemDragListener: ItemDragListener){
        this.itemDragListener = itemDragListener
    }

}