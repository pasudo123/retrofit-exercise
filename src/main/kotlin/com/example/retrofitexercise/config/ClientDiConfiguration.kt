package com.example.retrofitexercise.config

import com.example.retrofitexercise.client.shortnews.ShortNewsClient
import com.example.retrofitexercise.client.shortnews.ShortNewsDummyClient
import com.example.retrofitexercise.client.shortnews.ShortNewsRealClient
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Configuration
@ConfigurationProperties(prefix = "app.client")
class ClientDiConfiguration(
    private val mapper: ObjectMapper
) {

    val shortNews = ShortNews()

    class ShortNews {
        var host: String? = null
        var dummy: Boolean? = null
    }

    @Bean
    fun shortNewsClient(): ShortNewsClient {
        val httpClient = generateHttpClient()

        val shortNewsClientClazz = if(shortNews.dummy!!) {
            ShortNewsDummyClient::class.java
        } else {
            ShortNewsRealClient::class.java
        }

        return Retrofit.Builder()
            .baseUrl(shortNews.host!!)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .callFactory(httpClient)
            .build()
            .create(shortNewsClientClazz)
    }

    companion object {
        private fun generateHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val builder = chain.request().newBuilder()
                        .header("Accept", "application/json")
                    val request = builder.build()
                    chain.proceed(request)
                }.build()
        }
    }
}