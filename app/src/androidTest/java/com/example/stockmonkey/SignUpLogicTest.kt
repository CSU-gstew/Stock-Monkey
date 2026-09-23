package com.example.stockmonkey

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class FakeUserDao : UserDao {
    val users = mutableListOf<UserItem>()

    override suspend fun insertAll(vararg users: UserItem) {
        this.users.addAll(users)
    }

    override suspend fun insertAllReturnID(vararg user: UserItem): List<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): List<UserItem> = users

    override suspend fun getUserWID(userId: Int): UserItem {
        return users.firstOrNull { it.id == userId } ?: UserItem(0, "", "", emptyList())
    }

    override suspend fun getUserWUsername(username: String): UserItem? {
        return users.firstOrNull { it.username == username }
    }

    override suspend fun update(user: UserItem) {
        val index = users.indexOfFirst { it.id == user.id }
        if (index != -1) users[index] = user
    }

    override suspend fun delete(user: UserItem) {
        users.removeIf { it.id == user.id }
    }
}

class SignUpLogicTest {

    private lateinit var fakeDao: FakeUserDao

    @Before
    fun setup() {
        fakeDao = FakeUserDao()
    }

    @Test
    fun signUp_emptyUsernameOrPassword_returnsError() = runTest {
        val result = signUp(fakeDao, "", "Password123!", "Password123!")
        assertEquals("Username and password cannot be empty.", result)
    }

    @Test
    fun signUp_shortPassword_returnsLengthError() = runTest {
        val result = signUp(fakeDao, "trader", "Pass1!", "Pass1!")
        assertEquals("Password must be 8-20 characters long.", result)
    }

    @Test
    fun signUp_passwordMissingNumber_returnsNumberError() = runTest {
        val result = signUp(fakeDao, "trader", "Password!", "Password!")
        assertEquals("Password must contain at least one number.", result)
    }

    @Test
    fun signUp_passwordMissingSpecialChar_returnsSpecialCharError() = runTest {
        val result = signUp(fakeDao, "trader", "Password123", "Password123")
        assertEquals("Password must contain at least one special character.", result)
    }

    @Test
    fun signUp_passwordsDoNotMatch_returnsMismatchError() = runTest {
        val result = signUp(fakeDao, "trader", "Password123!", "Password456!")
        assertEquals("Passwords do not match.", result)
    }

    @Test
    fun signUp_duplicateUsername_returnsDuplicateError() = runTest {
        fakeDao.insertAll(UserItem(1, "existingTrader", "Secret123!", emptyList()))
        val result = signUp(fakeDao, "existingTrader", "Password123!", "Password123!")
        assertEquals("Username already exists.", result)
    }

    @Test
    fun signUp_validInputs_returnsNullAndInsertsUser() = runTest {
        val result = signUp(fakeDao, "newTrader", "ValidPass123!", "ValidPass123!")
        assertNull(result)
        assertEquals(1, fakeDao.users.size)
        assertEquals("newTrader", fakeDao.users[0].username)
    }
}