package com.example.stockmonkey

import androidx.room3.ColumnTypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StockTickerConverter {
    @ColumnTypeConverter
    fun fromList(list: List<StockTicker>?): String{
        return Json.encodeToString(list ?: emptyList())
    }

    @ColumnTypeConverter
    fun toList(json: String?): List<StockTicker>{
        if (json.isNullOrEmpty()) return emptyList()
        return Json.decodeFromString(json)
    }
}