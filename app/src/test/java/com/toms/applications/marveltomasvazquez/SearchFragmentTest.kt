package com.toms.applications.marveltomasvazquez

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.toms.applications.marveltomasvazquez.domain.*
import com.toms.applications.marveltomasvazquez.repositories.FakeRemoteRepository
import com.toms.applications.marveltomasvazquez.repository.SearchRepository
import com.toms.applications.marveltomasvazquez.rules.CoroutineTestRule
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel.*
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel.UiModel.*
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
class SearchFragmentTest {

    private lateinit var searchViewModel: SearchViewModel
    private val fakeRemoteRepository = FakeRemoteRepository()
    private val searchRepository = SearchRepository(fakeRemoteRepository)

    // To allow the correct execution of test with LiveData
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp(){
        searchViewModel = SearchViewModel(searchRepository)
    }

    @After
    fun tearDown(){

    }

    @Test
    fun `onSearchBtnClicked update UI recycler items`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val observer = mock<Observer<UiModel>>()
        searchViewModel.model.observeForever(observer)

        val value = MockEditable("")

        searchViewModel.onSearchBtnClicked(value)

        verify(observer).onChanged(Loading)
        verify(observer).onChanged(searchViewModel.model.value)
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
        searchViewModel.navigation.observeForever(observer)

        searchViewModel.onCharacterClicked(character)

        verify(observer).onChanged(searchViewModel.navigation.value)
    }
}