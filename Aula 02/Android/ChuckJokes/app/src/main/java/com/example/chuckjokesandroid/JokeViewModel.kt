package com.example.chuckjokesandroid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JokeViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.chucknorris.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val chuckNorrisApi = retrofit.create(ChuckNorrisApi::class.java)

    var joke by mutableStateOf("Chuck Norris Jokes")

    fun fetchJoke() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val jokeResponse = chuckNorrisApi.getRandomJoke()
                joke = jokeResponse.value
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}