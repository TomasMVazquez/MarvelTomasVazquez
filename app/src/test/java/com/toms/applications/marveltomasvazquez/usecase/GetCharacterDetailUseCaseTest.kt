package com.toms.applications.marveltomasvazquez.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.applications.toms.data.*
import com.applications.toms.data.repository.CharacterDetailRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.testshared.listOfMocks
import com.applications.toms.usecases.characters.GetCharacterDetailUseCase
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
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class GetCharacterDetailUseCaseTest {
    @Mock
    private val fakeRemoteRepository = FakeRemoteRepository()

    @Mock
    private val repository = CharacterDetailRepository(fakeRemoteRepository)

    private val useCaseToTest by lazy { GetCharacterDetailUseCase(repository) }

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
            Mockito.`when`(repository.getCharacterDetail("0"))
                .thenReturn(eitherSuccess(listOfMocks.filter { it.id.toString() == "0" }))

            assert(useCaseToTest.execute("0") is Either.Success)
            useCaseToTest.execute("0").onSuccess {
                assertEquals(it, listOfMocks.filter { it.id.toString() == "0" })
            }
        }
    }

    @Test
    fun `get data and fail`() {
        runBlocking {
            Mockito.`when`(repository.getCharacterDetail("0"))
                .thenReturn(eitherFailure(ErrorStates.THROWABLE))

            assert(useCaseToTest.execute("0") is Either.Failure)
            useCaseToTest.execute("0").onFailure {
                assertEquals(it, ErrorStates.THROWABLE)
            }
        }
    }
}