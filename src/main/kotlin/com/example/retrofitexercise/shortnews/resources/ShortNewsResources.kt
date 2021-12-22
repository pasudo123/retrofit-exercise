package com.example.retrofitexercise.shortnews.resources

class ShortNewsResources {

    data class Response(
        val category: String,
        val data: List<DataResponse>,
        val success: String
    )

    data class BasicResponse(
        val message: String
    )

    data class DataResponse(
        val author: String?,
        val content: String?,
        val date: String?,
        val imageUrl: String?,
        val readMoreUrl: String?,
        val time: String?,
        val title: String?,
        val url: String?
    )
}