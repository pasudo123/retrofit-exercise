package com.example.retrofitexercise.client.shortnews

import com.example.retrofitexercise.shortnews.resources.ShortNewsResources
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShortNewsRealClient: ShortNewsClient {

    @GET("news")
    override fun getNewsByCategory(@Query("category") category: String): Call<ShortNewsResources.Response>
}