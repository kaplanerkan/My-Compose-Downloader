package com.mycomposedownloader.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL =
        "http://192.168.1.187:4200/"           // SUNUCUNUZUN BASE URL'i ile DEĞİŞTİRİN

    private val loggingInterceptor = HttpLoggingInterceptor()

    init {
        // You can change the level to HEADERS or BASIC if needed
        loggingInterceptor.level =
            HttpLoggingInterceptor.Level.BODY
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
    }


    // Create an OkHttpClient and add the logging interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()


    val instance: FileDownloadService by lazy {
        val retrofit = Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())         // veya kullandığınız converter
            .build()

        retrofit.create(FileDownloadService::class.java)
    }

}