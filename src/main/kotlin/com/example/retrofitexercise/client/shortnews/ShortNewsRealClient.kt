package com.example.retrofitexercise.client.shortnews

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShortNewsRealClient : ShortNewsClient {

    @GET
    override fun getNewsByCategory(@Query("category") category: String): Call<String>
}