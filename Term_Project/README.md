# 🚩3rd Week

1. 실행 영상
2. 과제
3. Fragment에서 onCreateView와 onViewCreated의 차이점
4. Fragment + ViewPager
5. BottomNavigation
6. TabLayout
7. ➕로그인 후, 뒤로가기 2번 누르면 앱 종료되게 구현



### 🎥실행영상

<div>
    <img width="200" src="https://user-images.githubusercontent.com/68374234/98128515-5e246480-1efb-11eb-96a4-162462fe9ac2.gif">
    <img width="200" src="https://user-images.githubusercontent.com/68374234/98128499-5b297400-1efb-11eb-848d-1312be431b6d.gif">
</div>





### 📋과제

1. 하단탭 + 뷰페이저로 전체적인 화면 구성하기

   - 로그인 버튼 클릭 👉 <프로필화면 / 리사이클러뷰 화면 / 비어있는 화면> 3개의 프래그먼트 화면

   - 프로필 화면 내 👉 <Info화면 / Other화면> 2개의 프래그먼트 화면 포함



### 🍒Fragment에서 onCreateView와 onViewCreated

- Fragment는 기존에 1개의 Activity 위에 여러개의 view를 지정하여 액티비티를 꾸렸다면<br>Activity위에 Fragment를 올리고 Fragment위에 뷰를 올릴 수 있게 해준다.
- onCrate : Fragment가 생성될 때 호출되는 부분
- onCreateView : onCreate 후에 화면을 구성할 때 호출되는 부분 
  - Fragment에서는 onCreate가 아닌 onCreateView부분에서 Activity의 onCreate에서 사용한 코드를 적으면 된다고 생각하면 됨.
- onViewCreated : onCreateview에서 뷰를 생성하고 뷰가 만들어지면 onViewCreated가 콜백된다고 한다.
  -  onViewCreated는 onCreateView에서 return해준 view를 갖고 있다.
- 레이아웃을 inflate(setContentView())하고, root뷰에 대한 작업은  👉  onCreateView
-  Fragment의 모든 초기화 코드는 👉  onViewCreated



### 🍒Fragment + ViewPager

- 하나의 화면에서 여러개의 전체화면을 보이게 하기 위해 Fragment + ViewPager 조합을 사용한다!

- 전체화면에 3개의 Fragment 필요! 👉 하단 탭(BottomNavigation 사용)으로도 페이지 이동 가능하게 할 것!

  1. 프로필화면 (ProfileFragment)에 2개의 Fragment 필요! 👉 중앙 탭(TabLayout 사용)으로도 페이지 이동 가능하게 할 것!
     1. Info화면(ChildInfoFragment)
     2. Other화면(ChildOtherFragment)
  2. 리사이클러뷰 화면 (MusicAlbumFragment)
  3. 빈 화면 (ThirdFragment)

- 전체화면을 띄우는 액티비티 & 프로필화면 에 각각 ViewPager필요하다.

  - Android에서는 데이터 리스트를 아이템 단위의 뷰 또는 뷰 집합으로 표시할 때, Adapter를 사용한다.
  - ViewPager의 경우, PagerAdapter를 사용하여 각 페이지를 위한 뷰를 생성한다.<br> 2개의 ViewPager에 적용할 2개의 PagerAdapter필요하다!
    - ViewPagerAdapter는 2가지 메소드 반드시 오버라이드 해야한다!
      - getItem메소드, getCount메소드

- ViewPager 사용방법

  1. ViewPager사용할 액티비티 레이아웃에 ViewPager추가하기
  2. ViewPager에서 페이지로 표시될 레이아웃 리소스 xml 작성하기 <br>(이번 과제는 ViewPager에 레이아웃 리소스  xml 대신 Fragment를 넣을 것!)
  3. PagerAdapter 상속 및 구현하기
  4. ViewPager에 PageAdapter 저장하기

