package com.example.stockmonkey

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Test

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import java.io.IOException

class HomePageTest {
    @Test
    fun pullStock_returnsStock_whenApiSucceeds() = runTest {
        val result = pullStock("AAPL")

        assertFalse(result.isFailure)
    }
}