package com.example.starwars.domain.usecase

import com.example.starwars.common.extension.asErrorObject
import com.example.starwars.common.viewstate.ViewError
import com.example.starwars.data.remote.response.PeopleResponse
import com.example.starwars.domain.model.PeopleData
import com.example.starwars.domain.source.PeopleDataSource
import com.example.starwars.domain.Either

class PeopleUseCaseImpl(private val repository: PeopleDataSource) : PeopleUseCase {

    override suspend fun invoke(params: Unit): Either<List<PeopleData>, ViewError> {
        return try {
            val result = repository.getPeople()
            Either.Success(result.map(userMapper))
        } catch (e: Exception) {
            Either.Fail(e.asErrorObject())
        }
    }

    private val userMapper: (PeopleResponse) -> PeopleData = { data ->
        PeopleData(
            data.results.name,
            data.results.height,
            data.results.mass,
            data.results.hairColor,
            data.results.skinColor,
            data.results.eyeColor,
            data.results.birthYear,
            data.results.gender
        )
    }
}