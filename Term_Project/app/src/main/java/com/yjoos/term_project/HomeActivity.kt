package com.yjoos.term_project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.properties.Delegates

class HomeActivity : AppCompatActivity() {
    private lateinit var viewPagerAdapter: SampleViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewPagerAdapter = SampleViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.fragments = listOf(
            ProfileFragment(),
            MusicAlbumFragment(),
            ThirdFragment()
        )
        //sample_viewpager = xml에서 뷰페이저 아이디
        sample_viewpager.adapter = viewPagerAdapter

        sample_viewpager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
            //뷰페이저의 페이지 중 하나가 선택된 경우
            override fun onPageSelected(position: Int) {
                sample_bottom_navi.menu.getItem(position).isChecked = true
            }
        })


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


    }
    //옵션 메뉴 적용하기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(Menu.NONE, Menu.FIRST+1, Menu.NONE, "로그아웃")
        return true
    }

    //옵션메뉴 선택할 경우의 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            Menu.FIRST + 1 -> {
                val homeIntent = Intent(this, MainActivity::class.java)
                val idpwShared = getSharedPreferences("idpw", Context.MODE_PRIVATE)
                val idpwEditor = idpwShared.edit()
                idpwEditor.remove("id")
                idpwEditor.remove("pw")
                idpwEditor.commit()
                startActivity(homeIntent)
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }
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
}