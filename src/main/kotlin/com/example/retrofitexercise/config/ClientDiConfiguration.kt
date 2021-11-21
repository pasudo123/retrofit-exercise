package com.example.retrofitexercise.config

import com.example.retrofitexercise.client.shortnews.ShortNewsClient
import com.example.retrofitexercise.client.shortnews.ShortNewsDummyClient
import com.example.retrofitexercise.client.shortnews.ShortNewsRealClient
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.client")
@Validated
class ClientDiConfiguration(
    private val mapper: ObjectMapper
) {

    private val shortNews = ShortNews()

    class ShortNews {
        var host: String? = null
        var dummy: Boolean? = null
    }

    @Bean
    fun shortNewsClient(): ShortNewsClient {
        val httpClient = generateHttpClient()

        val shortNewsClient = if(shortNews.dummy!!) {
            ShortNewsDummyClient()
        } else {
            ShortNewsRealClient()
        }

        return Retrofit.Builder()
            .baseUrl(shortNews.host!!)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .callFactory(httpClient)
            .build()
            .create(shortNewsClient::class.java)
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