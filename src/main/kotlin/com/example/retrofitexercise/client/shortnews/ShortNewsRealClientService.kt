package com.example.retrofitexercise.client.shortnews

import com.example.retrofitexercise.shortnews.resources.ShortNewsResources

class ShortNewsRealClientService(
    override val shortNewsClient: ShortNewsClient
): ShortNewsClientService {

    override fun getNewsByCategory(category: String): ShortNewsResources.Response {
        return (shortNewsClient as ShortNewsRealClient).getNewsByCategory(category)
            .execute()
            .body()!!
    }
}