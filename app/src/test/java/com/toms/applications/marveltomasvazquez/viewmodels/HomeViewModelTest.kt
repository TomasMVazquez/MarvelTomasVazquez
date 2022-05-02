package com.toms.applications.marveltomasvazquez.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.testshared.listOfMocks
import com.applications.toms.usecases.characters.GetAllCharacters
import com.google.gson.Gson
import com.toms.applications.marveltomasvazquez.repositories.FakeLocalRepository
import com.toms.applications.marveltomasvazquez.repositories.FakeRemoteRepository
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel
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
class HomeViewModelTest {

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
    fun `init viewModel test`() {
        runBlocking {
            assert(viewModel.state.value.loading)
            assert(viewModel.state.value.characters.isEmpty())
            assert(viewModel.state.value.errorWatcher == null)
        }
    }

    @Test
    fun `get data and success`()  {
        runBlocking {
            Mockito.`when`(getAllCharacters.execute(GetAllCharacters.OkInput(0)))
                .thenReturn(eitherSuccess(listOfMocks))
            assert(viewModel.state.value.characters.isNotEmpty())
            assertEquals(viewModel.state.value.characters, listOfMocks)
        }
    }

    @Test
    fun `get data and fail`()  {
        runBlocking {
            Mockito.`when`(getAllCharacters.execute(GetAllCharacters.OkInput(0)))
                .thenReturn(eitherFailure(ErrorStates.SERVER))
            assert(viewModel.state.value.errorWatcher != null)
        }
    }
}
