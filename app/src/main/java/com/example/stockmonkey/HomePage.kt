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
import androidx.compose.foundation.layout.*;

class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Holder()
//            StockMonkeyTheme {
//                Scaffold(modifier = Modifier.fillMaxWidth()) { innerPadding ->
//                    TitleCard(
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                    StyleBar(
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                    StockList(
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
        }
    }
}

@Composable
fun Holder(){
    Column() {
        TitleCard()
        StyleBar()
        StockListTopper()
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

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    StockMonkeyTheme {
        TitleCard()
    }
}