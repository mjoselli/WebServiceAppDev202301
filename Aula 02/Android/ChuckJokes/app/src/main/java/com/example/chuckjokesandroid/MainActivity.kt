package com.example.chuckjokesandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chuckjokesandroid.ui.theme.ChuckJokesAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: JokeViewModel = JokeViewModel()
        setContent {
            ChuckJokesAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContentView(viewModel)
                }
            }
        }
    }
}

@Composable
fun ContentView(viewModel: JokeViewModel) {
    Column {
        Text(text = viewModel.joke, modifier = Modifier.padding(16.dp))
        Button(onClick = { viewModel.fetchJoke() }, modifier = Modifier.padding(16.dp)) {
            Text(text = "Get Joke")
        }
    }
}