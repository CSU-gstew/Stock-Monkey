package com.example.stockmonkey

import android.content.Context
import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [UserItem::class],
    version = 1,
    exportSchema = true,
)
@ColumnTypeConverters(StockTickerConverter::class)
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
                ).addCallback(Callback(context.applicationContext)).build()
                INSTANCE = instance
                instance
                /*
                (Note for future ppl + myself)
                To use the instance:
                private val db = UserDatabase.getDatabase(context).userDao()
                 */
            }
        }

        private class Callback(private val context: Context): RoomDatabase.Callback(){
            override suspend fun onCreate(connection: SQLiteConnection) {
                super.onCreate(connection)
                //INSTANCE?.let {
                    CoroutineScope(Dispatchers.IO).launch{
                        val dao = getDatabase(context).userDao()
                        if(dao.getAllUsers().isEmpty()){
                            val testUser = UserItem(0,"TestUser", "12345", listOf<StockTicker>())
                            dao.insertAll(testUser)
                        }
                    }
                //}
            }
        }
    }
}

