package com.yjoos.term_project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : AppCompatActivity() {
    private lateinit var sampleAdapter: SampleAdapter //나중에 초기화할 것.
    private lateinit var itemTouchHelper: ItemTouchHelper
    var list_of_sortType = arrayOf("최신순","발매일순","사용자변경")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val detailIntent = Intent(this, DetailActivity::class.java)
        sampleAdapter = SampleAdapter(this)

        spinner.adapter = ArrayAdapter(this, R.layout.spinner_item, list_of_sortType)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var temp= sampleAdapter.data[0]
                when(spinner.getItemAtPosition(position)){
                    "최신순" -> {
                        for(i in 0 until sampleAdapter.data.size-1 ){
                            for(j in 0 until sampleAdapter.data.size-1-i){
                                if(sampleAdapter.data[j].seqNum < sampleAdapter.data[j+1].seqNum){
                                    temp=sampleAdapter.data[j]
                                    sampleAdapter.data[j]=sampleAdapter.data[j+1]
                                    sampleAdapter.data[j+1]=temp
                                }
                            }

                        }
                        sampleAdapter.notifyDataSetChanged()
                    }
                    "발매일순" -> {
                        for(i in 0 until sampleAdapter.data.size-1 ){
                            for(j in 0 until sampleAdapter.data.size-1-i){
                                if(sampleAdapter.data[j].seqNum > sampleAdapter.data[j+1].seqNum){
                                    temp=sampleAdapter.data[j]
                                    sampleAdapter.data[j]=sampleAdapter.data[j+1]
                                    sampleAdapter.data[j+1]=temp
                                }
                            }

                        }
                        sampleAdapter.notifyDataSetChanged()
                    }
                    "사용자변경"->{
                    }
                }
            }
        }



        val spaceDecoration = SampleItemDeco(20)
        main_rcv.apply {
            adapter = sampleAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
            addItemDecoration(spaceDecoration)
        }

        sampleAdapter.data = mutableListOf(
            SampleData(1, 16,"꽃길 ", "2018.03.13", "G-Dragon", "알앤비", R.drawable.flower_road),
            SampleData(1, 15,"MADE", "2016.12.13", "G-Dragon", "댄스, 발라드", R.drawable.made),
            SampleData(1,14,"STILL ALIVE", "2012.06.03", "G-Dragon", "일렉트로닉, 팝, 댄스", R.drawable.special_alive),
            SampleData(1, 13,"ALIVE", "2012.02.29", "G-Dragon", "발라드, 댄스, 팝, 힙합", R.drawable.mini5),
            SampleData(1, 12,"스페셜에디션", "2011.04.08", "G-Dragon", "댄스, 팝", R.drawable.special),
            SampleData(1, 11,"미니앨범 4집", "2011.02.24", "G-Dragon", "댄스, 팝, 힙합", R.drawable.mini4),
            SampleData(1, 10,"BigBang", "2009.08.19", "G-Dragon", "댄스, 팝, 힙합", R.drawable.bigbang),
            SampleData(1, 9,"Remember", "2008.11.05", "G-Dragon", "댄스, 팝, 힙합", R.drawable.remember),
            SampleData(1, 8,"Number1", "2008.10.09", "G-Dragon", "댄스, 팝, 힙합", R.drawable.number1),
            SampleData(1, 7,"Stand Up", "2008.08.08", "G-Dragon", "댄스, 팝, 힙합", R.drawable.standup),
            SampleData(1, 6,"For The World", "2008.01.04", "G-Dragon", "댄스, 팝, 힙합", R.drawable.for_the_world),
            SampleData(1, 5,"Hot Issue", "2007.11.22", "G-Dragon", "댄스, 팝, 힙합", R.drawable.mini2),
            SampleData(1, 4,"Always", "2007.08.16", "G-Dragon", "댄스, 팝, 힙합", R.drawable.always),
            SampleData(1, 3,"Bigbang 001", "2006.12.22", "G-Dragon", "틴 팝, 댄스, 팝, 힙합", R.drawable.bigbang001),
            SampleData(1, 2,"Bigbang 03", "2006.12.22", "G-Dragon", "힙합, 팝", R.drawable.bigbang03),
            SampleData(1, 1,"Bigbang is V.I.P", "2006.12.22", "G-Dragon", "틴 팝, 힙합", R.drawable.bigbang_is_vip),
            SampleData(1, 0,"Bigbang First Single", "2006.12.22", "G-Dragon", "틴 팝, 댄스 팝", R.drawable.first_single)
        )
        //Adapter에 데이터가 갱신되었다고 알려주기!
        sampleAdapter.notifyDataSetChanged()

        //아이템 액션 리스너
        sampleAdapter.setItemActionListener(object : SampleAdapter.ItemActionListener {
            override fun onItemMoved(from: Int, to: Int) {
                if (from == to) {
                    return
                }

                //이동할 객체를 원래 위치에서 삭제하고 fromItem에 임시로 저장
                val fromItem = sampleAdapter.data.removeAt(from)
                //이동하고 싶은 position에 추가
                sampleAdapter.data.add(to, fromItem)
                //어댑터에 데이터 이동 알림
                sampleAdapter.notifyItemMoved(from, to)
                spinner.setSelection(2)
            }

            override fun onItemSwiped(position: Int) {
                sampleAdapter.data.removeAt(position)
                //어댑터에 데이터 삭제 알림
                sampleAdapter.notifyItemRemoved(position)
                //Log.d("delete", "${sampleAdapter.data.size}개")
                spinner.setSelection(2)
            }

        })
        //아이템 드래그 리스너
        sampleAdapter.setItemDragListener(object : SampleAdapter.ItemDragListener {
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)

            }
        })

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(sampleAdapter.getItemActionListener()))
        itemTouchHelper.attachToRecyclerView(main_rcv)



    }


    //옵션 메뉴 적용하기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // xml을 이용한 메뉴 만들기
        menuInflater.inflate(R.menu.home_option_menu, menu)
        return true
    }

    //옵션메뉴 선택할 경우의 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.logout -> {
                val homeIntent = Intent(this, MainActivity::class.java)
                val idpwShared = getSharedPreferences("idpw", Context.MODE_PRIVATE)
                val idpwEditor = idpwShared.edit()
                idpwEditor.remove("id")
                idpwEditor.remove("pw")
                idpwEditor.commit()
                startActivity(homeIntent)
                finish()
            }
            R.id.change_linear -> {
                for (i in 0 until sampleAdapter.data.size) {
                    sampleAdapter.data[i].viewType = 1
                }

                main_rcv.apply {
                    //adapter = sampleAdapter
                    layoutManager = LinearLayoutManager(this@HomeActivity)
                }
            }
            R.id.change_grid -> {
                for (i in 0 until sampleAdapter.data.size) {
                    sampleAdapter.data[i].viewType = 2
                }
                main_rcv.apply {
                    //adapter = sampleAdapter
                    layoutManager = GridLayoutManager(this@HomeActivity, 3)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}