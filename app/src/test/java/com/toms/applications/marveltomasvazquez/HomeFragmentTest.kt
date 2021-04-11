package com.toms.applications.marveltomasvazquez

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.toms.applications.marveltomasvazquez.domain.*
import com.toms.applications.marveltomasvazquez.repositories.FakeRemoteRepository
import com.toms.applications.marveltomasvazquez.repository.CharactersRepository
import com.toms.applications.marveltomasvazquez.rules.CoroutineTestRule
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel.UiModel
import com.toms.applications.marveltomasvazquez.ui.screen.home.HomeViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    private lateinit var homeViewModel: HomeViewModel
    private val fakeRemoteRepository = FakeRemoteRepository()
    private val characterRepository = CharactersRepository(fakeRemoteRepository)

    // To allow the correct execution of test with LiveData
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp(){
        homeViewModel = HomeViewModel(characterRepository)
    }

    @After
    fun tearDown(){

    }

    @Test
    fun `update UI when init`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val observer = mock<Observer<UiModel>>()
        homeViewModel.model.observeForever(observer)

        verify(observer).onChanged(Loading)
        verify(observer).onChanged(homeViewModel.model.value)
    }

    @Test
    fun `onCharactersChanged when update UI recycler items`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val observer = mock<Observer<MutableList<Character>>>()
        homeViewModel.characters.observeForever(observer)

        homeViewModel.onCharactersChanged(emptyList<Character>().toMutableList())

        verify(observer).onChanged(homeViewModel.characters.value)
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
        homeViewModel.navigation.observeForever(observer)

        homeViewModel.onCharacterClicked(character)

        verify(observer).onChanged(homeViewModel.navigation.value)
    }

}