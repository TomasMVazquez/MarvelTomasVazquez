package com.toms.applications.marveltomasvazquez.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.repository.CharacterDetailRepository
import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.domain.ErrorStates
import com.applications.toms.testshared.listOfMocks
import com.applications.toms.usecases.characters.GetCharacterDetailUseCase
import com.applications.toms.usecases.favorites.GetFavoritesUseCase
import com.applications.toms.usecases.favorites.RemoveFromFavoritesUseCase
import com.applications.toms.usecases.favorites.SaveToFavoritesUseCase
import com.toms.applications.marveltomasvazquez.repositories.FakeLocalRepository
import com.toms.applications.marveltomasvazquez.repositories.FakeRemoteRepository
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel
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
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    @Mock
    private val fakeRemoteRepository = FakeRemoteRepository()
    @Mock
    private val fakeLocalRepository = FakeLocalRepository()
    @Mock
    private val repository = FavoriteRepository(fakeLocalRepository)
    @Mock
    private val repositoryDetail = CharacterDetailRepository(fakeRemoteRepository)
    @Mock
    private val favoriteUseCase = GetFavoritesUseCase(repository)
    @Mock
    private val saveFavoriteUseCase = SaveToFavoritesUseCase(repository)
    @Mock
    private val removeFavoriteUseCase = RemoveFromFavoritesUseCase(repository)
    @Mock
    private val getCharacterDetailUseCase = GetCharacterDetailUseCase(repositoryDetail)

    private val viewModel by lazy {
        DetailViewModel(
            getCharacterDetailUseCase = getCharacterDetailUseCase,
            getFavoritesUseCase = favoriteUseCase,
            saveToFavoritesUseCase = saveFavoriteUseCase,
            removeFromFavoritesUseCase = removeFavoriteUseCase,
            characterId = "0"
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
    fun `init viewModel test`() {
        runBlocking {
            assert(viewModel.state.value.loading)
            assertNull(viewModel.state.value.character)
            assertFalse(viewModel.state.value.isFavorite)
        }
    }

    @Test
    fun `get data and success`() {
        runTest {
            Mockito.`when`(favoriteUseCase.execute(null))
                .thenReturn(eitherSuccess(listOfMocks))
            Mockito.`when`(getCharacterDetailUseCase.execute("0"))
                .thenReturn(eitherSuccess(listOfMocks.filter { it.id.toString() == "0" }))

            viewModel.state.test {
                viewModel.getData()
                assertEquals(expectMostRecentItem().character, listOfMocks.first())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `get data and success with empty favorite`() {
        runTest {
            Mockito.`when`(favoriteUseCase.execute(null))
                .thenReturn(eitherFailure(ErrorStates.EMPTY))
            Mockito.`when`(getCharacterDetailUseCase.execute("0"))
                .thenReturn(eitherSuccess(listOfMocks.filter { it.id.toString() == "0" }))

            viewModel.state.test {
                viewModel.getData()
                assertEquals(expectMostRecentItem().character, listOfMocks.first())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `get data and fail`() {
        runTest {
            Mockito.`when`(favoriteUseCase.execute(null))
                .thenReturn(eitherFailure(ErrorStates.EMPTY))
            Mockito.`when`(getCharacterDetailUseCase.execute("0"))
                .thenReturn(eitherFailure(ErrorStates.THROWABLE))

            viewModel.state.test {
                viewModel.getData()
                assertEquals(expectMostRecentItem().character, null)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `save favorite and success`() {
        runTest {

            Mockito.`when`(saveFavoriteUseCase.execute(listOfMocks.first()))
                .thenReturn(eitherSuccess(EitherState.SUCCESS))

            viewModel.state.test {
                viewModel.onFabClicked(listOfMocks.first())
                assertTrue(expectMostRecentItem().isFavorite)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `remove favorite and success`() {
        runTest {

            Mockito.`when`(removeFavoriteUseCase.execute(listOfMocks.first()))
                .thenReturn(eitherSuccess(EitherState.SUCCESS))

            viewModel.state.test {
                viewModel.onFabClicked(listOfMocks.first())
                assertFalse(expectMostRecentItem().isFavorite)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

}