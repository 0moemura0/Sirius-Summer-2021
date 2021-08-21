package com.example.tinkoffproject.model.utils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

fun Retrofit.Builder.setClient(apiKey: Pair<String, String>) = apply {
    val okkHttpBuilder = OkHttpClient.Builder()

    okkHttpBuilder.addInterceptor(ApiKeyInterceptor(apiKey))

    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
    okkHttpBuilder.addInterceptor(logging)

    okkHttpBuilder.connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)

    this.client(okkHttpBuilder.build())

}

class ApiKeyInterceptor(private val apiKey: Pair<String, String>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader(apiKey.first, apiKey.second)
        return chain.proceed(request.build())
    }
}