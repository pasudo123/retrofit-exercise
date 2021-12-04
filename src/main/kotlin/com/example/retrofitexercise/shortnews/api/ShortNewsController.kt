package com.example.retrofitexercise.shortnews.api

import com.example.retrofitexercise.client.shortnews.ShortNewsClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("short-news")
class ShortNewsController(
    private val shortNewsClient: ShortNewsClient
) {

    @GetMapping
    fun getNews(@RequestParam("category") category: ShortNewsClient.Category) {
        shortNewsClient.getNewsByCategory(category.param)
    }
}