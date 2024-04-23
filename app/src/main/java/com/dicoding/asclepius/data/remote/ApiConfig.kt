package com.dicoding.asclepius.data.remote

import com.dicoding.asclepius.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val API_KEY = BuildConfig.API_KEY
    private const val BASE_URL = BuildConfig.BASE_URL

    fun getApiService(): ApiService {
        val interceptor = Interceptor {
            val chainReq = it.request()
            val request = chainReq.newBuilder()
                .addHeader("X-Api-Key", API_KEY)
                .build()
            it.proceed(request)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}