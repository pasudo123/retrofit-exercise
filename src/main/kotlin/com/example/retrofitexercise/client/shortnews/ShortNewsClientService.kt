package com.example.retrofitexercise.client.shortnews

import com.example.retrofitexercise.shortnews.resources.ShortNewsResources

interface ShortNewsClientService {

    val shortNewsClient: ShortNewsClient

    fun getNewsByCategory(category: String): ShortNewsResources.Response
}