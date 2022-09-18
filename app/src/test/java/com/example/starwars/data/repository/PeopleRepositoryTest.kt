package com.example.starwars.data.repository

import com.example.starwars.data.remote.PeopleApiService
import com.example.starwars.data.remote.response.PeopleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PeopleRepositoryTest : KoinTest {

    @Mock
    lateinit var apiService: PeopleApiService

    private val repository by inject<PeopleRepository>()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(module {
                single { PeopleRepository(apiService) }
            })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `on get people list success`() {
        runTest {
            val expectedResult = PeopleResponse(listOf())
            BDDMockito.given(apiService.getPeople()).willReturn(expectedResult)
            val actual = repository.getPeople()
            assertEquals(expectedResult, actual)
        }
    }
}