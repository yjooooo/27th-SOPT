package com.yjoos.hw1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeIntent = Intent(this, MainActivity::class.java)
        val idpwShared = getSharedPreferences("idpw", Context.MODE_PRIVATE)
        val idpwEditor = idpwShared.edit()

        logout_btn.setOnClickListener {
            idpwEditor.remove("id")
            idpwEditor.remove("pw")
            idpwEditor.commit()
            startActivity(homeIntent)
            finish()
        }
    }
}