- 전체화면(HomeActivity)

  - 전체화면에서 사용할 ViewPager에 적용할 PageAdapter (SampleViewPagerAdapter.kt)

    - getItem 메소드 : ViewPager의 각 poisition에서 보여줄 프래그먼트를 ViewPager에서 사용할 3개의 Fragment를 설정한다.
    - getCount 메소드 : 사용가능한 뷰의 개수를 return해준다.

    ```kotlin
    class SampleViewPagerAdapter(fm: FragmentManager):
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    
        override fun getItem(position: Int): Fragment = when(position){
            0 -> ProfileFragment()
            1 -> MusicAlbumFragment()
            2 -> ThirdFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
        
        override fun getCount(): Int = 3
    }
    ```
    
  - ViewPager에 SampleViewPagerAdapter 저장하기

    ```kotlin
    class HomeActivity : AppCompatActivity() {
        private lateinit var viewPagerAdapter: SampleViewPagerAdapter //Adapter 선언
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)
            
            viewPagerAdapter = SampleViewPagerAdapter(supportFragmentManager)
            sample_viewpager.adapter = viewPagerAdapter
            //sample_viewpager는 xml에서 뷰페이저 아이디임.
            ...
        }
        ...
    }
    ```

- 프로필화면에서 구현하는 것도 같은 방법!!



### 🍒BottomNavigation

- 전체화면에서 하단 탭(BottomNavigation 사용)으로도 페이지 이동 가능하게 할 것!

- BottomNavigation

  - 앱에서 하단 탭을 만들 때 사용한다.
  - 위에서 구현한 ViewPager와 연동하여 서브화면들을 전환할 것이다.

- BottomNavigation 사용방법

  - menu이름으로 res위치에 directory를 생성한다.

  - menu폴더 안에 xml을 추가한다. (bottom_menu.xml)

    - 하단 탭으로 보여줄 아이콘 개수 만큼 item태그를 생성한다.

    - 액티비티에서 각 item의 id값으로 접근할 것이므로 각 item의 id값을 설정한다.

    - icon 속성은 탭의 아이콘 이미지로 만들어둔 Vector Asset 파일을 넣어준다.

    - title 속성은 탭의 아이템 이름으로 화면상에서 보여지는 각 탭의 이름이다.

      ```kotlin
      <menu xmlns:android="http://schemas.android.com/apk/res/android">
          <item
              android:id="@+id/menu_profile"
              android:icon="@drawable/ic_profile"
              android:checked="false"
              android:title="Profile"/>
          <item
              android:id="@+id/menu_album"
              android:icon="@drawable/ic_album_list"
              android:checked="false"
              android:title="Album"/>
          <item
              android:id="@+id/menu_settings"
              android:icon="@drawable/ic_settings"
              android:checked="false"
              android:title="Setting"/>
      </menu>
      ```

  - 이때 하단 탭의 아이콘 색상(선택 되었을 때, 선택되지 않았을 때)을 변경하고 싶다면

    - color이름으로 res위치에 directory를 생성한다. (botom_navi.color.xml)

    - color폴더 안에 selector태그를 단 xml을 추가하고

    - item태그 안에서 state_checked 속성이 true/false값을 가질 때의 색상을 color 속성에 설정해준다.

      ```kotlin
      <selector xmlns:android="http://schemas.android.com/apk/res/android">
          <item
              android:color="#fff000"
              android:state_checked="true"/>
          <item
              android:color="#9e9e9e"
              android:state_checked="false"/>
      </selector>
      ```

  - 하단 탭을 사용할 액티비티 xml에 BottomNavigationView를 추가한다.

    - 이때, itemIconTint 속성과 itemTextColor 속성에 만들어둔 color폴더의 bottom_navi_color.xml파일을 넣어준다.

    - menu 속성에는 menu폴더의 bottom_menu.xml파일을 넣어준다.

    - itemIconTint : 탭의 아이콘 색상, itemTextColor : 탭의 아이템 이름 색상, itemRippleColor : 탭 선택시 퍼져나가는 물결효과의 색상

      ```kotlin
      <com.google.android.material.bottomnavigation.BottomNavigationView
      	...
      	app:itemRippleColor="#96c3ba"
      	app:itemIconTint="@color/bottom_navi_color"
          app:itemTextColor="@color/bottom_navi_color"
          app:menu="@menu/bottom_menu"
      	...>
      </com.google.android.material.bottomnavigation.BottomNavigationView>
      ```

  - 하단 탭을 사용할 액티비티.kt 파일에서 BottomNavigation을 세팅한다.

    - 탭의 각 아이템을 선택했을 때 👉 ViewPager에서 각 아이템에 해당하는 페이지로 변경

      - 각 탭을 클릭했을 때의 이벤트 처리 Listener를 설정한다.

      - bottom_menu.xml의 아이템 id값을 통해 ViewPager의 currentItem을 조작한다.

        ```kotlin
        class HomeActivity : AppCompatActivity() {
            private lateinit var viewPagerAdapter: SampleViewPagerAdapter
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_home)
        		...
                //바텀 네비게이션 세팅
                sample_bottom_navi.setOnNavigationItemSelectedListener {
                    var index by Delegates.notNull<Int>()
                    when(it.itemId){
                        R.id.menu_profile -> index = 0
                        R.id.menu_album -> index = 1
                        R.id.menu_settings -> index = 2
                    }
                    sample_viewpager.currentItem = index
                    true
                }
                ...
            }
            ...
        }
        ```

  - 탭의 각 아이템을 선택했을 때 👉 ViewPager에서 각 아이템에 해당하는 페이지로 변경된 것 처럼<br>ViewPager에서 슬라이드해서 화면변경됨 👉 그 페이지에 해당하는 탭의 아이템이 선택되도록 해야한다.

    - ViewPager에 페이지 변경에 관한 Listener가 필요하다.

      ```kotlin
      class HomeActivity : AppCompatActivity() {
          private lateinit var viewPagerAdapter: SampleViewPagerAdapter
          override fun onCreate(savedInstanceState: Bundle?) {
              super.onCreate(savedInstanceState)
              setContentView(R.layout.activity_home)
              ...
              //뷰페이저 슬라이드 -> 바텀네비 변경
              sample_viewpager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
                  override fun onPageScrollStateChanged(state: Int) {}
                  override fun onPageScrolled(
                      position: Int,
                      positionOffset: Float,
                      positionOffsetPixels: Int
                  ) {}
                  //뷰페이저의 페이지 중 하나가 선택된 경우 그에 대응되는 하단 탭의 상태 변경
                  override fun onPageSelected(position: Int) {
                      sample_bottom_navi.menu.getItem(position).isChecked = true
                  }
              })
              ...
          }
          ...
      }
      ```



