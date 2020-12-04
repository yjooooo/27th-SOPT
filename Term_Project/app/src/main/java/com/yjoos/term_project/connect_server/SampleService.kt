package com.yjoos.term_project.connect_server

import retrofit2.Call
import retrofit2.http.*

interface SampleService {
    @Headers("Content-Type:application/json")
    @POST("/users/signup")
    fun postSignup(
        @Body body: SampleSignupRequestData
    ) : Call<SampleResponseData>
    @POST("/users/signin")
    fun postLogin(
        @Body body: SampleRequestData
    ) : Call<SampleResponseData>
    @GET("/api/users")
    fun getList(
        @Query("page") page : Int
    ) : Call<SampleRcvListResponseData>

    @Headers("Authorization:KakaoAK 181f5e5c475eb6a7f7a4e535f7e8e783")
    @GET("/v2/search/web")
    fun getSearch(
        @Query("query") query : String,
        @Query("sort") sort : String? = "accuracy",
        @Query("page") page : Int? = 1,
        @Query("size") size : Int? = 8
    ) : Call<SampleSearchResponseData>

}