package com.example.stockmonkey

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HomePageTest {

    private lateinit var fakeDao: FakeUserDao
    private lateinit var testUser: UserItem

    @Before
    fun setup() {
        fakeDao = FakeUserDao()
        testUser = UserItem(
            id = 1,
            username = "trader",
            password = "Password123!",
            tickerList = listOf(
                StockTicker("AAPL", "Apple Inc.", 150.0f),
                StockTicker("GOOGL", "Alphabet Inc.", 140.0f),
                StockTicker("MSFT", "Microsoft Corp.", 300.0f)
            )
        )
    }

    @Test
    fun setupStockList_populatesListFromUserItem() = runTest {
        val stocks = setupStockList(testUser)
        assertEquals(3, stocks.size)
        assertEquals("AAPL", stocks[0].symbol)
        assertEquals("GOOGL", stocks[1].symbol)
        assertEquals("MSFT", stocks[2].symbol)
    }

    @Test
    fun removeStock_removesOlderStockWithoutCrashing() = runTest {
        val stocks = setupStockList(testUser)

        // Remove an older element (index 0) rather than the most recent one
        val updatedStocks = removeStock(stocks, "AAPL", testUser, fakeDao)

        assertEquals(2, updatedStocks.size)
        assertFalse(updatedStocks.any { it.symbol == "AAPL" })
        assertTrue(updatedStocks.any { it.symbol == "GOOGL" })
        assertTrue(updatedStocks.any { it.symbol == "MSFT" })
    }

    @Test
    fun removeStock_caseInsensitiveOrNotFound_preservesList() = runTest {
        val stocks = setupStockList(testUser)
        val updatedStocks = removeStock(stocks, "NON_EXISTENT", testUser, fakeDao)

        assertEquals(3, updatedStocks.size)
    }

    @Test
    fun stockModelConversions_areAccurate() {
        val stock = Stock("NVIDIA Corporation", "NVDA", 120.5f)
        val ticker = StockToStockTicker(stock)

        assertEquals("NVDA", ticker.ticker)
        assertEquals("NVIDIA Corporation", ticker.companyName)
        assertEquals(120.5f, ticker.eodPrice)

        val convertedStock = StockTickerToStock(ticker)
        assertEquals(stock.name, convertedStock.name)
        assertEquals(stock.symbol, convertedStock.symbol)
        assertEquals(stock.close, convertedStock.close)
    }
}