package com.toms.applications.marveltomasvazquez.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.repository.SearchRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.testshared.listOfMocks
import com.applications.toms.usecases.search.SearchUseCase
import com.toms.applications.marveltomasvazquez.repositories.FakeRemoteRepository
import com.toms.applications.marveltomasvazquez.ui.customviews.InfoState
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel
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
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    @Mock
    private val fakeRemoteRepository = FakeRemoteRepository()
    @Mock
    private val repository = SearchRepository(fakeRemoteRepository)
    @Mock
    private val useCase = SearchUseCase(repository)

    private val viewModel: SearchViewModel by lazy { SearchViewModel(useCase) }

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
            val value = MockEditable("")
            viewModel.onSearchBtnClicked(value)
            assert(viewModel.state.value.loading)
            assertTrue(viewModel.state.value.characters.isEmpty())
            assertNull(viewModel.state.value.errorWatcher)
        }
    }

    @Test
    fun `get data and success`()  {
        runTest {
            val value = MockEditable("prueba")

            Mockito.`when`(useCase.execute(value.toString()))
                .thenReturn(eitherSuccess(listOfMocks))

            viewModel.state.test {
                viewModel.onSearchBtnClicked(value)
                assertTrue(viewModel.state.value.characters.isNotEmpty())
                assertEquals(expectMostRecentItem().characters, listOfMocks)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `get empty data`()  {
        runTest {
            val value = MockEditable("prueba")

            Mockito.`when`(useCase.execute(value.toString()))
                .thenReturn(eitherSuccess(emptyList()))

            viewModel.state.test {
                viewModel.onSearchBtnClicked(value)
                assertEquals(expectMostRecentItem().errorWatcher, InfoState.SEARCH_EMPTY)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `get data and fail`()  {
        runTest {
            val value = MockEditable("prueba")

            Mockito.`when`(useCase.execute(value.toString()))
                .thenReturn(eitherFailure(ErrorStates.SERVER))

            viewModel.state.test {
                viewModel.onSearchBtnClicked(value)
                assertEquals(expectMostRecentItem().errorWatcher, InfoState.OTHER)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}