package com.yjoos.term_project

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SampleAdapter(private val context: Context): RecyclerView.Adapter<SampleViewHolder>(){
    var data = mutableListOf<SampleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.profile_item_list,
        parent, false)
        when (viewType){
            1->{
                view = LayoutInflater.from(context).inflate(R.layout.profile_item_list,
                    parent, false)
            }
            2->{
                view = LayoutInflater.from(context).inflate(R.layout.profile_item_grid,
                    parent, false)
            }
        }

        return SampleViewHolder(view,itemDragListener)
    }

    override fun getItemViewType(position: Int): Int {
        data?.let{
            return it[position].viewType
        }?:kotlin.run{
            return super.getItemViewType(position)
        }
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

        //view에 onClickListenr를 달고, 그 안에서 직접 만든 itemClickListener를 연결시킨다.
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }

    }

    //클릭 인터페이스 정의
    interface ItemClickListener{
        fun onClick(view: View, position: Int)
    }
    //클릭리스너 선언
    private lateinit var itemClickListener: ItemClickListener
    //클릭리스너 등록 메소드
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
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