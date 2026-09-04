package com.example.stockmonkey

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class UserDatabaseTest {
    //there's like 20 different ways to test ts i'm gonna crash out
    private lateinit var db: UserDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup(){
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext,UserDatabase::class.java).setDriver(BundledSQLiteDriver()).build()
        dao = db.userDao()
    }

    @After
    fun teardown(){
        db.close()
    }

    @Test
    fun insertTest() = runTest {
        val name = "userTest"
        val pass = "passTest"
        val ticker = listOf(StockTicker("tick", "Apple", 1.01F))
        val user = UserItem(0, name, pass, ticker)

        dao.insertAll(user)
        val datauser = dao.getUserWName(name)
        val newname = datauser.username
        val newpass = datauser.password
        val newticker = datauser.tickerList
        assertTrue(name.equals(newname))
        assertTrue(pass.equals(newpass))
        assertTrue(ticker.equals(newticker))
    }
}