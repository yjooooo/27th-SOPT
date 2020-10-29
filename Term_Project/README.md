

# 🚩2nd Week

1. 과제
2. RecyclerView  Item 클릭 이벤트
3. LinearLayout 🔄 GridLayout
4. RecyclerView Item 이동 ▶ Drag & Drop
5. RecyclerView Item 삭제 ▶ Swipe to Dismiss
6. Spinner
7. Options Menu
8. RecyclerView Item background



### 📋과제

1. 로그인 버튼 클릭 👉 RecyclerView화면
   각 아이템 클릭 👉 해당 아이템의 상세화면
2. GridLayout 만들기
3. RecyclerView Item 이동, 삭제 구현
4. ➕ Item 각각의 이미지 적용
5. ➕ Spinner 적용
6. ➕ Options Menu 적용
7. ➕  RecyclerView Item background 설정



### 🍩RecyclerView Item 클릭 이벤트

- RecyclerView Item 클릭 👉 해당 아이템의 상세화면(DetailActivity.kt)

  - 뷰홀더 파일의 onBind함수
    ▶ 뷰의 요소들에 실질적으로 데이터를 넣어주는 함수
    ▶ Adapter에서 해당 함수를 호출할 예정
    ▶ 이 함수에서 itemView에 클릭리스너를 적용하기 
    ▶ 어댑터에서 전달받은 아이템의 데이터를 layout에 Bind시켜줄 때 클릭리스너도 함께 적용한 것.

    ```kotlin
    fun onBind(data: SampleData){
        //뷰의 요소들에 실질적으로 데이터를 넣어주는 함수, Adapter에서 해당 함수를 호출할 예정.
        title.text=data.title
        date.text=data.date
        imageNum.setImageResource(data.imageNum)
        itemView.setOnClickListener{
            var detailIntent= Intent(itemView.context, DetailActivity::class.java)
    
            //클릭한 아이템에 대한 정보를 intent에 담아서 DetailActivity에 보내기
            detailIntent.putExtra("album", data.title)
            detailIntent.putExtra("date", data.date)
            detailIntent.putExtra("producer", data.producer)
            detailIntent.putExtra("genre", data.genre)
            detailIntent.putExtra("image", data.imageNum)
            itemView.context.startActivity(detailIntent)
        }
    }
    ```



### 🍩LinearLayout 🔄 GridLayout

- RecyclerView 자체 레이아웃만 LinearLayout과 GridLayout으로 왔다갔다할 때는<br> layoutManager에 LinearLayoutManager와 GridLayoutManager를 번갈아 설정해주면 OK.

- RecyclerView 자체 레이아웃이 <br><LinearLayout일 때의 아이템 레이아웃>과 <GridLayout일 때의 아이템 레이아웃>도 다르게 해주고 싶다면 RecyclerView 어댑터의 onCreateViewHolder함수에서 viewType을 다르게 받아서 설정 가능!

  - SampleAdapter.kt

    ```kotlin
    var changeViewType = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        var view =
        when (viewType){
            1->{
                LayoutInflater.from(context).inflate(R.layout.profile_item_grid,
                    parent, false)
            }
            else ->{
                LayoutInflater.from(context).inflate(R.layout.profile_item_list,
                    parent, false)
            }
        }
    
        return SampleViewHolder(view,itemDragListener)
    }
    
    override fun getItemViewType(position: Int): Int {
        return changeViewType
    }
    ```

  - HomeActivity.kt ▶ GridLayout설정

    ```kotlin
    sampleAdapter.changeViewType = 1
    main_rcv.apply {
        //adapter = sampleAdapter
        layoutManager = GridLayoutManager(this@HomeActivity, 3)
    }
    ```

  - HomeActivity.kt ▶ LinearLayout 설정

    ```kotlin
    sampleAdapter.changeViewType = 0
    main_rcv.apply {
        //adapter = sampleAdapter
        layoutManager = LinearLayoutManager(this@HomeActivity)
    }
    ```



### 🍩RecyclerView Item 클릭 이벤트





# 🚩1st Week

1. 과제

2. ConstraintLayout

3. Intent를 사용한 액티비티 간 데이터 전달

4. startActivityForResult()

5. SharedPreferences()

   

### 📋과제

1. SignUpActivity 생성해서 회원가입 화면 구현하기

2. 회원가입 성공 후, 로그인 화면으로 돌아오면 아이디와 비밀번호가 입력되어 있도록 구현하기<br>
   SignUpActivity에서 MainActivity로 돌아올 때 아이디와 비밀번호 넘겨주기 <br>
   ▶ StartActivityForResult()사용

