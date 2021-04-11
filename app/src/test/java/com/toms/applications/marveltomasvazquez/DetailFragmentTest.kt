package com.toms.applications.marveltomasvazquez

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.toms.applications.marveltomasvazquez.domain.*
import com.toms.applications.marveltomasvazquez.repositories.FakeLocalRepository
import com.toms.applications.marveltomasvazquez.repository.FavoriteRepository
import com.toms.applications.marveltomasvazquez.rules.CoroutineTestRule
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel.UiModel
import com.toms.applications.marveltomasvazquez.ui.screen.detail.DetailViewModel.UiModel.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class DetailFragmentTest {

    private lateinit var detailViewModel: DetailViewModel
    private val fakeLocalRepository = FakeLocalRepository()
    private val favoriteRepository = FavoriteRepository(fakeLocalRepository)

    private val character = Character(
        1,
        "",
        "",
        Thumbnail("",""),
        Comics(0, emptyList<ComicItem>()),
        Events(0, emptyList<EventItem>()),
        Series(0, emptyList<SerieItem>()),
        Stories(0, emptyList<StoryItem>())
    )

    // To allow the correct execution of test with LiveData
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp(){
        detailViewModel = DetailViewModel(favoriteRepository,character)
    }

    @After
    fun tearDown(){

    }

    @Test
    fun `update UI when init`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val observer = mock<Observer<UiModel>>()
        detailViewModel.model.observeForever(observer)

        verify(observer).onChanged(NotFavorite)
    }

    @Test
    fun `onFabClicked update UI icon`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val observer = mock<Observer<UiModel>>()
        detailViewModel.model.observeForever(observer)

        detailViewModel.onFabClicked(character)

        verify(observer).onChanged(NotFavorite)
        verify(observer).onChanged(Loading)
        verify(observer).onChanged(Favorite)
    }
}