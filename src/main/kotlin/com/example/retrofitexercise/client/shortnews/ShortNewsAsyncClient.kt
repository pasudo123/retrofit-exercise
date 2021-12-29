package com.example.retrofitexercise.client.shortnews

import com.example.retrofitexercise.shortnews.resources.ShortNewsResources
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ShortNewsAsyncClient {

    /**
     * local 에서 실행된 거를 내려주도록 한다.
     * local -> local
     */
    @GET
    fun getMockNewsByCategory(@Url url: String): Call<ShortNewsResources.Response>
}