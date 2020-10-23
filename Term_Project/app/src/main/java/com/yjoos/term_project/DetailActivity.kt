package com.yjoos.term_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        album_text.text = intent.getStringExtra("album")
        date_text.text = intent.getStringExtra("text")
        producer_text.text = intent.getStringExtra("producer")
        genre_text.text = intent.getStringExtra("genre")
        album_image.setImageResource(intent.getIntExtra("image",R.drawable.loading))
    }
}