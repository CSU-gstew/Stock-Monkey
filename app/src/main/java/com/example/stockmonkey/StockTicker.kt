package com.example.stockmonkey

import kotlinx.serialization.Serializable

@Serializable
data class StockTicker (
    val ticker: String,
    val companyName: String,
    val eodPrice: Float,
    )