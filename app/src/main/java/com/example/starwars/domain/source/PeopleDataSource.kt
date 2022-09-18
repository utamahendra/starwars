package com.example.starwars.domain.source

import com.example.starwars.data.remote.response.PeopleResponse

interface PeopleDataSource {

    suspend fun getPeople(): PeopleResponse

}