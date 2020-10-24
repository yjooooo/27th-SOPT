package com.yjoos.term_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.yjoos.term_project.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*

//global scope에서 바인딩 어댑터 선언해야됨...
@BindingAdapter("drawableBind")
fun bindImage(view: ImageView, @DrawableRes image: Int) {
    view.setImageResource(image)
}


class DetailActivity : AppCompatActivity() {
    private lateinit var dbinding: ActivityDetailBinding
    var imageNum: Int = R.drawable.loading
    var title: String = "제목"
    var date: String = "발매일"
    var producer: String = "프로듀서"
    var genre: String = "장르"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbinding = ActivityDetailBinding.inflate(LayoutInflater.from(this))

        setContentView(dbinding.root)

        dbinding.sampleData = this
        title = intent.getStringExtra("album")
        date = intent.getStringExtra("date")
        producer = intent.getStringExtra("producer")
        genre = intent.getStringExtra("genre")
        imageNum = intent.getIntExtra("image", R.drawable.loading)
    }

}