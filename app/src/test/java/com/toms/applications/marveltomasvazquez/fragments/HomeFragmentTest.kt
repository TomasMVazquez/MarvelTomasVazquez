package com.toms.applications.marveltomasvazquez.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.testshared.listOfMocks
import com.applications.toms.testshared.mockCharacter
import com.applications.toms.usecases.characters.GetAllCharacters
import com.toms.applications.marveltomasvazquez.repositories.FakeLocalRepository
import com.toms.applications.marveltomasvazquez.repositories.FakeRemoteRepository
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.expect
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
@RunWith(MockitoJUnitRunner::class)
class HomeFragmentTest {

    @Mock
    private val fakeRemoteRepository = FakeRemoteRepository()
    @Mock
    private val fakeLocalRepository = FakeLocalRepository()
    @Mock
    private val characterRepository = CharactersRepository(fakeRemoteRepository,fakeLocalRepository)
    @Mock
    private val getAllCharacters = GetAllCharacters(characterRepository)

    private val viewModel: HomeViewModel by lazy { HomeViewModel(getAllCharacters) }

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
    fun `navigation to detail when clicked on recycler item`() {
        runTest {
            val character = mockCharacter
            viewModel.event.test {
                viewModel.onCharacterClicked(character)
                assertEquals(awaitItem(),HomeViewModel.Event.GoToDetail(character))
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `navigation fail`() {
        runTest {
            viewModel.event.test {
                expectNoEvents()
            }
        }
    }

}