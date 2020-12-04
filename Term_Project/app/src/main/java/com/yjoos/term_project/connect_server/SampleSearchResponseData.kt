package com.yjoos.term_project.connect_server

data class SampleSearchResponseData(
    val meta: Meta,
    val documents: List<Documents>
){
    data class Meta(
        val total_count: Int,
        val pageable_count: Int,
        val is_end: Boolean
    )

    data class Documents(
        val dateTime: String,
        val contents: String,
        val title: String,
        val url: String
    )
}
