package com.yjoos.term_project.viewpager_family

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yjoos.term_project.rcv.ItemTouchHelperCallback
import com.yjoos.term_project.R
import com.yjoos.term_project.connect_server.SampleRcvListResponseData
import com.yjoos.term_project.connect_server.SampleResponseData
import com.yjoos.term_project.connect_server.SampleServiceImpl
import com.yjoos.term_project.connect_server.SampleSignupRequestData
import com.yjoos.term_project.rcv.SampleAdapter
import com.yjoos.term_project.rcv.SampleData
import com.yjoos.term_project.rcv.SampleItemDeco
import kotlinx.android.synthetic.main.fragment_music_album.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MusicAlbumFragment : Fragment() {

    private lateinit var sampleAdapter: SampleAdapter //나중에 초기화할 것.
    private lateinit var itemTouchHelper: ItemTouchHelper
    private var rcvListData = mutableListOf<SampleData>()

    //var list_of_sortType = arrayOf("최신순","발매일순","사용자변경")  //스피너 목록
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_music_album, container, false)

        setHasOptionsMenu(true)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sampleAdapter = SampleAdapter(view.context)

        val spaceDecoration = SampleItemDeco(20) //리사이클러뷰 아이템간 간격 설정
        main_rcv.apply {
            adapter = sampleAdapter
            layoutManager = LinearLayoutManager(view.context)
            addItemDecoration(spaceDecoration) //간격 설정한거 데코로 설정
        }



        val call: Call<SampleRcvListResponseData> = SampleServiceImpl.dummyService.getList(page = 2)
        call.enqueue(object: Callback<SampleRcvListResponseData> {
            override fun onFailure(call: Call<SampleRcvListResponseData>, t: Throwable) {
                //통신 실패 로직
            }

            override fun onResponse(
                call: Call<SampleRcvListResponseData>,
                response: Response<SampleRcvListResponseData>
            ) {
                response.takeIf{ it.isSuccessful }
                    ?.body()
                    ?.let{ data ->
                        for(i in 0 until data.data.size){
                            rcvListData.add(SampleData(i,data.data[i].first_name,data.data[i].email, data.data[i].last_name, data.data[i].avatar))
                        }
                        sampleAdapter.data = rcvListData
                        //Adapter에 데이터가 갱신되었다고 알려주기!
                        sampleAdapter.notifyDataSetChanged()
                    } ?: showError(response.errorBody())
            }

            private fun showError(error: ResponseBody?) {
                val e = error ?: return
                val ob = JSONObject(e.string())
                Toast.makeText(view.context, ob.getString("message"),Toast.LENGTH_SHORT).show()
            }
        })

        //스피너 만들었던 코드
//        spinner.adapter = ArrayAdapter(view.context, R.layout.spinner_item, list_of_sortType)
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                var temp= sampleAdapter.data[0]
//                when(spinner.getItemAtPosition(position)){
//                    "최신순" -> {
//                        for(i in 0 until sampleAdapter.data.size-1 ){
//                            for(j in 0 until sampleAdapter.data.size-1-i){
//                                if(sampleAdapter.data[j].seqNum < sampleAdapter.data[j+1].seqNum){
//                                    temp=sampleAdapter.data[j]
//                                    sampleAdapter.data[j]=sampleAdapter.data[j+1]
//                                    sampleAdapter.data[j+1]=temp
//                                }
//                            }
//
//                        }
//                        sampleAdapter.notifyDataSetChanged()
//                    }
//                    "발매일순" -> {
//                        for(i in 0 until sampleAdapter.data.size-1 ){
//                            for(j in 0 until sampleAdapter.data.size-1-i){
//                                if(sampleAdapter.data[j].seqNum > sampleAdapter.data[j+1].seqNum){
//                                    temp=sampleAdapter.data[j]
//                                    sampleAdapter.data[j]=sampleAdapter.data[j+1]
//                                    sampleAdapter.data[j+1]=temp
//                                }
//                            }
//
//                        }
//                        sampleAdapter.notifyDataSetChanged()
//                    }
//                    "사용자변경"->{
//                    }
//                }
//            }
//        }





