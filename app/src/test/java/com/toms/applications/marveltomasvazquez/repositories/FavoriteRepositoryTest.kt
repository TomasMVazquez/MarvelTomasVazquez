package com.toms.applications.marveltomasvazquez.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.applications.toms.data.*
import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.testshared.listOfMocks
import com.applications.toms.testshared.mockCharacter
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
class FavoriteRepositoryTest {

    @Mock
    private val fakeLocalRepository = FakeLocalRepository()

    private val repositoryTest by lazy { FavoriteRepository(fakeLocalRepository) }

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
            Mockito.`when`(fakeLocalRepository.getMyFavoritesCharacters())
                .thenReturn(eitherSuccess(listOfMocks))

            assert(repositoryTest.getCharacters() is Either.Success)
            repositoryTest.getCharacters().onSuccess {
                assertEquals(it, listOfMocks)
            }
        }
    }

    @Test
    fun `get data and fail`() {
        runBlocking {
            Mockito.`when`(fakeLocalRepository.getMyFavoritesCharacters())
                .thenReturn(eitherFailure(ErrorStates.EMPTY))

            assert(repositoryTest.getCharacters() is Either.Failure)
            repositoryTest.getCharacters().onFailure {
                assertEquals(it, ErrorStates.EMPTY)
            }
        }
    }

    @Test
    fun `search data and success`() {
        runBlocking {
            val searchValue = MockEditable("Nombre")
            Mockito.`when`(fakeLocalRepository.searchCharacters(searchValue.toString()))
                .thenReturn(eitherSuccess(listOfMocks))

            assert(repositoryTest.searchCharacters(searchValue.toString()) is Either.Success)
            repositoryTest.searchCharacters(searchValue.toString()).onSuccess {
                assertEquals(it, listOfMocks)
            }
        }
    }

    @Test
    fun `search data and fail`() {
        runBlocking {
            val searchValue = MockEditable("Nombre")
            Mockito.`when`(fakeLocalRepository.searchCharacters(searchValue.toString()))
                .thenReturn(eitherFailure(ErrorStates.EMPTY))

            assert(repositoryTest.searchCharacters(searchValue.toString()) is Either.Failure)
            repositoryTest.searchCharacters(searchValue.toString()).onFailure {
                assertEquals(it, ErrorStates.EMPTY)
            }
        }
    }

    @Test
    fun `add favorite and success`() {
        runBlocking {
            Mockito.`when`(fakeLocalRepository.addFavorite(mockCharacter))
                .thenReturn(eitherSuccess(EitherState.SUCCESS))

            assert(repositoryTest.addToFavorites(mockCharacter) is Either.Success)
        }
    }

    @Test
    fun `add favorite and fail`() {
        runBlocking {
            Mockito.`when`(fakeLocalRepository.addFavorite(mockCharacter))
                .thenReturn(eitherFailure(ErrorStates.NOT_SAVED))

            assert(repositoryTest.addToFavorites(mockCharacter) is Either.Failure)
        }
    }

    @Test
    fun `remove favorite and success`() {
        runBlocking {
            Mockito.`when`(fakeLocalRepository.removeFavorite(mockCharacter))
                .thenReturn(eitherSuccess(EitherState.SUCCESS))

            assert(repositoryTest.removeFromFavorites(mockCharacter) is Either.Success)
        }
    }

    @Test
    fun `remove favorite and fail`() {
        runBlocking {
            Mockito.`when`(fakeLocalRepository.removeFavorite(mockCharacter))
                .thenReturn(eitherFailure(ErrorStates.NOT_SAVED))

            assert(repositoryTest.removeFromFavorites(mockCharacter) is Either.Failure)
        }
    }
}