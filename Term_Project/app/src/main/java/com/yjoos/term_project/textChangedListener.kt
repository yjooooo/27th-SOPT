package com.yjoos.term_project

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.textChangedListener(textChanged : (CharSequence?) -> Unit){
    this.addTextChangedListener(object: TextWatcher {
        //입력이 끝났을 때
        override fun afterTextChanged(s: Editable?) {
        }
        //입력하기 전에 호출되는 API
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        //EditText에 변화가 있을 때
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textChanged(s)
        }
    })
}