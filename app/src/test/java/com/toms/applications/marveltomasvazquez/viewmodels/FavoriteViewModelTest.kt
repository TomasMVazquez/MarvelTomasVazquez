package com.toms.applications.marveltomasvazquez.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.testshared.listOfMocks
import com.applications.toms.testshared.mockCharacter
import com.applications.toms.usecases.favorites.GetFavorites
import com.toms.applications.marveltomasvazquez.repositories.FakeLocalRepository
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel
import com.toms.applications.marveltomasvazquez.utils.MockEditable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    @Mock
    private val fakeLocalRepository = FakeLocalRepository()

    @Mock
    private val repository = FavoriteRepository(fakeLocalRepository)

    @Mock
    private val useCase = GetFavorites(repository)

    private val viewModel: FavoriteViewModel by lazy { FavoriteViewModel(useCase) }

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
    fun `get data and success`() {
        runBlocking {
            Mockito.`when`(useCase.execute(null))
                .thenReturn(eitherSuccess(listOfMocks))
            assert(viewModel.state.value.characters.isNotEmpty())
            assertEquals(viewModel.state.value.characters, listOfMocks)
        }
    }

    @Test
    fun `get specific data and success`() {
        runTest {
            val searchValue = MockEditable("Nombre")
            Mockito.`when`(useCase.execute(searchValue.toString()))
                .thenReturn(eitherSuccess(listOf(mockCharacter)))
            viewModel.state.test {
                viewModel.onSearchCharacter(searchValue)
                assertEquals(expectMostRecentItem().characters, listOf(mockCharacter))
            }
        }
    }

    @Test
    fun `get data and fail`() {
        runBlocking {
            Mockito.`when`(useCase.execute(null))
                .thenReturn(eitherFailure(ErrorStates.EMPTY))
            assert(viewModel.state.value.errorWatcher != null)
        }
    }

}