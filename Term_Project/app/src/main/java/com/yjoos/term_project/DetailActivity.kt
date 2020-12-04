package com.yjoos.term_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.yjoos.term_project.rcv.SampleData
import kotlinx.android.synthetic.main.activity_detail.*

//global scope에서 바인딩 어댑터 선언해야됨...
@BindingAdapter("drawableBind")
fun bindImage(view: ImageView, @DrawableRes image: Int) {
    view.setImageResource(image)
}


class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var listData = intent.getParcelableExtra<SampleData>("listData")

        txt_firstName.text = listData.firstName
        txt_email.text = listData.email
        txt_lastName.text = listData.lastName
        Glide.with(this).load(listData.imageSrc).placeholder(R.drawable.loading).error(R.drawable.loading).into(profile_image)
        //genre = albumData.genre
        //imageNum = albumData.imageNum
    }

}