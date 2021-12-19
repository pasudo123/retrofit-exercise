package com.example.retrofitexercise.client.shortnews

import com.example.retrofitexercise.shortnews.resources.ShortNewsResources
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShortNewsClient {

    enum class Category(val param: String) {
        ALL("all"),
        BUSINESS("business"),
        SPORTS("sports"),
        WORLD("worlds"),
        TECHNOLOGY("technology"),
        SCIENCE("science")
    }

    @GET("news")
    fun getNewsByCategory(@Query("category") category: String): Call<ShortNewsResources.Response>
}