### 🍒TabLayout

- 프로필화면(ProfileFragment)에서 중앙 탭(TabLayout 사용)으로도 페이지 이동 가능하게 할 것!

- TabLayout

  - 상단 탭을 만들 때 주로 사용한다.
  - BottomNavigationView에 비해 위치 이동이 자유롭다.

- TabLayout 사용방법

  - TabLayout사용할 액티비티 레이아웃에 TabLayout 추가하기

  - 액티비티에서 탭 레이아웃에 ViewPager를 연동하기

  - getTabAt(index)?.text = "" 사용하여 각 인덱스와 일치하는 탭 아이템 title 작성하기

    - 이것은 반드시!! 연동 후에 작성해줘야 한다고 한다.

    ```kotlin
    sample_tab.setupWithViewPager(profile_viewpager)
    sample_tab.apply{
        getTabAt(0)?.text = "INFO"
        getTabAt(1)?.text = "OTHER"
    }
    ```

  - 

    - 이것은 반드시!! 연동 후에 작성해줘야 한다고 한다.



### 🍒로그인 후, 뒤로가기 두번  👉  앱 종료

- 뒤로가기 버튼을 눌렀을 때 호출되는 onBackPressed()메소드를 오버라이드 해준다.

- System.currentTimeMillis()의 return형은 long값이며, 1/1000초의 값을 return한다.

- 처음 back버튼을 누르면 Toast로 한번 더 누르면 종료된다는 메시지 보여주고, <br>2초 안에 한번 더 back버튼을 누르면 종료되도록 한다.

  ```kotlin
  var time3: Long = 0
  override fun onBackPressed() {
      val time1 = System.currentTimeMillis()
      val time2 = time1 - time3
      if (time2 in 0..2000) {
          ActivityCompat.finishAffinity(this) //해당 앱의 루트 액티비티를 종료시킨다.
  
          System.runFinalization() //현재 작업중인 쓰레드가 다 종료되면, 종료 시키라는 명령어이다.
  
          System.exit(0) // 현재 액티비티를 종료시킨다.
      } else {
          time3 = time1
          Toast.makeText(applicationContext, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
      }
  }
  ```





# 🚩2nd Week

1. 실행 영상
2. 과제
3. RecyclerView  Item 클릭 이벤트
4. LinearLayout 🔄 GridLayout
5. RecyclerView Item 이동, 삭제 ▶ Drag & Drop, Swipe to Dismiss



