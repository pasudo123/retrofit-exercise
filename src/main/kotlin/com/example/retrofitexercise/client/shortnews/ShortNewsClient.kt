package com.example.retrofitexercise.client.shortnews

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

    fun getNewsByCategory(category: String): Call<String>
}