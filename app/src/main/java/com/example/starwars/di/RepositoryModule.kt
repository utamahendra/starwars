package com.example.starwars.di

import com.example.starwars.data.repository.PeopleRepository
import com.example.starwars.domain.source.PeopleDataSource
import org.koin.dsl.module

val repositoryModule = module {

    factory { PeopleRepository(get()) as PeopleDataSource }
}
