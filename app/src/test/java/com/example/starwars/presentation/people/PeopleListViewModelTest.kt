package com.example.starwars.presentation.people

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.starwars.common.ErrorCode
import com.example.starwars.common.viewstate.ViewError
import com.example.starwars.common.viewstate.ViewState
import com.example.starwars.domain.Either
import com.example.starwars.domain.model.PeopleData
import com.example.starwars.domain.usecase.PeopleUseCase
import com.example.starwars.getTestObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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

class PeopleListViewModelTest : KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val viewModel by inject<PeopleListViewModel>()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var peopleUseCase: PeopleUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        startKoin {
            modules(module {
                single { PeopleListViewModel(peopleUseCase) }
            })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `on get peoples success`() {
        runTest {
            BDDMockito.given(peopleUseCase.invoke(Unit)).willReturn(
                Either.Success(listOf(PeopleData("name",
                    "172",
                    "77",
                    "blond",
                    "fair",
                    "blue",
                    "19BBY",
                    "male")))
            )
            val expected = listOf(
                ViewState.Loading(),
                ViewState.Success(listOf(PeopleData("name",
                    "172",
                    "77",
                    "blond",
                    "fair",
                    "blue",
                    "19BBY",
                    "male")))
            )
            val actual = viewModel.peopleState.getTestObserver().observedValues
            viewModel.getPeoples()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `on get peoples error`() {
        runTest {
            val error = ViewError(ErrorCode.GLOBAL_UNKNOWN_ERROR)
            BDDMockito.given(peopleUseCase.invoke(Unit)).willReturn(Either.Fail(error))
            val expected = listOf<ViewState<List<PeopleData>>>(
                ViewState.Loading(),
                ViewState.Error(error)
            )
            val actual = viewModel.peopleState.getTestObserver().observedValues
            viewModel.getPeoples()
            assertEquals(expected, actual)
        }
    }
}