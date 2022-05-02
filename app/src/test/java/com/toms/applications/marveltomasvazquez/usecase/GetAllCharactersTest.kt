package com.toms.applications.marveltomasvazquez.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.applications.toms.data.*
import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.testshared.listOfMocks
import com.applications.toms.usecases.characters.GetAllCharacters
import com.toms.applications.marveltomasvazquez.repositories.FakeLocalRepository
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
class GetAllCharactersTest {

    @Mock
    private val fakeRemoteRepository = FakeRemoteRepository()

    @Mock
    private val fakeLocalRepository = FakeLocalRepository()

    @Mock
    private val characterRepository =
        CharactersRepository(fakeRemoteRepository, fakeLocalRepository)

    private val useCaseToTest by lazy { GetAllCharacters(characterRepository) }

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
            Mockito.`when`(characterRepository.getCharacters(0)).thenReturn(
                eitherSuccess(
                    listOfMocks
                )
            )

            assert(useCaseToTest.execute(GetAllCharacters.OkInput(0)) is Either.Success)
            useCaseToTest.execute(GetAllCharacters.OkInput(0)).onSuccess {
                assert(it.isNotEmpty())
            }
        }
    }

    @Test
    fun `get data and fail`() {
        runBlocking {
            Mockito.`when`(characterRepository.getCharacters(0))
                .thenReturn(eitherFailure(ErrorStates.SERVER))

            assert(useCaseToTest.execute(GetAllCharacters.OkInput(0)) is Either.Failure)
            useCaseToTest.execute(GetAllCharacters.OkInput(0)).onFailure {
                assert(it == ErrorStates.SERVER)
            }
        }
    }
}