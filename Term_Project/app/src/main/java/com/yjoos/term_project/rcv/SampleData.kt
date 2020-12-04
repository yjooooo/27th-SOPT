package com.yjoos.term_project.rcv

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SampleData(
    val seqNum: Int, // 정렬 순서(가장 최신:0, 가장 옛날:size-1)
    val firstName: String, //타이틀 정보를 담은 title 변수
    val email: String, //서브타이틀 정보를 담은 subTitle 변수
    val lastName:String,
    //val genre:String,
    val imageSrc: String //R.drawable.이미지명(이게 int값임)
): Parcelable