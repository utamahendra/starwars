package com.example.starwars.domain.usecase

import com.example.starwars.domain.UseCase
import com.example.starwars.domain.model.PeopleData

interface PeopleSearchUseCase: UseCase<String, List<PeopleData>>