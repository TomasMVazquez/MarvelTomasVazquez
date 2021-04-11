package com.toms.applications.marveltomasvazquez

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.toms.applications.marveltomasvazquez.domain.*
import com.toms.applications.marveltomasvazquez.repositories.FakeLocalRepository
import com.toms.applications.marveltomasvazquez.repository.FavoriteRepository
import com.toms.applications.marveltomasvazquez.rules.CoroutineTestRule
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.UiModel
import com.toms.applications.marveltomasvazquez.ui.screen.favorite.FavoriteViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.utils.MockEditable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class FavoriteFragmentTest {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private val fakeLocalRepository = FakeLocalRepository()
    private val favoriteRepository = FavoriteRepository(fakeLocalRepository)

    // To allow the correct execution of test with LiveData
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp(){
        favoriteViewModel = FavoriteViewModel(favoriteRepository)
    }

    @After
    fun tearDown(){

    }

    @Test
    fun `onSearchCharacter update UI recycler items`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val observer = mock<Observer<UiModel>>()
        favoriteViewModel.model.observeForever(observer)

        val value = MockEditable("")

        favoriteViewModel.onSearchCharacter(value)

        verify(observer).onChanged(Loading)
        verify(observer).onChanged(favoriteViewModel.model.value)
    }

    @Test
    fun `navigation to detail when clicked on recycler item`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val character = Character(
            1,
            "",
            "",
            Thumbnail("",""),
            Comics(0, emptyList<ComicItem>()),
            Events(0, emptyList<EventItem>()),
            Series(0, emptyList<SerieItem>()),
            Stories(0, emptyList<StoryItem>())
        )

        val observer = mock<Observer<Event<Character>>>()
        favoriteViewModel.navigation.observeForever(observer)

        favoriteViewModel.onCharacterClicked(character)

        verify(observer).onChanged(favoriteViewModel.navigation.value)
    }
}