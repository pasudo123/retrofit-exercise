package com.example.retrofitexercise.client

import okhttp3.ResponseBody
import org.slf4j.LoggerFactory
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class NullOrEmptyConverterFactory : Converter.Factory() {

    private val logger = LoggerFactory.getLogger(javaClass)

    companion object {
        const val READ_MAX_SIZE = 1024L
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, Any> {
        return Converter { responseBody ->
            val source = responseBody.source()
            source.request(READ_MAX_SIZE)
            val buffer = source.buffer

            if (buffer.size() <= 0L) {
                logger.info("if buffer is zero down, message input")
                responseBody.source().buffer.writeUtf8("{ \"message\": \"body is empty\" }")
            }

            retrofit
                .nextResponseBodyConverter<ResponseBody>(this, type, annotations)
                .convert(responseBody)
        }
    }
}
