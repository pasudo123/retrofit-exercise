package com.example.retrofitexercise.client.shortnews

import com.example.retrofitexercise.shortnews.resources.ShortNewsResources
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

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

    /**
     * local 에서 실행된 거를 내려주도록 한다.
     * local -> local
     */
    @GET
    fun getMockNewsByCategory(@Url url: String): Call<ShortNewsResources.Response>

    @GET("result-200")
    fun result200(): Call<ShortNewsResources.BasicResponse>

    @GET("result-200-body-empty")
    fun result200BodyEmpty(): Call<ShortNewsResources.BasicResponse>

    @GET("result-204")
    fun result204(): Call<ShortNewsResources.BasicResponse>

    @GET("result-404")
    fun result404(): Call<ShortNewsResources.BasicResponse>

    @GET("result-500")
    fun result500(): Call<ShortNewsResources.BasicResponse>

    @GET("result-xxx")
    fun resultXXX(): Call<ShortNewsResources.BasicResponse>
}