package com.example.stockmonkey

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class UserDatabaseTest {
    //there's like 20 different ways to test ts i'm gonna crash out
    private lateinit var db: UserDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup(){
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext,
            UserDatabase::class.java).setDriver(BundledSQLiteDriver()).build()
        dao = db.userDao()
    }

    @After
    fun teardown(){
        db.close()
    }

    /*
    Tests to make sure u can add at least one user
     */
    @Test
    fun insertTest() = runTest {
        val name = "userTest"
        val user = UserItem(1, name, "passTest", listOf(StockTicker("tick", "Apple", 1.01F)))

        val uID = dao.insertAllReturnID(user)
        val datauser = dao.getUserWID(uID[0].toInt())
        assertTrue(user.equals(datauser))
    }

    /*
    Just testing insertall and getall works
     */
    @Test
    fun getAllTest() = runTest {
        val user1 = UserItem(0,"name1", "passTest1", listOf(StockTicker("tick", "Apple", 1.01F)))
        val user2 = UserItem(0,"name2", "passTest2", listOf(StockTicker("tick", "Apple", 1.01F)))
        val user3 = UserItem(0,"name3", "passTest3", listOf(StockTicker("tick", "Apple", 1.01F)))
        dao.insertAll(user1, user2, user3)

        val all = dao.getAllUsers()
        assertTrue(all[0].username == "name1")
        assertTrue(all[1].username == "name2")
        assertTrue(all[2].username == "name3")
    }

    /*
    Tests to make sure u can delete a user
     */
    @Test
    fun deleteTest() = runTest{
        val name = "userTest2"
        val user = UserItem(1,name, "passTest", listOf(StockTicker("tick", "Apple", 1.01F)))
        val uID = dao.insertAllReturnID(user)

        val all1 = dao.getAllUsers()
        assertTrue(all1.contains(user))

        dao.delete(dao.getUserWID(uID[0].toInt()))

        val all = dao.getAllUsers()
        assertFalse(all.contains(user))
    }

    /*
    Tests how this thing handles multiple users
     */
    @Test
    fun multiTest() = runTest{
        // ID MUST be 0, if it's 1+ it won't try to autoincrement and just stays at that ID#
        // there's prolly a way to fix that (but the best solution is just use a 0 lol)
        val name = "userTest3"
        val user1 = UserItem(0,name, "passTest", listOf(StockTicker("tick", "Apple", 1.01F)))
        val name2 = "userTest4"
        val user2 = UserItem(0,name2, "passTest", listOf(StockTicker("tick", "Apple", 1.01F)))

        val uID = dao.insertAllReturnID(user1, user2)
        val datauser1 = dao.getUserWID(uID[0].toInt())
        val datauser2 = dao.getUserWID(uID[1].toInt())
        assertTrue(datauser1.id == 1)
        assertTrue(datauser2.id == 2)

        // Also checks to make sure if u add something later on it just goes to the next id, also tests duplicate usernames
        val lateruser = UserItem(0,name, "passTest", listOf(StockTicker("tick", "Apple", 1.01F)))
        val newID = dao.insertAllReturnID(lateruser)
        val datauser3 = dao.getUserWID(newID[0].toInt())
        assertTrue(datauser3.username == "userTest3")
        assertTrue(datauser3.id == 3)
    }

    /*
    Tests update
     */
    @Test
    fun updateTest() = runTest{
        val name = "userTest6"
        val user = UserItem(0,name, "passTest", listOf(StockTicker("tick", "Apple", 1.01F)))
        val GenId = dao.insertAllReturnID(user)

        val newuser = user.copy(id = GenId[0].toInt(), username = "updatedUserTest6", password = "1234lol")
        dao.update(newuser)

        val all = dao.getAllUsers()
        assertTrue(all.size == 1)
        val testuser = dao.getUserWID(GenId[0].toInt())
        assertTrue(testuser.password == "1234lol")
        assertTrue(testuser.username == "updatedUserTest6")
    }


}