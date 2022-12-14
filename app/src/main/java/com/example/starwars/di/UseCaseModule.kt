package com.example.starwars.di

import com.example.starwars.domain.usecase.PeopleSearchUseCase
import com.example.starwars.domain.usecase.PeopleSearchUseCaseImpl
import com.example.starwars.domain.usecase.PeopleUseCase
import com.example.starwars.domain.usecase.PeopleUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    factory { PeopleUseCaseImpl(get()) as PeopleUseCase }

    factory { PeopleSearchUseCaseImpl(get()) as PeopleSearchUseCase }
}