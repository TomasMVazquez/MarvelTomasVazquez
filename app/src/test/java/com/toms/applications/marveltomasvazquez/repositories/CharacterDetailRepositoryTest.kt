package com.toms.applications.marveltomasvazquez.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.applications.toms.data.*
import com.applications.toms.data.repository.CharacterDetailRepository
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
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class CharacterDetailRepositoryTest {
    @Mock
    private val fakeRemoteRepository = FakeRemoteRepository()

    private val repositoryTest by lazy { CharacterDetailRepository(fakeRemoteRepository) }

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
            Mockito.`when`(repositoryTest.getCharacterDetail("0"))
                .thenReturn(eitherSuccess(listOfMocks.filter { it.id.toString() == "0" }))

            assert(repositoryTest.getCharacterDetail("0") is Either.Success)
            repositoryTest.getCharacterDetail("0").onSuccess {
                assertEquals(it, listOfMocks.filter { it.id.toString() == "0" })
            }
        }
    }

    @Test
    fun `get data and fail`() {
        runBlocking {
            Mockito.`when`(repositoryTest.getCharacterDetail("0"))
                .thenReturn(eitherFailure(ErrorStates.SERVER))

            assert(repositoryTest.getCharacterDetail("0") is Either.Failure)
            repositoryTest.getCharacterDetail("0").onFailure {
                assertEquals(it, ErrorStates.SERVER)
            }
        }
    }
}