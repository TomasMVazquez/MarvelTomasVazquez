package com.toms.applications.marveltomasvazquez.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.testshared.mockCharacter
import com.applications.toms.usecases.favorites.SaveToFavoritesUseCase
import com.toms.applications.marveltomasvazquez.repositories.FakeLocalRepository
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
class SaveToFavoriteUseCaseTest {

    @Mock
    private val fakeLocalRepository = FakeLocalRepository()

    @Mock
    private val repository = FavoriteRepository(fakeLocalRepository)

    private val useCaseToTest by lazy { SaveToFavoritesUseCase(repository) }

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
            Mockito.`when`(repository.addToFavorites(mockCharacter))
                .thenReturn(eitherSuccess(EitherState.SUCCESS))

            assert(useCaseToTest.execute(mockCharacter) is Either.Success)
        }
    }

    @Test
    fun `get data and fail`() {
        runBlocking {
            Mockito.`when`(repository.addToFavorites(mockCharacter))
                .thenReturn(eitherFailure(ErrorStates.THROWABLE))

            assert(useCaseToTest.execute(mockCharacter) is Either.Failure)
        }
    }
}