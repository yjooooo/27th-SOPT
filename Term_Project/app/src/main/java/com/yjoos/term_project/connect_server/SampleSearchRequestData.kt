package com.yjoos.term_project.connect_server

data class SampleSearchRequestData(
    val query: String,
    val sort: String,
    val page: Int,
    val size: Int
)
