package com.example.stockmonkey

import android.content.pm.ApplicationInfo
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stockmonkey.ui.theme.StockMonkeyTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userDao = UserDatabase.getDatabase(applicationContext).userDao()
        lifecycleScope.launch {
            val user = userDao.getUserWID(intent.getIntExtra("LOGGED_IN_USER_ID",0))
            val name = intent.getStringExtra("LOGGED_IN_USERNAME")
            enableEdgeToEdge()
            setContent {
                StockMonkeyTheme {
                    Holder(name = name, userDao, user)
                }
            }
        }

    }
}

@Composable
fun Holder(name: String?, userDao: UserDao, user: UserItem){
    var stocks by remember { mutableStateOf<ArrayList<Stock>>(ArrayList<Stock>()) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showRemoveDialog by remember { mutableStateOf(false) }


    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        stocks = setupStockList(user)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(0.0f to Color(0xFFB1A1BA), 0.8f to Color(0xFFF3F0F4), 1.0f to Color(0xFFF3F0F4))
            ),
    ) {
        BgImage()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                TitleCard(name = name)
                StyleBar()
                StockButtons(
                    addButton = {
                        showAddDialog = true
                    },
                    removeButton = {
                        showRemoveDialog = true
                    }
                )


                //This essentially lets the stockTickerDialogue pop up
                //Then yeet its information into removeStock
                if (showRemoveDialog) {
                    StockTickerDialog(
                        onDismiss = {
                            showRemoveDialog = false
                        },
                        onConfirm = { ticker ->
                            scope.launch { stocks = removeStock(stocks, ticker, user, userDao) }
                            showRemoveDialog = false
                        }
                    )
                }
                if (showAddDialog) {
                    StockTickerDialog(
                        onDismiss = {
                            showAddDialog = false
                        },
                        onConfirm = { ticker ->
                            scope.launch { stocks = addStock(stocks, ticker, user, userDao) }
                            showAddDialog = false
                        }
                    )
                }

                StockListTopper()
                StockList(stocks)
            }
        }
    }
}

@Composable
fun TitleCard(name: String?) {
    val mainGradient = listOf(Color(0xFFA05CFF), Color(0xFF852EFF))

    Text(
        text = "Welcome",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
    )
    Text(
        text = "$name!",
        fontSize = 30.sp,
        style = TextStyle(brush = Brush.verticalGradient(colors = mainGradient)),
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}

@Composable
fun StyleBar() {
    HorizontalDivider(
        thickness = 3.dp,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun StockListTopper() {
    Text(
        text = "Your Tracked Stocks",
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary
    )

}

@Composable
fun StockDisplay(stock: Stock, modifier: Modifier = Modifier) {
    Text(
        text = stock.name + "(" + stock.symbol + ")" + ", Price: " + stock.close,
        modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp),
        fontSize = 15.sp,
        fontFamily = FontFamily.Monospace,
    )
}

//Big boy dialogue box that takes in some text input to spit back out the ticker
@Composable
fun StockTickerDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit
) {
    var ticker by remember { mutableStateOf("") }

    AlertDialog(
        containerColor = Color(0xFFF3F0F4),
        onDismissRequest = onDismiss,
        text = {
            OutlinedTextField(
                value = ticker,
                onValueChange = { ticker = it },
                label = { Text("Stock ticker", fontFamily = FontFamily.Monospace, color = MaterialTheme.colorScheme.primary) },
                placeholder = { Text("Example: AAPL", fontFamily = FontFamily.Monospace, color = MaterialTheme.colorScheme.secondary) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFF9885A8),
                    focusedBorderColor = Color(0xFF852EFF)
                )
            )
        },
        confirmButton = {
            TextButton(
                enabled = ticker.isNotBlank(),
                onClick = {
                    onConfirm(ticker.trim().uppercase())
                }
            ) {
                Text("Confirm", fontFamily = FontFamily.Monospace)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", fontFamily = FontFamily.Monospace)
            }
        }
    )
}

