package com.example.tinkoffproject.data.network

import com.example.tinkoffproject.data.UserData
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!request.url.toString().contains("users")) {
            val token: String = UserData.email
            if (token.isNotEmpty()) {
                val finalToken = "Bearer $token"
                request = request.newBuilder()
                    .addHeader("Authorization", finalToken)
                    .build()
            }
        }
        return chain.proceed(request)
    }
}