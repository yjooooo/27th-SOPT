package com.yjoos.term_project

//import androidx.lifecycle.MutableLiveData
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yjoos.term_project.connect_server.SampleRequestData
import com.yjoos.term_project.connect_server.SampleResponseData
import com.yjoos.term_project.connect_server.SampleServiceImpl
import com.yjoos.term_project.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            val id = id_edt.text.toString()
            val pw = pw_edt.text.toString()
            if(id_edt.text.toString()!=""
                && pw_edt.text.toString()!=""){
                val call: Call<SampleResponseData> = SampleServiceImpl.baseService.postLogin(
                    SampleRequestData(email = id, password = pw)
                )
                call.enqueue(object: Callback<SampleResponseData> {
                    override fun onFailure(call: Call<SampleResponseData>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<SampleResponseData>,
                        response: Response<SampleResponseData>
                    ) {
                        Log.d("tag", response.body()!!.status.toString())
                        response.takeIf{ it.isSuccessful }
                            ?.body()
                            ?.let{ data ->
                                Log.d("tag", response.body()!!.status.toString())
                                idpwEditor.putString("id",data.data.email)
                                idpwEditor.putString("pw",data.data.password)
                                idpwEditor.commit()
                                Toast.makeText(this@MainActivity, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                startActivity(homeIntent)
                            } ?: showError(response.errorBody())
                    }

                    private fun showError(error: ResponseBody?) {
                        val e = error ?: return
                        val ob = JSONObject(e.string())
                        Toast.makeText(this@MainActivity, ob.getString("message"),Toast.LENGTH_SHORT).show()
                    }
                })
            } else{
                Toast.makeText(this, "아이디와 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            }



//            //서버통신 전
//            if(id_edt.text.toString()!=""
//                    && pw_edt.text.toString()!=""){
//                idpwEditor.putString("id",id_edt.text.toString())
//                idpwEditor.putString("pw",pw_edt.text.toString())
//                idpwEditor.commit()
//                Toast.makeText(this, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()
//                startActivity(homeIntent)
//            }
//            else{
//                Toast.makeText(this, "아이디와 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
//            }
        }

        id_edt.textChangedListener {
            if(it.isNullOrEmpty()){
                id_edt.setBackgroundResource(0)
            }
            else{
                id_edt.setBackgroundResource(R.drawable.login_edit)
            }
        }
        pw_edt.textChangedListener {
            if(it.isNullOrEmpty()){
                pw_edt.setBackgroundResource(0)
            }
            else{
                pw_edt.setBackgroundResource(R.drawable.login_edit)
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