//This once you press the button + will pull a stock object and add it to the stocks list
suspend fun addStock(stocks: ArrayList<Stock>, ticker: String, user: UserItem, userDao: UserDao): ArrayList<Stock> {
    //Do a Popup
    //Store the popup string
    //Pull a stock object
    //Add it to the stocks list
    val newStocks = ArrayList(stocks)
    Log.d("HomePage", "Inside Add Button")

    //Getting screams due to suspend fuckery will fix later once removeStock works
    pullStock(ticker)
        .onSuccess { stock -> newStocks.add(stock)}
        .onFailure { error ->
            Log.e("API", "Failed to load stock", error) }

//    val stupidNewStock = Stock("No Name", ticker, 100.0f)
//    newStocks.add(stupidNewStock)

    Log.d("Database", (stockListToStockTickerList(newStocks)).toString())
    //This should add the new stock to the users tickerList
    userDao.update(user.copy(tickerList = stockListToStockTickerList(newStocks)))

    return newStocks
}

//This will pop up ask for a ticker, search the list for it and delete it if possible
//If not it will send an error message
suspend fun removeStock(stocks: ArrayList<Stock>, ticker: String, user: UserItem, userDao: UserDao): ArrayList<Stock> {
    //Do a Popup
    //Store the popup string
    //Search from the stocks list and remove if if there is a match
    //Else return an error
    val newStocks = ArrayList(stocks)

//    Log.d("HomePage", "Inside Remove Button")
//
//    Log.d("HomePage", newStocks.toString())
    for (stock in newStocks){
        if(stock.symbol == ticker){
            newStocks.remove(stock)
        }
    }
//    Log.d("HomePage", newStocks.toString())
    userDao.update(user.copy(tickerList = stockListToStockTickerList(newStocks)))

    return newStocks
}

//Holder for the two buttons addStock and removeStock for a slightly prettier look
@Composable
fun StockButtons( addButton: () -> Unit, removeButton: () -> Unit
){
    val mainGradient = listOf(Color(0xFFA05CFF), Color(0xFF852EFF))
    Column(
        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Button(
            onClick = addButton ,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(50.dp)
                .background(brush = Brush.verticalGradient(mainGradient)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Text("Add Stock By Ticker",
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,)
        }
        Button(
            onClick = removeButton,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(50.dp)
                .background(brush = Brush.verticalGradient(mainGradient)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Text("Remove Stock By Ticker",
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,)
        }
    }
}


//Tries to asynchronously pull a single stock object
suspend fun pullStock(ticker: String): Result<Stock> {
    try {
        val newStock = RetrofitClient.api.getStock(ticker = ticker,
            accessKey = "dd4a485c14e147c9a5fcd760d39570a0"
            )

        return Result.success(newStock)
    } catch (e: Exception) {
        Log.e("API", "Error Pulling Stock Data", e)
        return Result.failure(e)
    }
}

//Takes the database StockTicker object and converts it into a Stock object
//The reason we don't have one Class is because the database cries if you change anything
//And you can't change the name of the thing you technically could change Stock
//Without breaking the GSON converter that relies on names to convert the JSON into a kotlin object
fun StockTickerToStock(stockTicker: StockTicker): Stock {
    val stock = Stock(stockTicker.companyName, stockTicker.ticker, stockTicker.eodPrice)
    return stock
}

fun StockToStockTicker(stock: Stock): StockTicker {
    val stockTicker = StockTicker(stock.symbol, stock.name, stock.close)
    return stockTicker
}

fun stockListToStockTickerList(stocks: ArrayList<Stock>): List<StockTicker>{
    val stockTickers = mutableListOf<StockTicker>();
    for (stock in stocks){
        stockTickers.add(StockToStockTicker(stock))
    }
    return stockTickers;
}

//Tries to pull from the api all the stocks by calling getStock
//This will be replaced by pulling from the database to make the stock list
suspend fun setupStockList(user: UserItem): ArrayList<Stock> {
    var stockList = ArrayList<Stock>()
    stockList.clear()
//    val appleTest = Stock("Apple", "AAPL", 1000.0f)
//    stockList.add(appleTest)

    //Temporarily only pulls Apple
    //TODO make it pull from the database instead
    //Through taking the ticker and pulling in new data since obviously the close probably changed
//    pullStock("AAPL")
//        .onSuccess { stock -> stockList.add(stock)}
//        .onFailure { error ->
//            Log.e("API", "Failed to load stock", error) }

    //Takes in the list converts them all to stocks and adds them
    val tickerList = user.tickerList
    for(ticker in tickerList){
        stockList.add(StockTickerToStock(ticker))
    }

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
//        Holder(
//            name = "TestUser", userDao = UserDatabase.getDatabase().userDao(), UserItem(0, "", "", List<StockTicker>(1, init = { StockTicker("AAPL", "Apple", 1.01f) }))
//        )
    }
}