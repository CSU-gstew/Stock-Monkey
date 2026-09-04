package com.example.stockmonkey

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.room3.ColumnInfo

/*
Makes the database table for User
 */
@Entity(tableName = "user_item_table")
data class UserItem (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "tickerList") val tickerList: List<StockTicker>
    )