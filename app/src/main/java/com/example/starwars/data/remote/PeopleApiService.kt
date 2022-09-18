package com.example.starwars.data.remote

import com.example.starwars.data.remote.response.PeopleResponse
import retrofit2.http.GET

interface PeopleApiService {

    @GET("people")
    suspend fun getPeople(): List<PeopleResponse>
}