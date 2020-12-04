package com.yjoos.term_project

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yjoos.term_project.connect_server.SampleResponseData
import com.yjoos.term_project.connect_server.SampleServiceImpl
import com.yjoos.term_project.connect_server.SampleSignupRequestData
import kotlinx.android.synthetic.main.activity_sign_up.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signIntent = Intent(this, MainActivity::class.java)



        s_joinBtn.setOnClickListener {
            val id = s_idEdt.text.toString()
            val pw = s_pwEdt.text.toString()
            val userName = s_nameEdt.text.toString()
            if (userName != ""
                && id != ""
                && pw != ""){
                val call: Call<SampleResponseData> = SampleServiceImpl.baseService.postSignup(
                    SampleSignupRequestData(email = id, password = pw, userName = userName)
                )
                call.enqueue(object: Callback<SampleResponseData>{
                    override fun onFailure(call: Call<SampleResponseData>, t: Throwable) {
                        //통신 실패 로직
                    }
                    override fun onResponse(
                        call: Call<SampleResponseData>,
                        response: Response<SampleResponseData>
                    ) {
                        response.takeIf{ it.isSuccessful }
                            ?.body()
                            ?.let{ data ->
                                Toast.makeText(this@SignUpActivity, "반갑습니다,"+userName+"님! 회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                signIntent.putExtra("id", id)
                                signIntent.putExtra("pw", pw)
                                setResult(Activity.RESULT_OK,signIntent)
                                finish()
                            } ?: showError(response.errorBody())

                    }
                    private fun showError(error: ResponseBody?) {
                        val e = error ?: return
                        val ob = JSONObject(e.string())
                        Toast.makeText(this@SignUpActivity, ob.getString("message"),Toast.LENGTH_SHORT).show()
                    }
                })
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