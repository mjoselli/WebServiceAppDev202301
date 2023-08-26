package com.example.chuckjokesandroid

import retrofit2.http.GET

interface ChuckNorrisApi {
    @GET("jokes/random")
    suspend fun getRandomJoke(): Joke
}