package com.example.retrofitexercise.config

import com.example.retrofitexercise.client.shortnews.ShortNewsClient
import com.example.retrofitexercise.client.shortnews.ShortNewsDummyClient
import com.example.retrofitexercise.client.shortnews.ShortNewsRealClient
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Configuration
@ConfigurationProperties(prefix = "app.client")
class ClientDiConfiguration(
    private val mapper: ObjectMapper,
) {

    val shortNews = ShortNews()

    class ShortNews {
        var host: String? = null
        var dummy: Boolean? = null
    }

    @Bean
    fun shortNewsClient(): ShortNewsClient {

        if(shortNews.dummy!!) {
            return ShortNewsDummyClient(mapper)
        }

        val httpClient = generateHttpClient()
        return Retrofit.Builder()
            .baseUrl(shortNews.host!!)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .callFactory(httpClient)
            .build()
            .create(ShortNewsRealClient::class.java)
    }

    companion object {
        private fun generateHttpClient(): OkHttpClient {

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BASIC
            }

            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val builder = chain.request().newBuilder()
                        .header("Accept", "application/json")
                    val request = builder.build()
                    chain.proceed(request)
                }.build()
        }
    }
}