package com.yjoos.term_project

data class SampleData(
    val seqNum: Int, // 정렬 순서(가장 최신:0, 가장 옛날:size-1)
    val title: String, //타이틀 정보를 담은 title 변수
    val date: String, //서브타이틀 정보를 담은 subTitle 변수
    val producer:String,
    val genre:String,
    val imageNum: Int //R.drawable.이미지명(이게 int값임)
)