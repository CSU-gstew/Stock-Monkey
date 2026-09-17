package com.example.stockmonkey

suspend fun signUp(
    userDao: UserDao,
    username: String,
    password: String,
    confirmPassword: String
): String? {
    if (username.isBlank() || password.isBlank()) {
        return "Username and password cannot be empty."
    }

    if (password.length < 8 || password.length > 20) {
        return "Password must be 8-20 characters long."
    }
    if (!password.any { it.isDigit() }) {
        return "Password must contain at least one number."
    }
    if (!password.any { !it.isLetterOrDigit() }) {
        return "Password must contain at least one special character."
    }

    if (password != confirmPassword) {
        return "Passwords do not match."
    }

    val allUsers = userDao.getAllUsers()
    val existingUser = allUsers.find { it.username == username }
    if (existingUser != null) {
        return "Username already exists."
    }

    userDao.insertAll(
        UserItem(
            username = username,
            password = password,
            tickerList = emptyList<StockTicker>()
        )
    )
    return null
}