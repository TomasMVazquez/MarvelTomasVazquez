package com.toms.applications.marveltomasvazquez

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class SearchFragmentTest {
/*
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

    }*/
}