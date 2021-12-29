package com.example.retrofitexercise.config

import com.example.retrofitexercise.client.shortnews.ShortNewsClient
import com.example.retrofitexercise.client.NullOrEmptyConverterFactory
import com.example.retrofitexercise.client.shortnews.ShortNewsAsyncClient
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
@ConfigurationProperties(prefix = "app.client")
class ClientDiConfiguration(
    private val mapper: ObjectMapper,
) {

    abstract class Client(
        var host: String? = null,
        var connectionTimeout: Long = 3000,
        var readTimeout: Long = 3000,
        var writeTimeout: Long = 3000,
    )
    class ShortNews : Client()
    val shortNews = ShortNews()

    @Bean
    fun shortNewsClient(): ShortNewsClient {

        val headers = Headers.of(mapOf(
            "Content-Type" to "application/json",
            "Accept" to "application/json"
        ))

        val httpClient = generateHttpClient(headers, shortNews)

        return Retrofit.Builder()
            .baseUrl(shortNews.host!!)
            .addConverterFactory(NullOrEmptyConverterFactory())
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .callFactory(httpClient)
            .build()
            .create(ShortNewsClient::class.java)
    }

    @Bean
    fun shortNewsAsyncClient(): ShortNewsAsyncClient {

        val headers = Headers.of(mapOf(
            "Content-Type" to "application/json",
            "Accept" to "application/json"
        ))

        val httpClient = generateHttpClient(headers, shortNews)

        return Retrofit.Builder()
            .baseUrl(shortNews.host!!)
            .addConverterFactory(NullOrEmptyConverterFactory())
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .callFactory(httpClient)
            .build()
            .create(ShortNewsAsyncClient::class.java)
    }

    companion object {
        private fun generateHttpClient(headers: Headers, client: Client): OkHttpClient {

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            return OkHttpClient.Builder()
                .connectTimeout(client.connectionTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(client.readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(client.writeTimeout, TimeUnit.MILLISECONDS)
//                .addInterceptor(RetrofitCustomLoggingInterceptor())
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val builder = chain.request().newBuilder().headers(headers)
                    val request = builder.build()
                    chain.proceed(request)
                }.build()
        }
    }
}