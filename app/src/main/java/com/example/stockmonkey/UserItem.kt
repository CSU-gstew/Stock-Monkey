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
    /*
    So I don't really want to go about making a column for the list yet
    Mostly cuz it's complicated I think
    Also cuz it should be in whatever datatype we use to store StockTicker info in
    Which I'm *pretty sure* we don't have
    It's easier to add a column than update the datatype of one, so I'll do it when the time comes
     */
    //@ColumnInfo(name = "tickerList") val tickerList: List<StockTicker>
    )