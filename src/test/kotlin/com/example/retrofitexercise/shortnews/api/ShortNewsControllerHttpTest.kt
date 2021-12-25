package com.example.retrofitexercise.shortnews.api

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class ShortNewsControllerHttpTest {

    @Test
    @DisplayName("동기방식 http 호출한다.")
    fun syncHttpCallTest() {

        repeat(30) {
//            get("http://localhost:48080/short-news/sync").text
        }
    }

    @Test
    @DisplayName("비동기방식 http 호출한다.")
    fun asyncHttpCallTest() {

        repeat(30) {
//            get("http://localhost:48080/short-news/async").text
        }
    }
}