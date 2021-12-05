package com.example.retrofitexercise.shortnews.api

import com.example.retrofitexercise.client.shortnews.ShortNewsClient
import com.example.retrofitexercise.client.shortnews.ShortNewsClientService
import com.example.retrofitexercise.shortnews.resources.ShortNewsResources
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("short-news")
class ShortNewsController(
    private val shortNewsClientService: ShortNewsClientService
) {

    @GetMapping
    fun getNews(
        @RequestParam("category") category: ShortNewsClient.Category
    ): ResponseEntity<ShortNewsResources.Response> {
        val response = shortNewsClientService.getNewsByCategory(category.param)
        return ResponseEntity.ok(response)
    }
}