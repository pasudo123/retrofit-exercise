package com.example.retrofitexercise

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class RetrofitExerciseApplication

fun main(args: Array<String>) {
    runApplication<RetrofitExerciseApplication>(*args)
}

@RestController
@RequestMapping("index")
class IndexController {
    @GetMapping
    fun index(): String {
        return "index"
    }
}