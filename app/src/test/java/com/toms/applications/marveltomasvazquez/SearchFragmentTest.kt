package com.toms.applications.marveltomasvazquez

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.cash.turbine.test
import com.applications.toms.data.repository.SearchRepository
import com.applications.toms.testshared.mockCharacter
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.toms.applications.marveltomasvazquez.repositories.FakeRemoteRepository
import com.toms.applications.marveltomasvazquez.rules.CoroutineTestRule
import com.toms.applications.marveltomasvazquez.ui.customviews.InfoState
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel.*
import com.toms.applications.marveltomasvazquez.ui.screen.search.SearchViewModel.UiModel.*
import com.toms.applications.marveltomasvazquez.util.Event
import com.toms.applications.marveltomasvazquez.utils.MockEditable
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
class SearchFragmentTest {

    private lateinit var searchViewModel: SearchViewModel
    private val fakeRemoteRepository = FakeRemoteRepository()
    private val searchRepository = SearchRepository(fakeRemoteRepository)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp(){
        searchViewModel = SearchViewModel(searchRepository,Dispatchers.Unconfined)
    }

    @After
    fun tearDown(){

    }

    @Test
    fun `onSearchBtnClicked update UI recycler items`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val value = MockEditable("")

        searchViewModel.model.test {
            assertEquals(None,awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        searchViewModel.onSearchBtnClicked(value)

        searchViewModel.model.test {
            assertEquals(Loading,awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `navigation to detail when clicked on recycler item`() = coroutineTestRule.testDispatcher.runBlockingTest {

        searchViewModel.onCharacterClicked(mockCharacter)

        searchViewModel.navigation.test {
            assertEquals(mockCharacter,awaitItem().peekContent())
            cancelAndConsumeRemainingEvents()
        }

    }
}