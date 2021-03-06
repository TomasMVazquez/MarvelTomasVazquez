package com.toms.applications.marveltomasvazquez.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.repository.CharactersRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.testshared.listOfMocks
import com.applications.toms.usecases.characters.GetAllCharactersUseCase
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
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    private val fakeRemoteRepository = FakeRemoteRepository()
    @Mock
    private val fakeLocalRepository = FakeLocalRepository()
    @Mock
    private val characterRepository = CharactersRepository(fakeRemoteRepository,fakeLocalRepository)
    @Mock
    private val getAllCharacters = GetAllCharactersUseCase(characterRepository)

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
            assertTrue(viewModel.state.value.characters.isEmpty())
            assertNull(viewModel.state.value.errorWatcher)
        }
    }

    @Test
    fun `get data and success`()  {
        runBlocking {
            Mockito.`when`(getAllCharacters.execute(GetAllCharactersUseCase.OkInput(0)))
                .thenReturn(eitherSuccess(listOfMocks))
            assertTrue(viewModel.state.value.characters.isNotEmpty())
            assertEquals(viewModel.state.value.characters, listOfMocks)
        }
    }

    @Test
    fun `get data and fail`()  {
        runBlocking {
            Mockito.`when`(getAllCharacters.execute(GetAllCharactersUseCase.OkInput(0)))
                .thenReturn(eitherFailure(ErrorStates.SERVER))
            assertNotNull(viewModel.state.value.errorWatcher)
        }
    }
}
