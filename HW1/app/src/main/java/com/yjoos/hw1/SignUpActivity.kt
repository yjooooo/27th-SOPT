package com.yjoos.hw1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signIntent = Intent(this, MainActivity::class.java)
        s_joinBtn.setOnClickListener {
            if (s_nameEdt.text.toString() != ""
                && s_idEdt.text.toString() != ""
                && s_pwEdt.text.toString() != ""){
                Toast.makeText(this, "반갑습니다,"+s_nameEdt.text.toString()+"님! 회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                signIntent.putExtra("id", s_idEdt.text.toString())
                signIntent.putExtra("pw", s_pwEdt.text.toString())
                setResult(Activity.RESULT_OK,signIntent)
                finish()
                //startActivityForResult(signIntent, 1)
            }
            else{
                Toast.makeText(this, "빈 칸이 있습니다. 모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}