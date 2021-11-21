package com.example.retrofitexercise.client.shortnews

import retrofit2.Call

interface ShortNewsDummyClient : ShortNewsClient{

    override fun getNewsByCategory(category: String): Call<String> {
        TODO("")
    }
}