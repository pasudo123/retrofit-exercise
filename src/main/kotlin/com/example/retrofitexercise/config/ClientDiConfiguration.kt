package com.example.retrofitexercise.config

import com.example.retrofitexercise.client.interceptor.RetrofitCustomLoggingInterceptor
import com.example.retrofitexercise.client.shortnews.ShortNewsClientService
import com.example.retrofitexercise.client.shortnews.ShortNewsDummyClient
import com.example.retrofitexercise.client.shortnews.ShortNewsDummyClientService
import com.example.retrofitexercise.client.shortnews.ShortNewsRealClient
import com.example.retrofitexercise.client.shortnews.ShortNewsRealClientService
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
    fun shortNewsClientService(): ShortNewsClientService {

        if(shortNews.dummy!!) {
            return ShortNewsDummyClientService(ShortNewsDummyClient(mapper))
        }

        val httpClient = generateHttpClient()
        val shortNewsRealClient = Retrofit.Builder()
            .baseUrl(shortNews.host!!)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .callFactory(httpClient)
            .build()
            .create(ShortNewsRealClient::class.java)

        return ShortNewsRealClientService(shortNewsRealClient)
    }

    companion object {
        private fun generateHttpClient(): OkHttpClient {

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            return OkHttpClient.Builder()
                .addInterceptor(RetrofitCustomLoggingInterceptor())
                .addInterceptor { chain ->
                    val builder = chain.request().newBuilder()
                        .header("Accept", "application/json")
                    val request = builder.build()
                    chain.proceed(request)
                }.build()
        }
    }
}