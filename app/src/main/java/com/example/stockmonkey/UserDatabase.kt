package com.example.stockmonkey

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(entities = [UserItem::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    // If I got my info right this will let u make a database instance
    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase{
            // Returns INSTANCE if not null, creates database if it is
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
                /*
                (Note for future ppl + myself)
                To use the instance:
                private val db = UserDatabase.getDatabase(context).userDao()
                 */
            }
        }
    }
}