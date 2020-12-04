package com.yjoos.term_project.viewpager_family

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.yjoos.term_project.R
import com.yjoos.term_project.connect_server.SampleSearchResponseData
import com.yjoos.term_project.connect_server.SampleServiceImpl
import com.yjoos.term_project.rcv.SampleItemDeco
import com.yjoos.term_project.rcv.SearchAdapter
import com.yjoos.term_project.rcv.SearchData
import kotlinx.android.synthetic.main.fragment_search.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdFragment : Fragment() {

    private lateinit var searchAdapter: SearchAdapter
    private var rcvSearchData = mutableListOf<SearchData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_search.setOnClickListener {
            val call: Call<SampleSearchResponseData> = SampleServiceImpl.searchService.getSearch(
                query = edt_search.text.toString(), sort = "accuracy", page = 1, size = 8)
            call.enqueue(object: Callback<SampleSearchResponseData> {
                override fun onFailure(call: Call<SampleSearchResponseData>, t: Throwable) {
                    //통신 실패 로직
                }

                override fun onResponse(
                    call: Call<SampleSearchResponseData>,
                    response: Response<SampleSearchResponseData>
                ) {
                    response.takeIf{ it.isSuccessful }
                        ?.body()
                        ?.let{ data ->
                            for(i in 0 until data.documents.size){
                                //rcvSearchData.add(SearchData(data.documents[i].title, data.documents[i].contents,data.documents[i].url, data.documents[i].dateTime))
                                rcvSearchData.add(SearchData(
                                    removeHtmlTag(data.documents[i].title),
                                    removeHtmlTag(data.documents[i].contents),
                                    data.documents[i].url
                                    /*data.documents[i].dateTime.slice(IntRange(0,9)) + " " + data.documents[i].dateTime.slice(IntRange(11,15))*/

                                    ))
                            }
                            searchAdapter.data = rcvSearchData
                            //Adapter에 데이터가 갱신되었다고 알려주기!
                            searchAdapter.notifyDataSetChanged()
                        } ?: showError(response.errorBody())

                }

                private fun showError(error: ResponseBody?) {
                    val e = error ?: return
                    val ob = JSONObject(e.string())
                    Toast.makeText(view.context, ob.getString("message"), Toast.LENGTH_SHORT).show()
                }

                fun removeHtmlTag(html : String) : String {
                    return Html.fromHtml(html).toString()
                }
            })
        }

        searchAdapter = SearchAdapter(view.context)

        val spaceDecoration = SampleItemDeco(20) //리사이클러뷰 아이템간 간격 설정
        rcv_search.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(view.context)
            addItemDecoration(spaceDecoration) //간격 설정한거 데코로 설정
        }

        super.onViewCreated(view, savedInstanceState)
    }
}