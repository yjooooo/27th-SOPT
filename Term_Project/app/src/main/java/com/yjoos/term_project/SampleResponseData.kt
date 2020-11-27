package com.yjoos.term_project

data class SampleResponseData(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Data
){
    data class Data(
        val email: String,
        val password: String,
        val userName: String
    )
}