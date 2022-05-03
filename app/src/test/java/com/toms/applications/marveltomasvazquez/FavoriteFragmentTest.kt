package com.toms.applications.marveltomasvazquez

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class FavoriteFragmentTest {
/*
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
    }*/
}