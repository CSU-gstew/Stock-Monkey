package com.example.stockmonkey

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class Stock (
    val name: String,
    val symbol: String,
    var close: Float,
)

interface MarketStackApiService {
    //Not sure where to put in api key and limit.
    @GET("tickers/{ticker}/eod/latest")
    suspend fun getStock(
        @Path("ticker") ticker: String,
        @Query("access_key") accessKey: String,
        @Query("limit") limit: Int = 1
    ): Stock


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