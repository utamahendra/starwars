package com.example.starwars.data.repository

import com.example.starwars.data.remote.PeopleApiService
import com.example.starwars.data.remote.response.PeopleResponse
import com.example.starwars.domain.source.PeopleDataSource

class PeopleRepository(private val apiService: PeopleApiService) : PeopleDataSource {

    override suspend fun getPeople(): List<PeopleResponse> = apiService.getPeople()
}