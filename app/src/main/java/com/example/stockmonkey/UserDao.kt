package com.example.stockmonkey

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Update

@Dao
interface UserDao {
    @Insert
    suspend fun insertAll(vararg user: UserItem)

    @Delete
    suspend fun delete(user: UserItem)

    @Update
    suspend fun update(user: UserItem)

    @Query(
        """SELECT * FROM user_item_table WHERE username LIKE :name LIMIT 1"""
    )
    suspend fun getUserWName(name: String): UserItem
}