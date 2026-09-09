package com.example.stockmonkey

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.util.Log;
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stockmonkey.ui.theme.StockMonkeyTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect

class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockMonkeyTheme {
                Holder()
            }
        }
    }
}

@Composable
fun Holder(){
    Column {
        TitleCard()
        StyleBar()
        StockListTopper()
        StockButtons {  }
        StockList(stockList)
    }
}

@Composable
fun TitleCard(modifier: Modifier = Modifier) {
    Text(
        text = "Home Page",
        modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    )
}

@Composable
fun StyleBar(modifier: Modifier = Modifier) {
    Text(
        text = "-------------------------------------------",
        modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    )
}

@Composable
fun StockListTopper(modifier: Modifier = Modifier) {
    Text(
        text = "Stock List",
        modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    )

}

//Testing stock display made before the Stock Object was made.
@Composable
fun StupidStockDisplay(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier.padding(horizontal = 160.dp, vertical = 5.dp)
    )
}

//Not final display might want to add delete button to it rather than a search based one
@Composable
fun StockDisplay(stock: Stock, modifier: Modifier = Modifier) {
    Text(
        text = stock.name + "(" + stock.symbol + ")" + ", Price: " + stock.close,
        modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    )
}

@Composable
fun StockButtons(onClick: () -> Unit){
    Row(
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        Button(onClick = { onClick() }) {
            Text("+")
        }
        Button(onClick = { onClick() }) {
            Text("-")
        }
    }
}
//@Composable
//fun AddStockButton(onClick: () -> Unit) {
//    Box(
//        contentAlignment = Alignment.BottomStart, // you apply alignment to all children
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Button(onClick = { onClick() }) {
//            Text("+")
//        }
//    }
//}

//Tries to asynchronously pull a single stock object
suspend fun pullStock(ticker: String): Result<Stock> {
    try {
        val newStock = RetrofitClient.api.getStock(ticker = ticker,
            accessKey = "Your API KEY HERE"
            )
        return Result.success(newStock)
    } catch (e: Exception) {
        Log.e("API", "Error Pulling Stock Data", e)
        return Result.failure(e)
    }
}

var stupidList = ArrayList<String>()

var stockList = ArrayList<Stock>()

//This is temporary code to set up the dummy information
// that will be replaced by the stocklist in the database
fun setupStupidList(): ArrayList<String> {
    stupidList.clear()
    stupidList.add("Apple")
    stupidList.add("Alphabet")
    return stupidList
}

//Tries to pull from the api all the stocks by calling getStock
//This will be replaced by pulling from the database to make the stock list
suspend fun setupStockList(): ArrayList<Stock> {
    stockList.clear()
//    val appleTest = Stock("Apple", "AAPL", 1000.0f)
//    stockList.add(appleTest)

    //Temporarily only pulls Apple
    // will add stuff later to pull everything from the database to add to here
    pullStock("AAPL")
        .onSuccess { stock -> stockList.add(stock)
        Log.e("API", stock.toString())}
        .onFailure { error ->
            Log.e("API", "Failed to load stock", error) }

    return stockList
}

@Composable
fun StockList(stocks: ArrayList<Stock>){
    //Get the stock information asynchronously
    LaunchedEffect(Unit) {
        stockList = setupStockList()
    }

    //Display all the stocks in a collumn
    Column {
        for (i in stocks) {
            StockDisplay(i)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    StockMonkeyTheme {
        Holder()
    }
}