### 🎥실행영상

<div>
    <img width="200" src="https://user-images.githubusercontent.com/68374234/97593895-77359d00-1a45-11eb-8ac5-c7f98b9eadaf.gif">
    <img width="200" src="https://user-images.githubusercontent.com/68374234/97593918-7bfa5100-1a45-11eb-9a13-0065aa255485.gif">
    <img width="200" src="https://user-images.githubusercontent.com/68374234/97593930-7e5cab00-1a45-11eb-9129-1cfabae01d98.gif">
</div>



### 📋과제

1. 로그인 버튼 클릭 👉 RecyclerView화면
   각 아이템 클릭 👉 해당 아이템의 상세화면
2. GridLayout 만들기
3. RecyclerView Item 이동, 삭제 구현
4. ➕ Item 각각의 이미지 적용
5. ➕ Spinner 적용 (앨범 발매일순, 최신순, 사용자변경)
6. ➕ Options Menu 적용 (로그아웃, 레이아웃변경-리니어, 그리드)
7. ➕  RecyclerView Item background 설정



### 🍩RecyclerView Item 클릭 이벤트

- RecyclerView Item 클릭 👉 해당 아이템의 상세화면(DetailActivity.kt)

  - 뷰홀더 파일의 onBind함수
    ▶ 뷰의 요소들에 실질적으로 데이터를 넣어주는 함수
    ▶ Adapter에서 해당 함수를 호출할 예정
    ▶ 이 함수에서 itemView에 클릭리스너를 적용
    ▶ 어댑터에서 전달받은 아이템의 데이터를 layout에 Bind시켜줄 때 클릭리스너도 함께 적용한 것이다.

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



### 🍩RecyclerView Item 이동 ▶ Drag & Drop

- ItemTouchHelper 

  - RecyclerView.ItemDecoration의 서브 클래스
  - RecyclerView 및 Callback 클래스와 함께 작동한다.
  - 사용자가 액션을 수행할 때 이벤트를 수신한다.
  - 여기서의 메소드를 재정의해서 사용할 것

- ItemTouchHelper.Callback

  - 추상클래스로 추상메소드 getMovementFlags(), onMove(), onSwiped()를 필수로 재정의 해야한다.
  - 이것을 이용하거나 Wrapper클래스인 ItemTouchHelper.SimpleCallback 이용해도 된다.

- SampleAdapter.kt

  - ItemDragaListener ▶ 사용자가 Drag 액션을 시작할 때 itemTouchHelper에 이벤트를 전달한다.

    ```kotlin
    //아이템 드래그 인터페이스 정의
    interface ItemDragListener{
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }
    //드래그리스너 선언
    private lateinit var itemDragListener: ItemDragListener
    //드래그리스너 등록 메소드
    fun setItemDragListener(itemDragListener: ItemDragListener){
        this.itemDragListener = itemDragListener
    }
    ```

  - ItemActionListener ▶ 아이템이 Drag&Drop됐거나 Swiped됐을 때 어댑터에 이벤트를 전달한다.

    ```kotlin
    //아이템 액션 인터페이스 정의
    interface ItemActionListener{
        fun onItemMoved(from: Int, to: Int)
        fun onItemSwiped(position: Int)
    }
    //액션리스너 선언
    private lateinit var itemActionListener: ItemActionListener
    //액션리스너 등록 메소드
    fun setItemActionListener(itemActionListener: ItemActionListener){
        this.itemActionListener = itemActionListener
    }
    fun getItemActionListener(): ItemActionListener{
        return itemActionListener
    }
    ```

- HomeActivity.kt

  - 어댑터에서 만들었던 액션 리스너의 함수를 재정의한다.

  - 어댑터가 아이템 변경 사항을 인식할 수 있도록 notifyItemMoved(), notifyItemRemoved()를 호출해야 한다.

    ```kotlin
    //아이템 액션 리스너
    sampleAdapter.setItemActionListener(object : SampleAdapter.ItemActionListener {
        override fun onItemMoved(from: Int, to: Int) {
            if (from == to) {
                return
            }
    
            //이동할 객체를 원래 위치에서 삭제하고 fromItem에 임시로 저장
            val fromItem = sampleAdapter.data.removeAt(from)
            //이동하고 싶은 position에 추가
            sampleAdapter.data.add(to, fromItem)
            //어댑터에 데이터 이동 알림
            sampleAdapter.notifyItemMoved(from, to)
            spinner.setSelection(2)
        }
    
        override fun onItemSwiped(position: Int) {
            sampleAdapter.data.removeAt(position)
            //어댑터에 데이터 삭제 알림
            sampleAdapter.notifyItemRemoved(position)
            //Log.d("delete", "${sampleAdapter.data.size}개")
            spinner.setSelection(2)
        }
    
    })
    ```

