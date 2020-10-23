package com.yjoos.term_project

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.yjoos.term_project.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root) //binding을 참조하면 안됨. (binding은 뷰를 한단계감싸고 있는 것임.)

        /*
        //데이터 바인딩, 라이브 데이터 사용 시
        binding.lifecycleOwner = this
        val str: MutableLiveData<String> = MutableLiveData("HiHi")
        binding.text = str
        binding.changeBtn.setOnClickListener {
            str.value = "ByeBye"
        }
         */
        val intent = Intent(this, SignUpActivity::class.java)
        val homeIntent = Intent(this, HomeActivity::class.java)

        val idpwShared = getSharedPreferences("idpw", Context.MODE_PRIVATE)
        val idpwEditor = idpwShared.edit()

        if(idpwShared.getString("id","")!=""
                && idpwShared.getString("pw","")!=""){
            Toast.makeText(this, "자동로그인이 되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(homeIntent)
        }

        join_btn.setOnClickListener {
            //startActivity(intent)
            startActivityForResult(intent, 1)
        }

        login_btn.setOnClickListener {
            if(id_edt.text.toString()!=""
                    && pw_edt.text.toString()!=""){
                idpwEditor.putString("id",id_edt.text.toString())
                idpwEditor.putString("pw",pw_edt.text.toString())
                idpwEditor.commit()
                Toast.makeText(this, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                startActivity(homeIntent)
            }
            else{
                Toast.makeText(this, "아이디와 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
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
