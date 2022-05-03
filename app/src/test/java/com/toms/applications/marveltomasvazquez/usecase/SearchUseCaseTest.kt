package com.toms.applications.marveltomasvazquez.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.applications.toms.data.*
import com.applications.toms.data.repository.SearchRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.testshared.listOfMocks
import com.applications.toms.usecases.search.SearchUseCase
import com.toms.applications.marveltomasvazquez.repositories.FakeRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchUseCaseTest {

    @Mock
    private val fakeRemoteRepository = FakeRemoteRepository()
    @Mock
    private val repository = SearchRepository(fakeRemoteRepository)

    private val useCaseToTest by lazy { SearchUseCase(repository) }

    @ExperimentalCoroutinesApi
    val dispatcher = UnconfinedTestDispatcher()

    @Suppress("unused")
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `get data and success`() {
        runBlocking {

            Mockito.`when`(useCaseToTest.execute("prueba")).thenReturn(
                eitherSuccess(
                    listOfMocks
                )
            )

            assert(useCaseToTest.execute("prueba") is Either.Success)
            useCaseToTest.execute("prueba").onSuccess {
                assert(it.isNotEmpty())
            }
        }
    }

    @Test
    fun `get data and fail`() {
        runBlocking {
            Mockito.`when`(useCaseToTest.execute("prueba"))
                .thenReturn(eitherFailure(ErrorStates.SERVER))

            assert(useCaseToTest.execute("prueba") is Either.Failure)
            useCaseToTest.execute("prueba").onFailure {
                assert(it == ErrorStates.SERVER)
            }
        }
    }
}