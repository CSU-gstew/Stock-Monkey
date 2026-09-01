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

class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockMonkeyTheme {
                Scaffold(modifier = Modifier.fillMaxWidth()) { innerPadding ->
                    TitleCard(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    StyleBar(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    StockList(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TitleCard(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Home Page",
        modifier = modifier.padding(horizontal = 150.dp, vertical = 50.dp)
    )
}

@Composable
fun StyleBar(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "------------",
        modifier = modifier.padding(horizontal = 150.dp, vertical = 25.dp)
    )
}

@Composable
fun StockList(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Start List",
        modifier = modifier.padding(horizontal = 150.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    StockMonkeyTheme {
        TitleCard("Android")
    }
}