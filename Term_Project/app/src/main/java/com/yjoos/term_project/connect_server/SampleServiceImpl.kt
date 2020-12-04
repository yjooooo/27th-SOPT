package com.yjoos.term_project.connect_server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SampleServiceImpl {
    private const val BASE_URL = "http://15.164.83.210:3000"
    private const val DUMMY_URL = "https://reqres.in"
    private const val KAKAO_URL = "https://dapi.kakao.com"

    private val baseRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val baseService: SampleService = baseRetrofit.create(SampleService::class.java)

    private val dummyRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(DUMMY_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val dummyService: SampleService = dummyRetrofit.create(SampleService::class.java)

    private val searchRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(KAKAO_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val searchService: SampleService = searchRetrofit.create(SampleService::class.java)
}