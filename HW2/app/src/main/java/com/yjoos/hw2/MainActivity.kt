package com.yjoos.hw2


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var sampleAdapter: SampleAdapter //나중에 초기화할 것.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sampleAdapter=SampleAdapter(this)

        main_rcv.apply{
            adapter = sampleAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        /*
        main_rcv.adapter = sampleAdapter
        main_rcv.layoutManager = LinearLayoutManager(this)
        */
        sampleAdapter.data = mutableListOf(
                SampleData("이름","손연주"),
                SampleData("나이","23"),
                SampleData("학교","서울여자대학교"),
                SampleData("파트","안드로이드"),
                SampleData("GitHub","www.github.com/yjooooo"),
                SampleData("Sopt","www.sopt.org")
        )

        sampleAdapter.notifyDataSetChanged()
    }
}