package com.example.starwars.domain.usecase

import com.example.starwars.common.extension.asErrorObject
import com.example.starwars.common.viewstate.ViewError
import com.example.starwars.data.remote.response.People
import com.example.starwars.domain.Either
import com.example.starwars.domain.model.PeopleData
import com.example.starwars.domain.source.PeopleDataSource

class PeopleSearchUseCaseImpl(private val repository: PeopleDataSource): PeopleSearchUseCase {

    override suspend fun invoke(params: String): Either<List<PeopleData>, ViewError> {
        return try {
            val result = repository.getSearchByPeople(params)
            Either.Success(result.results.map(userMapper))
        } catch (e: Exception) {
            Either.Fail(e.asErrorObject())
        }
    }

    private val userMapper: (People) -> PeopleData = { data ->
        PeopleData(
            data.name,
            data.height,
            data.mass,
            data.hairColor,
            data.skinColor,
            data.eyeColor,
            data.birthYear,
            data.gender
        )
    }
}