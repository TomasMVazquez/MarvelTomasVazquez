package com.toms.applications.marveltomasvazquez.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.applications.toms.data.*
import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.testshared.listOfMocks
import com.applications.toms.usecases.favorites.GetFavoritesUseCase
import com.toms.applications.marveltomasvazquez.repositories.FakeLocalRepository
import com.toms.applications.marveltomasvazquez.utils.MockEditable
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
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class GetFavoritesUseCaseTest {

    @Mock
    private val fakeLocalRepository = FakeLocalRepository()

    @Mock
    private val repository = FavoriteRepository(fakeLocalRepository)

    private val useCaseToTest by lazy { GetFavoritesUseCase(repository) }

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
            Mockito.`when`(repository.getCharacters())
                .thenReturn(eitherSuccess(listOfMocks))

            assert(useCaseToTest.execute(null) is Either.Success)
            useCaseToTest.execute(null).onSuccess {
                assert(it.isNotEmpty())
                assertEquals(it, listOfMocks)
            }
        }
    }

    @Test
    fun `get data and fail`() {
        runBlocking {
            Mockito.`when`(repository.getCharacters())
                .thenReturn(eitherFailure(ErrorStates.EMPTY))

            assert(useCaseToTest.execute(null) is Either.Failure)
            useCaseToTest.execute(null).onFailure {
                assert(it == ErrorStates.EMPTY)
            }
        }
    }

    @Test
    fun `get specific data and success`() {
        runBlocking {
            val searchValue = MockEditable("Nombre")

            Mockito.`when`(repository.searchCharacters(searchValue.toString()))
                .thenReturn(eitherSuccess(listOfMocks))

            assert(useCaseToTest.execute(searchValue.toString()) is Either.Success)
            useCaseToTest.execute(searchValue.toString()).onSuccess {
                assert(it.isNotEmpty())
                assertEquals(it, listOfMocks)
            }
        }
    }

    @Test
    fun `get specific data and fail`() {
        runBlocking {
            val searchValue = MockEditable("Nombre")

            Mockito.`when`(repository.searchCharacters(searchValue.toString()))
                .thenReturn(eitherFailure(ErrorStates.EMPTY))

            assert(useCaseToTest.execute(searchValue.toString()) is Either.Failure)
            useCaseToTest.execute(searchValue.toString()).onFailure {
                assert(it == ErrorStates.EMPTY)
            }
        }
    }

}