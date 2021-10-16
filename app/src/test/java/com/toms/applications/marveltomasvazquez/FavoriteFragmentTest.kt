package com.toms.applications.marveltomasvazquez

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.cash.turbine.test
import com.applications.toms.data.repository.FavoriteRepository
import com.applications.toms.domain.MyCharacter
import com.applications.toms.testshared.mockCharacter
import com.applications.toms.usecases.favorites.GetFavorites
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.toms.applications.marveltomasvazquez.repositories.FakeLocalRepository
import com.toms.applications.marveltomasvazquez.rules.CoroutineTestRule
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.UiModel
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.utils.MockEditable
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class FavoriteFragmentTest {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private val fakeLocalRepository = FakeLocalRepository()
    private val favoriteRepository = FavoriteRepository(fakeLocalRepository)
    private val getFavoriteRepository = GetFavorites(favoriteRepository)

    // To allow the correct execution of test with LiveData
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp(){
        favoriteRepository.addToFavorites(mockCharacter)
        favoriteViewModel = FavoriteViewModel(getFavoriteRepository,Dispatchers.Unconfined)
    }

    @After
    fun tearDown(){

    }

    @Test
    fun `onSearchCharacter update UI recycler items`() = coroutineTestRule.testDispatcher.runBlockingTest {

        favoriteViewModel.model.test {
            assertEquals(Loading,awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `navigation to detail when clicked on recycler item`() = coroutineTestRule.testDispatcher.runBlockingTest {
        favoriteViewModel.onCharacterClicked(mockCharacter)
        favoriteViewModel.navigation.test {
            assertEquals(mockCharacter,awaitItem().peekContent())
            cancelAndConsumeRemainingEvents()
        }
    }
}