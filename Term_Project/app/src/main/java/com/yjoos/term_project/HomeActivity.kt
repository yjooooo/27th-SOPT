package com.yjoos.term_project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var sampleAdapter: SampleAdapter //나중에 초기화할 것.
    private lateinit var itemTouchHelper: ItemTouchHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val detailIntent = Intent(this, DetailActivity::class.java)

        sampleAdapter = SampleAdapter(this)
        //sampleViewHolder= SampleViewHolder()
        val spaceDecoration = SampleItemDeco(20)
        main_rcv.apply {
            adapter = sampleAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
            addItemDecoration(spaceDecoration)
        }

        /*
        main_rcv.adapter = sampleAdapter
        main_rcv.layoutManager = LinearLayoutManager(this)
        */
        sampleAdapter.data = mutableListOf(
            SampleData(1,"꽃길 ", "2012.02.29", "ttt", "댄스, 발라드", R.drawable.flower_road),
            SampleData(1,"MADE", "2016.12.13", "G-Dragon", "댄스, 발라드", R.drawable.made),
            SampleData(1,"STILL ALIVE", "서울여자대학교", "G-Dragon", "댄스, 발라드", R.drawable.special_alive),
            SampleData(1,"ALIVE", "서울여자대학교", "G-Dragon", "댄스, 발라드", R.drawable.mini5),
            SampleData(1,"스페셜에디션", "안드로이드", "G-Dragon", "댄스, 발라드", R.drawable.special),
            SampleData(1,"미니앨범 4집", "www.github.com/yjooooo", "G-Dragon", "댄스, 발라드", R.drawable.mini4),
            SampleData(1,"BigBang", "www.sopt.org", "G-Dragon", "댄스, 발라드", R.drawable.bigbang),
            SampleData(1,"Remember", "2012.02.29", "G-Dragon", "댄스, 발라드", R.drawable.remember),
            SampleData(1,"Number1", "2016.12.13", "G-Dragon", "댄스, 발라드", R.drawable.number1),
            SampleData(1,"Stand Up", "서울여자대학교", "G-Dragon", "댄스, 발라드", R.drawable.standup),
            SampleData(1,"For The World", "안드로이드", "G-Dragon", "댄스, 발라드", R.drawable.for_the_world),
            SampleData(1,"Hot Issue", "www.github.com/yjooooo", "G-Dragon", "댄스, 발라드", R.drawable.mini2),
            SampleData(1,"Always", "www.sopt.org", "G-Dragon", "댄스, 발라드", R.drawable.always),
            SampleData(1,"Bigbang 001", "www.sopt.org", "G-Dragon", "댄스, 발라드", R.drawable.bigbang001)
        )


        //아이템 클릭리스너
        sampleAdapter.setItemClickListener(object : SampleAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                //Log.d("click", "${position}번 리스트 선택")
                //클릭한 아이템에 대한 정보를 intent에 담아서 DetailActivity에 보내기
                detailIntent.putExtra("album",sampleAdapter.data[position].title)
                detailIntent.putExtra("date",sampleAdapter.data[position].date)
                detailIntent.putExtra("producer",sampleAdapter.data[position].producer)
                detailIntent.putExtra("genre",sampleAdapter.data[position].genre)
                detailIntent.putExtra("image",sampleAdapter.data[position].imageNum)
                startActivity(detailIntent)
            }
        })


        //아이템 액션 리스너
        sampleAdapter.setItemActionListener(object: SampleAdapter.ItemActionListener{
            override fun onItemMoved(from: Int, to: Int){
                if(from==to){
                    return
                }
                val fromItem = sampleAdapter.data.removeAt(from)
                sampleAdapter.data.add(to, fromItem)
                sampleAdapter.notifyItemMoved(from, to)
            }
            override fun onItemSwiped(position: Int){
                sampleAdapter.data.removeAt(position)
                sampleAdapter.notifyItemRemoved(position)
            }
        })
        sampleAdapter.setItemDragListener(object: SampleAdapter.ItemDragListener{
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder){
                itemTouchHelper.startDrag(viewHolder)
            }
        })

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(sampleAdapter.getItemActionListener()))
        itemTouchHelper.attachToRecyclerView(main_rcv)


        //Adapter에 데이터가 갱신되었다고 알려주기!
        sampleAdapter.notifyDataSetChanged()
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
                for(i in 0 until sampleAdapter.data.size){
                    sampleAdapter.data[i].viewType=1
                }
                main_rcv.apply {
                    adapter = sampleAdapter
                    layoutManager = LinearLayoutManager(this@HomeActivity)
                }
            }
            R.id.change_grid -> {
                for(i in 0 until sampleAdapter.data.size){
                    sampleAdapter.data[i].viewType=2
                }
                main_rcv.apply {
                    adapter = sampleAdapter
                    layoutManager = GridLayoutManager(this@HomeActivity, 3)
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }
}