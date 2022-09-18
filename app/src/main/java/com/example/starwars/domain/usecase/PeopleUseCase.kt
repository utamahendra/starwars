package com.example.starwars.domain.usecase

import com.example.starwars.domain.model.PeopleData
import com.example.starwars.domain.UseCase

interface PeopleUseCase: UseCase<Unit, List<PeopleData>>