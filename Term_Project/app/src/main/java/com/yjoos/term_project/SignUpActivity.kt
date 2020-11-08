package com.yjoos.term_project

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
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

        s_nameEdt.textChangedListener {
            if(it.isNullOrEmpty()){
                s_nameEdt.setBackgroundResource(0)
            }
            else{
                s_nameEdt.setBackgroundResource(R.drawable.login_edit)
            }
        }
        s_idEdt.textChangedListener {
            if(it.isNullOrEmpty()){
                s_idEdt.setBackgroundResource(0)
            }
            else{
                s_idEdt.setBackgroundResource(R.drawable.login_edit)
            }
        }
        s_pwEdt.textChangedListener {
            if(it.isNullOrEmpty()){
                s_pwEdt.setBackgroundResource(0)
            }
            else{
                s_pwEdt.setBackgroundResource(R.drawable.login_edit)
            }
        }
        s_pwCheckEdt.textChangedListener {
            if(it.isNullOrEmpty()){
                s_pwCheckEdt.setBackgroundResource(0)
                text_pwCheck.visibility = View.INVISIBLE
            }
            else{
                s_pwCheckEdt.setBackgroundResource(R.drawable.login_edit)
                if(s_pwEdt.text.toString() != s_pwCheckEdt.text.toString()){
                    text_pwCheck.visibility = View.VISIBLE
                }
                else{
                    text_pwCheck.visibility = View.INVISIBLE
                }
            }
        }
    }
}