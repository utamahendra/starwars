package com.example.starwars.di

import com.example.starwars.presentation.people.PeopleListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PeopleListViewModel(get()) }
}