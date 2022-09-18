package com.example.starwars.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.starwars.common.ErrorCode
import com.example.starwars.common.viewstate.ViewError
import com.example.starwars.data.remote.response.People
import com.example.starwars.data.remote.response.PeopleResponse
import com.example.starwars.domain.Either
import com.example.starwars.domain.model.PeopleData
import com.example.starwars.domain.source.PeopleDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.IOException

class PeopleUseCaseTest : KoinTest {
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val useCase by inject<PeopleUseCase>()

    @Mock
    private lateinit var repository: PeopleDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(module {
                single {
                    PeopleUseCaseImpl(repository) as PeopleUseCase
                }
            })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `success get peoples`() {
        runTest {
            val expected = Either.Success(
                listOf(
                    PeopleData(
                        "name",
                        "172",
                        "77",
                        "blond",
                        "fair",
                        "blue",
                        "19BBY",
                        "male"
                    )
                )
            )
            BDDMockito.given(repository.getPeople())
                .willReturn(
                    PeopleResponse(
                        listOf(
                            People(
                                "name",
                                "172",
                                "77",
                                "blond",
                                "fair",
                                "blue",
                                "19BBY",
                                "male"
                            )
                        )
                    )
                )

            val actual = useCase.invoke(Unit)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `internal server error get peoples`() {
        runTest {
            val expected = Either.Fail(ViewError(ErrorCode.GLOBAL_UNKNOWN_ERROR))
            BDDMockito.given(repository.getPeople()).willAnswer { throw RuntimeException() }

            val actual = useCase.invoke(Unit)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `IO error get peoples`() {
        runTest {
            val expected = Either.Fail(ViewError(ErrorCode.GLOBAL_INTERNET_ERROR))
            BDDMockito.given(repository.getPeople()).willAnswer { throw IOException() }

            val actual = useCase.invoke(Unit)

            assertEquals(expected, actual)
        }
    }
}