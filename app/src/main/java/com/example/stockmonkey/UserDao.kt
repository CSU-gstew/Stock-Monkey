package com.example.stockmonkey

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update

/*
Methods for the User database

Maybe playing it a little too safe with only being able to access via ID
But I can always add more methods
 */
@Dao
interface UserDao {
    // General insert
    @Insert
    suspend fun insertAll(vararg user: UserItem)

    // If u need to insert and need the ID and theoretically don't know it
    // (mostly for the update function)
    @Insert
    suspend fun insertAllReturnID(vararg user: UserItem): List<Long>

    // Deletes it idk, just make sure you're referring to the right user (via ID is best)
    @Delete
    suspend fun delete(user: UserItem)

    // Updates it, best example of usage is in the test file
    @Update
    suspend fun update(user: UserItem)

    // Lets u select a user with an ID
    @Query(
        """SELECT * FROM user_item_table WHERE id LIKE :id LIMIT 1"""
    )
    suspend fun getUserWID(id: Int): UserItem

    // Returns all users as a list
    @Query("""SELECT * FROM user_item_table""")
    suspend fun getAllUsers(): List<UserItem>
}