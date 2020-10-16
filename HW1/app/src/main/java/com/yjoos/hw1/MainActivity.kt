package com.yjoos.hw1

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, SignUpActivity::class.java)
        val homeIntent = Intent(this, HomeActivity::class.java)
        val shared = getSharedPreferences("idpw", Context.MODE_PRIVATE)
        val editor = shared.edit()

        if(shared.getString("id","")!=""
            && shared.getString("pw","")!=""){
            Toast.makeText(this, "자동로그인이 되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(homeIntent)
        }

        join_btn.setOnClickListener {

            //startActivity(intent)
            startActivityForResult(intent, 1)
        }

        login_btn.setOnClickListener {
            editor.putString("id",id_edt.text.toString())
            editor.putString("pw",pw_edt.text.toString())
            editor.apply()
            Toast.makeText(this, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(homeIntent)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode){
                1 -> {
                    id_edt.setText(data!!.getStringExtra("id").toString())
                    pw_edt.setText(data!!.getStringExtra("pw").toString())

                }
            }
        }


    }

}
/*
class MyApplication: Application(){
    companion object{
        lateinit var prefs: MySharedPreferences
    }
}

class MySharedPreferences(context: Context){
    val prefs: SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, value: String): String{
        return prefs.getString(key, value).toString()
    }
    fun setString(key: String, str: String){
        prefs.edit().putString(key, str).apply()
    }
}
*/