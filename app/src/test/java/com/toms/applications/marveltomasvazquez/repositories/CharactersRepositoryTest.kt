package com.toms.applications.marveltomasvazquez.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.applications.toms.data.*
import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.testshared.listOfMocks
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
import kotlin.onFailure
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class CharactersRepositoryTest {

    @Mock
    private val fakeRemoteRepository = FakeRemoteRepository()

    @Mock
    private val fakeLocalRepository = FakeLocalRepository()

    private val repositoryTest by lazy {
        CharactersRepository(
            fakeRemoteRepository,
            fakeLocalRepository
        )
    }

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
            Mockito.`when`(repositoryTest.fetchCharacters(0))
                .thenReturn(eitherSuccess(listOfMocks))

            assert(repositoryTest.getCharacters(0) is Either.Success)
            repositoryTest.getCharacters(0).onSuccess {
                assertEquals(it, listOfMocks)
            }
        }
    }

    @Test
    fun `get data and fail`() {
        runBlocking {
            Mockito.`when`(repositoryTest.fetchCharacters(0))
                .thenReturn(eitherFailure(ErrorStates.SERVER))

            assert(repositoryTest.getCharacters(0) is Either.Failure)
            repositoryTest.getCharacters(0).onFailure {
                assertEquals(it, ErrorStates.SERVER)
            }
        }
    }
}