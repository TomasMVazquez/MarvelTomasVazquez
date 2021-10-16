package com.toms.applications.marveltomasvazquez

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.testshared.listOfMocks
import com.applications.toms.testshared.mockCharacter
import com.applications.toms.usecases.favorites.GetFavorites
import com.applications.toms.usecases.favorites.RemoveFromFavorites
import com.applications.toms.usecases.favorites.SaveToFavorites
import com.toms.applications.marveltomasvazquez.repositories.FakeLocalRepository
import com.toms.applications.marveltomasvazquez.rules.CoroutineTestRule
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel.UiModel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class DetailFragmentTest {

    private lateinit var detailViewModel: DetailViewModel
    private val fakeLocalRepository = FakeLocalRepository()
    private val favoriteRepository = FavoriteRepository(fakeLocalRepository)
    private val getFavoriteRepository = GetFavorites(favoriteRepository)
    private val saveFavorites = SaveToFavorites(favoriteRepository)
    private val removeFavorite = RemoveFromFavorites(favoriteRepository)


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp(){
        detailViewModel = DetailViewModel(getFavoriteRepository,saveFavorites,removeFavorite,
            mockCharacter,Dispatchers.Unconfined)
    }

    @After
    fun tearDown(){

    }

    @Test
    fun `update UI when init`() = coroutineTestRule.testDispatcher.runBlockingTest {
        detailViewModel.model.test {
            assertEquals(Loading,awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onFabClicked update UI icon`() = coroutineTestRule.testDispatcher.runBlockingTest {

        detailViewModel.onFabClicked(mockCharacter)

        detailViewModel.model.test {
            assertEquals(Loading,awaitItem())
            assertEquals(Favorite,awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}