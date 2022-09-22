package com.example.starwars.data.remote

import com.example.starwars.data.remote.response.PeopleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleApiService {

    @GET("people")
    suspend fun getPeople(): PeopleResponse

    @GET("people")
    suspend fun getPeople(@Query("search") keyword: String): PeopleResponse
}