//        sampleAdapter.data = rcvListData

//        sampleAdapter.data = mutableListOf(
//            SampleData(16,"꽃길 ", "2018.03.13", "G-Dragon", "알앤비", R.drawable.flower_road),
//            SampleData(15,"MADE", "2016.12.13", "G-Dragon", "댄스, 발라드", R.drawable.made),
//            SampleData(14,"STILL ALIVE", "2012.06.03", "G-Dragon", "일렉트로닉, 팝, 댄스",
//                R.drawable.special_alive
//            ),
//            SampleData(13,"ALIVE", "2012.02.29", "G-Dragon", "발라드, 댄스, 팝, 힙합", R.drawable.mini5),
//            SampleData(12,"스페셜에디션", "2011.04.08", "G-Dragon", "댄스, 팝", R.drawable.special),
//            SampleData(11,"미니앨범 4집", "2011.02.24", "G-Dragon", "댄스, 팝, 힙합", R.drawable.mini4),
//            SampleData(10,"BigBang", "2009.08.19", "G-Dragon", "댄스, 팝, 힙합", R.drawable.bigbang),
//            SampleData(9,"Remember", "2008.11.05", "G-Dragon", "댄스, 팝, 힙합", R.drawable.remember),
//            SampleData(8,"Number1", "2008.10.09", "G-Dragon", "댄스, 팝, 힙합", R.drawable.number1),
//            SampleData(7,"Stand Up", "2008.08.08", "G-Dragon", "댄스, 팝, 힙합", R.drawable.standup),
//            SampleData(6,"For The World", "2008.01.04", "G-Dragon", "댄스, 팝, 힙합",
//                R.drawable.for_the_world
//            ),
//            SampleData(5,"Hot Issue", "2007.11.22", "G-Dragon", "댄스, 팝, 힙합", R.drawable.mini2),
//            SampleData(4,"Always", "2007.08.16", "G-Dragon", "댄스, 팝, 힙합", R.drawable.always),
//            SampleData(3,"Bigbang 001", "2006.12.22", "G-Dragon", "틴 팝, 댄스, 팝, 힙합",
//                R.drawable.bigbang001
//            ),
//            SampleData(2,"Bigbang 03", "2006.12.22", "G-Dragon", "힙합, 팝", R.drawable.bigbang03),
//            SampleData(1,"Bigbang is V.I.P", "2006.12.22", "G-Dragon", "틴 팝, 힙합",
//                R.drawable.bigbang_is_vip
//            ),
//            SampleData(0,"Bigbang First Single", "2006.12.22", "G-Dragon", "틴 팝, 댄스 팝",
//                R.drawable.first_single
//            )
//        )


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
                //spinner.setSelection(2)
            }

            override fun onItemSwiped(position: Int) {
                sampleAdapter.data.removeAt(position)
                //어댑터에 데이터 삭제 알림
                sampleAdapter.notifyItemRemoved(position)
                //Log.d("delete", "${sampleAdapter.data.size}개")
                //spinner.setSelection(2)
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

        super.onViewCreated(view, savedInstanceState)
    }


    //옵션 메뉴 적용하기
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        var sub: Menu? = menu?.addSubMenu("레이아웃 변경")
        sub?.add(Menu.NONE, Menu.FIRST + 2, Menu.NONE, "Linear")
        sub?.add(Menu.NONE, Menu.FIRST + 3, Menu.NONE, "Grid")
       super.onCreateOptionsMenu(menu, inflater)
    }

    //옵션메뉴 선택할 경우의 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            Menu.FIRST + 2 -> {
                sampleAdapter.changeViewType = 0
                main_rcv.apply {
                    //adapter = sampleAdapter
                    layoutManager = LinearLayoutManager(view?.context)
                }
            }
            Menu.FIRST + 3 -> {
                sampleAdapter.changeViewType = 1
                main_rcv.apply {
                    //adapter = sampleAdapter
                    layoutManager = GridLayoutManager(view?.context, 3)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}