package com.example.retrofitexercise.client.shortnews

import com.example.retrofitexercise.shortnews.resources.ShortNewsResources
import retrofit2.Call

interface ShortNewsClient {

    enum class Category(val param: String) {
        ALL("all"),
        BUSINESS("business"),
        SPORTS("sports"),
        WORLD("worlds"),
        TECHNOLOGY("technology"),
        SCIENCE("science")
    }

    fun getNewsByCategory(category: String): Call<ShortNewsResources.Response>
}