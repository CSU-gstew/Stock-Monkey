package com.example.stockmonkey

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
        StockList(stupidList)
    }
}

@Composable
fun TitleCard(modifier: Modifier = Modifier) {
    Text(
        text = "Home Page",
        modifier = modifier.padding(horizontal = 150.dp, vertical = 5.dp)
    )
}

@Composable
fun StyleBar(modifier: Modifier = Modifier) {
    Text(
        text = "------------",
        modifier = modifier.padding(horizontal = 165.dp, vertical = 5.dp)
    )
}

@Composable
fun StockListTopper(modifier: Modifier = Modifier) {
    Text(
        text = "Stock List",
        modifier = modifier.padding(horizontal = 160.dp, vertical = 5.dp)
    )
}

@Composable
fun Stock(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier.padding(horizontal = 160.dp, vertical = 5.dp)
    )
}

@Composable
fun AddStockButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center, // you apply alignment to all children
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = { onClick() }) {
            Text("+")
        }
    }
}



var stupidList = ArrayList<String>()

//This is temporary code to set up the dummy information
// that will be replaced by the stocklist in the database
fun setupStupidList(): ArrayList<String> {
    stupidList.clear()
    stupidList.add("Apple")
    stupidList.add("Alphabet")
    return stupidList
}

@Composable
fun StockList(stocks: ArrayList<String>){
    //Create an add button at the top.

    //For list of all the things make a stock object
    stupidList = setupStupidList()
    Column {
        AddStockButton {  }
        for (i in stocks) {
            Stock(i)
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