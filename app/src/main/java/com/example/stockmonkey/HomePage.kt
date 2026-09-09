package com.example.stockmonkey

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.util.Log;
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stockmonkey.ui.theme.StockMonkeyTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment

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
        text = stock.name + "(" + stock.ticker + ")" + ", Price: " + stock.closingPrice,
        modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    )
}

fun PullStock(){
    suspend fun getStock(ticker: String): Result<Stock> {
        return try {
            val newStock = RetrofitClient.api.getStock(ticker)
            return Result.success(newStock)
        } catch (e: Exception) {
            Log.e("API", "Error Pulling Stock Data", e)
            return Result.failure(e)
        }
    }
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

//This will be replaced by pulling from the database to make the stock list
fun setupStockList(): ArrayList<Stock> {
    stockList.clear()
    val appleTest = Stock("Apple", "AAPL", 1000.0f)
    stockList.add(appleTest)
    return stockList
}

@Composable
fun StockList(stocks: ArrayList<Stock>){
    //Create an add button at the top.

    //For list of all the things make a stock object
    stupidList = setupStupidList()
    stockList = setupStockList()
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