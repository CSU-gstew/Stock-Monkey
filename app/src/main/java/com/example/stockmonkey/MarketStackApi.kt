package com.example.stockmonkey

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class Stock (
    val name: String,
    val ticker: String,
    var closingPrice: Float,
)

interface MarketStackApiService {
    //Not sure where to put in
    @GET("tickers/{ticker}/eod?")
    suspend fun getStock(@Path("ticker") ticker: String): Stock
}

object RetrofitClient {
    private const val BASE_URL = "https://api.marketstack.com/v2/"

    // Create logging interceptor for debugging
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    // Create OkHttp client with interceptor
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    // Create Retrofit instance
    val api: MarketStackApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MarketStackApiService::class.java)
}