- SampleViewHolder.kt

  - 어댑터 생성자의 파라미터로 받은 ItemDragListener는 뷰홀더에서 사용된다.<br>
    아이템을 길게 누르면 아이템 이동이 되도록 구현하고자 한다.<br>
    ▶ 아이템뷰에 setOnLongClickListener를 달아준다.  그 안에서 listener.onStartDrag() 호출!

    ```kotlin
    init {
        //itemView.root 에서 root(xml아이디)이름 같게 하기
        //why? => profile_item_grid.xml과  profile_item_list.xml 두개의 레이아웃이 함께 띄워지는게 아니라 번갈아 띄워지는거라서
        //        아이디 다르게 해서 setOnLongClickListener를 각각 적용하면 띄워지지 않은 레이아웃을 가리켜서 null값때문에 오류남!!
        itemView.setOnLongClickListener { v->
            listener.onStartDrag(this)
            true
        }
    ```

- ItemTouchHelperCallback.kt

  - ItemTouchHelper.Callback을 상속받는 ItemTouchHelperCallback 클래스를 구현하고, 생성자의 파라미터로 ItemActionListener를 받는다.

  - getMovementFlags() 재정의 ▶ Drag, Swipe 이벤트의 방향을 지정한다.

  - 아이템이 Drag되면 ItemTouchHelper는 onMove()를 호출한다.<br>
    ▶ 여기서 ItemActionListener로 어댑터에 fromPosition과 toPosition을 파라미터와 함게 콜백을 전달한다.

  - 아이템이 Swipe되면 ItemTouchHelper는 범위를 벗어날 때까지 애니메이션을 적용 후, onSwiped()를 호출한다.<br>
    ▶ 여기서 ItemActionListener로 어댑터에 제거할 아이템의 position을 파라미터와 함게 콜백을 전달한다.

    ```
    package com.yjoos.term_project
    
    import androidx.recyclerview.widget.ItemTouchHelper
    import androidx.recyclerview.widget.RecyclerView
    
    class ItemTouchHelperCallback(val listener:SampleAdapter.ItemActionListener): ItemTouchHelper.Callback(){
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags = ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.START or ItemTouchHelper.END //위아래로 드래그
            val swipeFlags = ItemTouchHelper.START //좌로 스와이프
            return makeMovementFlags(dragFlags, swipeFlags)
        }
    
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            listener.onItemMoved(viewHolder!!.adapterPosition, target!!.adapterPosition)
            return true
    
        }
    
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            listener.onItemSwiped(viewHolder!!.adapterPosition)
        }
    
    }
    ```

- HomeActivity.kt

  - 액티비티에서는 어댑터에서 만들었던 ItemDragListener 인터페이스의 onStartDrag함수를 재정의한다.

  - 뷰홀더에서 onStartDrag() 이벤트를 보내면 ItemTouchHelper.startDrag()메소드를 호출해서 파라미터로 전달된 뷰홀더의 Drag를 시작한다.

  - onCreate()에서 ItemTouchHelperCallback을 파라미터로 하는 ItemTouchHelper를 생성하고 RecyclerView에 붙여준다.

    ```kotlin
    //아이템 드래그 리스너
    sampleAdapter.setItemDragListener(object : SampleAdapter.ItemDragListener {
        override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
            itemTouchHelper.startDrag(viewHolder)
    
        }
    })
    
    itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(sampleAdapter.getItemActionListener()))
    itemTouchHelper.attachToRecyclerView(main_rcv)
    ```

  



# 🚩1st Week

1. 실행영상
2. 과제
3. ConstraintLayout
4. Intent를 사용한 액티비티 간 데이터 전달
5. startActivityForResult()
6. SharedPreferences()




### 🎥실행영상

<img width="200" src="https://user-images.githubusercontent.com/68374234/97591920-7439ad00-1a43-11eb-87b4-476822235e88.gif">



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