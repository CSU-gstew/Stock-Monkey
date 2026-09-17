package com.example.stockmonkey

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.util.Log;
import androidx.compose.runtime.setValue;
import androidx.compose.runtime.getValue
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

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
    var stocks by remember { mutableStateOf<ArrayList<Stock>>(ArrayList<Stock>()) }

    LaunchedEffect(Unit) {
        stocks = setupStockList()
    }

    Column {
        TitleCard()
        StyleBar()
        StockListTopper()
        StockButtons (stocks)
        StockList(stocks)
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

@Composable
fun StockDisplay(stock: Stock, modifier: Modifier = Modifier) {
    Text(
        text = stock.name + "(" + stock.symbol + ")" + ", Price: " + stock.close,
        modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    )
}

//This once you press the button + will pull a stock object and add it to the stocks list
fun addStock(stocks: ArrayList<Stock>){
    //Do a Popup
    //Store the popup string
    //Pull a stock object
    //Add it to the stocks list

    //TODO make pressing the button add a predetermined stock
}

//This will pop up ask for a ticker, search the list for it and delete it if possible
//If not it will send an error message
fun removeStock(stocks: ArrayList<Stock>){
    //Do a Popup
    //Store the popup string
    //Search from the stocks list and remove if if there is a match
    //Else return an error

    //TODO make a mockup by testing if AAPL can be removed
}

//Holder for the two buttons addStock and removeStock for a slightly prettier look
@Composable
fun StockButtons(stocks: ArrayList<Stock>){
    Row(
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        Button(onClick = { addStock(stocks)}) {
            Text("+")
        }
        Button(onClick = { removeStock(stocks) }) {
            Text("-")
        }
    }
}


//Tries to asynchronously pull a single stock object
suspend fun pullStock(ticker: String): Result<Stock> {
    try {
        val newStock = RetrofitClient.api.getStock(ticker = ticker,
            accessKey = "YOUR ACESS KEY"
            )

        return Result.success(newStock)
    } catch (e: Exception) {
        Log.e("API", "Error Pulling Stock Data", e)
        return Result.failure(e)
    }
}
var stockList = ArrayList<Stock>()

//Tries to pull from the api all the stocks by calling getStock
//This will be replaced by pulling from the database to make the stock list
suspend fun setupStockList(): ArrayList<Stock> {
    stockList.clear()
//    val appleTest = Stock("Apple", "AAPL", 1000.0f)
//    stockList.add(appleTest)

    //Temporarily only pulls Apple
    //TODO make it pull from the database instead
    //Through taking the ticker and pulling in new data since obviously the close probably changed
    pullStock("AAPL")
        .onSuccess { stock -> stockList.add(stock)}
        .onFailure { error ->
            Log.e("API", "Failed to load stock", error) }

    return stockList
}

//Display all the stocks in a column
@Composable
fun StockList(stocks: ArrayList<Stock>){

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