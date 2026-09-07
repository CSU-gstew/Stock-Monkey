package com.example.stockmonkey

data class Stock (
    val name: String,
    val ticker: String,
    var closingPrice: Float,
)