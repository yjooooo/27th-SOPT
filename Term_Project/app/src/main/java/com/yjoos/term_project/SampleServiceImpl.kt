package com.yjoos.term_project

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SampleServiceImpl {
    private const val BASE_URL = "http://15.164.83.210:3000"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: SampleService = retrofit.create(SampleService::class.java)
}