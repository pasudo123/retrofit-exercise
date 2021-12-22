package com.example.retrofitexercise

import com.example.retrofitexercise.client.NullOrEmptyConverterFactory
import com.example.retrofitexercise.client.shortnews.ShortNewsClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.net.InetAddress
import java.util.concurrent.TimeUnit


@BaseTestEnvironment
abstract class MockWebServerSupport {

    companion object {
        const val HOST = "localhost"
        const val PORT = 44444
        const val TIMEOUT = 5000L
    }

    lateinit var server: MockWebServer

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        server.dispatcher = MockDispatcher()
        server.start(InetAddress.getByName(HOST), PORT)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    object RetrofitTestApi {

        fun <T> buildRetrofitClient(host: String, clazz: Class<T>) = Retrofit.Builder()
            .baseUrl(host)
            .addConverterFactory(NullOrEmptyConverterFactory())
            .addConverterFactory(JacksonConverterFactory.create(ObjectMapper().registerKotlinModule()))
            .callFactory(httpClient())
            .build()
            .create(clazz)

        private fun httpClient() = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.NONE
            })
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder().headers(
                    Headers.of(mapOf(
                        "Content-Type" to "application/json",
                        "Accept" to "application/json")))
                val request = builder.build()
                chain.proceed(request)
            }.build()
    }
}

class MockDispatcher : Dispatcher() {

    private val logger = LoggerFactory.getLogger(javaClass)

    private enum class PathType(val path: String) {
        RESULT_200("/result-200"),
        RESULT_200_BODY_EMPTY("/result-200-body-empty"),
        RESULT_204("/result-204"),
        RESULT_404("/result-404"),
        RESULT_500("/result-500"),
    }

    /**
     * retrofit2 에서 외부 api 를 호출하면 해당 디스패처가 호출된다.
     * Mocking 된 결과를 반환한다.
     */
    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path.removeQuerystringIfExist()
        val method = request.method

        logger.info("dispatch :: =========================")
        logger.info("dispatch :: {}", "[$method] $path")
        logger.info("dispatch :: =========================")

        return when (path) {
            PathType.RESULT_200.path -> MockResponse().setResponseCode(HttpStatus.OK.value()).setBody("{ \"message\": \"Hi Hello\" }")
            PathType.RESULT_200_BODY_EMPTY.path -> MockResponse().setResponseCode(HttpStatus.OK.value()).setBody("")
            PathType.RESULT_204.path -> MockResponse().setResponseCode(HttpStatus.NO_CONTENT.value())
            PathType.RESULT_404.path -> MockResponse().setResponseCode(HttpStatus.NOT_FOUND.value()).setBody("{ \"message\": \"404 NOT FOUND\" }")
            PathType.RESULT_500.path -> MockResponse().setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).setBody("{ \"message\": \"INTERNAL SERVER ERROR\" }")
            else -> MockResponse().setResponseCode(HttpStatus.OK.value()).setBody("\"message\": \"default\" }")
        }
    }

    private fun String.removeQuerystringIfExist(): String {
        return this.split("?").first()
    }
}