3. 로그인 성공한다면 재실행할 때 자동로그인 구현하기<br>
   ▶ SharedPreferences()사용

   

### 🍭ConstraintLayout

- 상대적인 제약을 통해 View/ViewGroup을 배치한다.

- Widget의 가로/세로 모두 제약이 걸려야 한다!

  

### 🍭Intent를 사용한 액티비티 간 데이터 전달

- **Intent란?**

  : 애플리케이션 구성요소 간에 작업 수행을 위한 정보 전달 역할을 한다.

  지금처럼 액티비티 간에 데이터를 주고 받으려는  상황에서도 사용할 수 있다.

- 데이터 송신 ▶ putExtra()

  ```kotlin
  signIntent.putExtra("id", s_idEdt.text.toString())
  signIntent.putExtra("pw", s_pwEdt.text.toString())
  ```

- 데이터 수신 ▶ getExtra()

  ```kotlin
  id_edt.setText(data!!.getStringExtra("id").toString())
  pw_edt.setText(data!!.getStringExtra("pw").toString())
  ```

  

### 🍭strartActivityForResult()

- startActivity()와 startActivityForResult() 모두 화면 전환에 사용되는데 차이점은?

  - startActivity() : 자식 액티비티에서의 결과를 부모 액티비티에서 처리하지 않아도 될 때 사용.
  - startActivityForResult() : 자식 액티비티에서의 결과를 부모 액티비티에서 처리할 때 사용.

- startActivityForResult()를 사용하려면 Request Code와 Result Code가 필요하다. 

  - Request Code : 각 액티비티에 부여해준 값.<br>
    ▶ 각 액티비티마다 다르게 지정 가능. 이 값에 따라 처리할 것을 달리할 수 있음.
  - Result Code : 액티비티가 정상적으로 왔는지 확인해주는 값.<br>
    ▶ 자식 액티비티에서 setResult()를 통해 값 지정 가능.
  - data : 각 액티비티에서 받아온 intent들.

- 부모 액티비티(MainActivity)

  - 자식 액티비티가 종료될 때 데이터를 받아와 사용할 것이므로 startActivityForResult()를 통해 화면전환.
  - requestCode값에 따라 어떤 일을 수행할지 onActivityResult 함수를 오버라이드를 통해 코드 작성.

  ```kotlin
  join_btn.setOnClickListener {
      startActivityForResult(intent, 1)
  }
  ```

  ```kotlin
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
  ```

- 자식 액티비티(SignUpActivity)

  - 부모액티비티로 넘겨줄 데이터를 intent에 담기.
  - 액티비티 종료 후 부모 액티비티로 돌아가기 위해 finish()실행.

  ```kotlin
  signIntent.putExtra("id", s_idEdt.text.toString())
  signIntent.putExtra("pw", s_pwEdt.text.toString())
  setResult(Activity.RESULT_OK,signIntent)
  finish()
  ```



### 🍭SharedPreferences()

- DB에 저장하기에는 애매한 간단한 설정 값이나 문자열 같은 데이터를 SharedPreferences를 통해 관리할 수 있다.

- 데이터를 파일로 저장하며, 파일이 앱 폴더 내에 저장되므로 앱이 삭제되면 데이터도 함께 삭제된다.

- getSharedPreferences()호출을 통해 객체 생성

  ```kotlin
  val idpwShared = getSharedPreferences("idpw", Context.MODE_PRIVATE)
  val idpwEditor = idpwShared.edit()
  ```

- 데이터 추가(주의사항 : commit()을 해줘야 적용됨!)
  ▶ key와 value형태로 저장

  ```kotlin
  idpwEditor.putString("id",id_edt.text.toString())
  idpwEditor.putString("pw",pw_edt.text.toString())
  idpwEditor.commit()
  ```

- 데이터 삭제(주의사항 : commit()을 해줘야 적용됨!)
  ▶ key를 통해 삭제

  ```kotlin
  idpwEditor.remove("id")
  idpwEditor.remove("pw")
  idpwEditor.commit()
  ```

- ➕주의사항➕<br>
  Editor.put()을 했다고 데이터가 바로 저장된 것은 No!!<br>
  Editor.commit() 또는 Editor.apply()를 호출해줘야 한다.<br>
  Editor.commit()은 동기적으로 동작하기 때문에 처리 중인 쓰레드가 blocking 될 수 있기 때문에 저장될 때까지 기다릴 필요가 없다면 비동기적으로 동작하는 Editor.apply()를 사용하는 것이 좋다고 한다.